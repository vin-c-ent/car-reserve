package com.car.repository;

import com.car.entity.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Repository
public interface ReserveRepository extends JpaRepository<ReserveEntity, UUID> {
    List<ReserveEntity> findAllByCarIdIn(Set<UUID> carIdList);
}
