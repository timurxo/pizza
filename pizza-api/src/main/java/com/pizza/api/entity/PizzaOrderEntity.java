package com.pizza.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pizza_orders")
public class PizzaOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "crust", nullable = false)
    private String crust;

    @Column(name = "topping", nullable = false)
    private String topping;

    @Column(name = "pizza_size", nullable = false)
    private String size;

    @Column(name = "table_no", nullable = false)
    private int tableNo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status")
    private String status;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public PizzaOrderEntity() {
    }

    public PizzaOrderEntity(int id, String crust, String topping, String size, Integer tableNo,
            LocalDateTime createdAt) {
        this.id = id;
        this.crust = crust;
        this.topping = topping;
        this.size = size;
        this.tableNo = tableNo;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCrust() {
        return crust;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
