package com.car.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Entity
@Table(name = "CAR")
public class CarEntity {
    private UUID id;
    private Long modelId;

    @Id
    @Column(name = "ID")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MODEL_ID")
    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarEntity carEntity = (CarEntity) o;
        return Objects.equals(id, carEntity.id) &&
                Objects.equals(modelId, carEntity.modelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, modelId);
    }
}
