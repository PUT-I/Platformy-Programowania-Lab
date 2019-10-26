package com.put.lab3.persistence.entities;

import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@ToString
@Builder
@Table(name = "COFFEES", schema = "public")
public class CoffeesEntity {
    private String cofName;
    private int supId;
    private BigDecimal price;
    private int sales;
    private int total;

    public CoffeesEntity() {

    }

    public CoffeesEntity(String cofName, int supId, BigDecimal price, int sales, int total) {
        this.cofName = cofName;
        this.supId = supId;
        this.price = price;
        this.sales = sales;
        this.total = total;
    }

    @Id
    @Column(name = "COF_NAME")
    public String getCofName() {
        return cofName;
    }

    public void setCofName(String cofName) {
        this.cofName = cofName;
    }

    @Basic
    @Column(name = "SUP_ID")
    public int getSupId() {
        return supId;
    }

    public void setSupId(int supId) {
        this.supId = supId;
    }

    @Basic
    @Column(name = "PRICE")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "SALES")
    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    @Basic
    @Column(name = "TOTAL")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoffeesEntity that = (CoffeesEntity) o;
        return sales == that.sales &&
                total == that.total &&
                Objects.equals(cofName, that.cofName) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cofName, price, sales, total);
    }
}
