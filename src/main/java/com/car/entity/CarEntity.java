package com.car.entity;

import javax.persistence.*;
import java.util.Date;
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
    private UUID lockVersion;
    private Date expireTime;

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

    @Basic
    @Column(name = "LOCK_VERSION")
    public UUID getLockVersion() {
        return lockVersion;
    }

    public void setLockVersion(UUID lockVersion) {
        this.lockVersion = lockVersion;
    }

    @Basic
    @Column(name = "EXPIRE_TIME")
    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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
                Objects.equals(modelId, carEntity.modelId) &&
                Objects.equals(lockVersion, carEntity.lockVersion) &&
                Objects.equals(expireTime, carEntity.expireTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, modelId, lockVersion, expireTime);
    }
}
