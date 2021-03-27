package com.car.repository;

import com.car.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

}
