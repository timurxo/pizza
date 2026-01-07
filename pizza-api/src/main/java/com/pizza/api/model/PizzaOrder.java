package com.pizza.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PizzaOrder {

    private int id;

    @NotBlank(message = "crust is required")
    private String crust;

    @NotBlank(message = "topping is required")
    private String topping;

    @NotBlank(message = "size is required")
    private String size;

    @NotNull(message = "table number is required")
    private int tableNo;

    private String status;

    private LocalDateTime createdAt;

    public PizzaOrder() {
    }

    public PizzaOrder(int id, String crust, String topping, String size, Integer tableNo, String status,
            LocalDateTime createdAt) {
        this.id = id;
        this.crust = crust;
        this.topping = topping;
        this.size = size;
        this.tableNo = tableNo;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
