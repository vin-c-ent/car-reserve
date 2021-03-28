package com.car.service;

import com.car.dto.response.BaseResponse;
import com.car.dto.response.ReserveResponse;
import com.car.entity.CarEntity;
import com.car.entity.ReserveEntity;
import com.car.exception.CustomizedException;
import com.car.repository.CarRepository;
import com.car.repository.ReserveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.car.exception.ReserveExceptionInfoEnums.NO_CAR_IS_AVAILABLE;

@Slf4j
@Service
public class ReserveService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ReserveRepository reserveRepository;
    @Value("${com.car.lock.period.ms}")
    private Long carLockPeriod;

    public BaseResponse<ReserveResponse> reserve(Long modelId, LocalDate startDate, LocalDate endDate) {

        // get car whose model id is match & expire time is valid
        List<CarEntity> carEntityList = this.getAvailableCarByModelId(modelId);
        log.info("for model {}, available car list is {}", modelId, carEntityList);

        // group car entity as a <car id, car entity> map, for using later
        Map<UUID, CarEntity> carIdAndEntityMap = carEntityList.stream()
                .collect(Collectors.toMap(
                        CarEntity::getId,
                        carEntity -> carEntity
                ));

        // find the reserve record of all available cars
        List<ReserveEntity> reserveEntityList = reserveRepository.findAllByCarIdIn(carIdAndEntityMap.keySet());

        // group the reserve record as a <car id, reserve order> map
        Map<UUID, List<ReserveEntity>> carIdReserveEntityListMap = reserveEntityList.stream()
                .collect(Collectors.groupingBy(ReserveEntity::getCarId));
        log.info("reserve entity map : {}", carIdReserveEntityListMap);

        // for loop every car
        for (Map.Entry<UUID, List<ReserveEntity>> entry : carIdReserveEntityListMap.entrySet()) {

            UUID carId = entry.getKey();
            List<ReserveEntity> reserveEntities = entry.getValue();

            // order the reserve record by start date
            List<ReserveEntity> orderedReserveEntities = reserveEntities.stream()
                    .sorted(Comparator.comparing(ReserveEntity::getStartDate))
                    .collect(Collectors.toList());

            // if no conflict
            if (this.noConflictExisted(startDate, endDate, orderedReserveEntities)) {

                // find the matched car entity
                CarEntity matchedCarEntity = carIdAndEntityMap.get(carId);
                log.info("found one matched car : {}", matchedCarEntity);

                // generate the new version
                UUID newVersion = UUID.randomUUID();

                // try to acquire the lock in CAS way
                int result = carRepository.lock(newVersion, new Date(System.currentTimeMillis() + carLockPeriod), carId, matchedCarEntity.getLockVersion());
                if (result == 1) {
                    // succssfully acquire the lock
                    log.info("acquire the lock for car {} successfully, previous version : {}, latest version : {}", carId, matchedCarEntity.getLockVersion(), newVersion);

                    // save db
                    ReserveEntity reserveEntity = new ReserveEntity();
                    reserveEntity.setUserId(UUID.fromString("08fff984-b782-4a31-8d67-c1c12b84f727"));
                    reserveEntity.setCarId(carId);
                    reserveEntity.setStartDate(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    reserveEntity.setEndDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    reserveRepository.save(reserveEntity);
                    log.info("saved into reserve table, id : {}", reserveEntity.getId());

                    // release the lock
                    matchedCarEntity.setLockVersion(newVersion);
                    matchedCarEntity.setExpireTime(null);
                    carRepository.save(matchedCarEntity);
                    log.info("car lock released, current version : {}", newVersion);
                    // response
                    UUID reserveEntityId = reserveEntity.getId();
                    log.info("create reserve order {} successfully", reserveEntityId);
                    return BaseResponse.success(ReserveResponse.builder().reserveId(reserveEntityId).build());
                } else {
                    log.warn("failed to acquire the lock for car {}, will try other cars", carId);
                }
            }
        }

        // all cars are locked, throw exception - please try again later
        log.error("Failed to acquire lock for cars in {}", carIdAndEntityMap.keySet());
        throw new CustomizedException(NO_CAR_IS_AVAILABLE);
    }

    private boolean noConflictExisted(LocalDate reqStartDate, LocalDate reqEndDate, List<ReserveEntity> orderedReserveEntityList) {
        for (ReserveEntity reserveEntity : orderedReserveEntityList) {
            LocalDate dbStartDate = reserveEntity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dbEndDate = reserveEntity.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // as it is ordered, once start date in db is even after end date in request, then no need to continue comparison
            if (dbEndDate.isAfter(reqEndDate)) {
                return true;
            }
            /*
             * startdate1 <=enddate2 and enddate1>=startdate2
             *
             * equals to
             * (startdate1 BETWEEN startdate2 AND enddate2)
             * or (enddate1 BETWEEN startdate2 AND enddate2)
             * or (startdate2 BETWEEN startdate1 AND enddate1)
             * or (enddate2 BETWEEN startdate1 AND enddate1)
             * */
            if (!(reqStartDate.isAfter(dbEndDate) || reqStartDate.isEqual(dbEndDate))
                    && !(reqEndDate.isBefore(dbStartDate) || reqEndDate.isEqual(dbStartDate))) {
                return false;
            }
        }
        return true;
    }

    private List<CarEntity> getAvailableCarByModelId(Long modelId) {
        Specification<CarEntity> specification = (Specification<CarEntity>) (root, query, cb) -> {
            Predicate modelPredicate = cb.equal(root.get("modelId"), modelId);

            Predicate expireTimeBeforePredicate = cb.lessThan(root.get("expireTime"), new Date());
            Predicate expireTimeNullPredicate = cb.isNull(root.get("expireTime"));

            Predicate expireTimePredicate = cb.or(expireTimeBeforePredicate, expireTimeNullPredicate);

            return cb.and(modelPredicate, expireTimePredicate);
        };
        return carRepository.findAll(specification);
    }
}
