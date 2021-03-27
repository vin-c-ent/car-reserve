package com.car.repository;

import com.car.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Repository
public interface CarRepository extends JpaRepository<CarEntity, String> {
    List<CarEntity> findAllByModelId(Long modelId);
}
