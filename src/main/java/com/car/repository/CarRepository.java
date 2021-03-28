package com.car.repository;

import com.car.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID>, JpaSpecificationExecutor<CarEntity> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update CarEntity c set c.lockVersion=?1,c.expireTime=?2 where c.id=?3 and c.lockVersion=?4")
    int lock(UUID newVersion, Date expireDate, UUID carId, UUID previousVersion);
}
