package com.car.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Vincent
 * @date 2021/03/27
 */
@Entity
@Table(name = "BRAND")
public class BrandEntity {
    private long id;
    private String brandName;

    @Id
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BRAND_NAME")
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BrandEntity that = (BrandEntity) o;
        return id == that.id &&
                Objects.equals(brandName, that.brandName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brandName);
    }
}
