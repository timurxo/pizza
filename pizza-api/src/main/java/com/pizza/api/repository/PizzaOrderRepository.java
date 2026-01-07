package com.pizza.api.repository;

import com.pizza.api.entity.PizzaOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PizzaOrderRepository extends JpaRepository<PizzaOrderEntity, Integer> {

    boolean existsByTableNoAndCreatedAtAfterAndStatusNot(int tableNo, LocalDateTime timestamp, String status);

}
