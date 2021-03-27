package com.car.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Entity
@Table(name = "MODEL")
public class ModelEntity {
    private Long id;
    private String modelName;
    private Long brandId;

    @Id
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MODEL_NAME")
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Basic
    @Column(name = "BRAND_ID")
    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelEntity that = (ModelEntity) o;
        return id.equals(that.id) &&
                Objects.equals(modelName, that.modelName) &&
                Objects.equals(brandId, that.brandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, modelName, brandId);
    }
}
