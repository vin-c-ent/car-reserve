package com.car.control;

import com.car.dto.request.ReserveRequest;
import com.car.dto.response.BaseResponse;
import com.car.dto.response.ReserveResponse;
import com.car.entity.CarEntity;
import com.car.entity.ReserveEntity;
import com.car.exception.CustomizedException;
import com.car.repository.CarRepository;
import com.car.repository.ReserveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static com.car.constants.CommonConstants.YYYYMMDD;
import static com.car.exception.ReserveExceptionInfoEnums.*;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Slf4j
@RestController
@RequestMapping("/")
public class ReserveController {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ReserveRepository reserveRepository;

    @PostMapping(value = "/reserve")
    public ResponseEntity reserve(@Valid @RequestBody ReserveRequest reserveRequest) {
        Long modelId = reserveRequest.getModelId();
        LocalDate startDate = LocalDate.parse(reserveRequest.getStartDate(), DateTimeFormatter.ofPattern(YYYYMMDD));
        LocalDate endDate = LocalDate.parse(reserveRequest.getEndDate(), DateTimeFormatter.ofPattern(YYYYMMDD));

        this.validateDate(startDate, endDate);

        List<CarEntity> carEntityList = carRepository.findAllByModelId(modelId);

        for (CarEntity carEntity : carEntityList) {
            List<ReserveEntity> orderedReserveEntityList = reserveRepository.findAllByCarIdOrderByStartDate(carEntity.getId());
            if (this.noConflictExisted(startDate, endDate, orderedReserveEntityList)) {
                // save db
                ReserveEntity reserveEntity = new ReserveEntity();
                reserveEntity.setUserId(UUID.fromString("08fff984-b782-4a31-8d67-c1c12b84f727"));
                reserveEntity.setCarId(carEntity.getId());
                reserveEntity.setStartDate(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                reserveEntity.setEndDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                reserveRepository.save(reserveEntity);
                // response
                UUID reserveEntityId = reserveEntity.getId();
                log.info("create reserve order {} successfully", reserveEntityId);
                return ResponseEntity.ok(BaseResponse.success(ReserveResponse.builder().reserveId(reserveEntityId).build()));
            }
        }

        throw new CustomizedException(NO_CAR_IS_AVAILABLE);
    }

    private void validateDate(LocalDate startDate, LocalDate endDate) {
        // start date need to before end date
        if (startDate.isEqual(endDate) || startDate.isAfter(endDate)) {
            throw new CustomizedException(START_DATE_AFTER_END_DATE);
        }
        // book date can`t exceed current date
        if (startDate.isBefore(LocalDate.now())) {
            throw new CustomizedException(START_DATE_BEFORE_NOW);
        }
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
            if (!reqStartDate.isAfter(dbEndDate) && !reqEndDate.isBefore(dbStartDate)) {
                return false;
            }
        }
        return true;
    }
}
