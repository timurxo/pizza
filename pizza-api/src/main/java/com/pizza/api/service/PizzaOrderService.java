package com.pizza.api.service;

import com.pizza.api.entity.PizzaOrderEntity;
import com.pizza.api.exception.CustomOrderException;
import com.pizza.api.model.PizzaOrder;
import com.pizza.api.repository.PizzaOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PizzaOrderService {

    private final PizzaOrderRepository repository;

    @Autowired
    public PizzaOrderService(PizzaOrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PizzaOrder createOrder(PizzaOrder order) {
        LocalDateTime fifteenMinutesAgo = LocalDateTime.now().minusMinutes(15);
        boolean hasActiveOrder = repository.existsByTableNoAndCreatedAtAfterAndStatusNot(
                order.getTableNo(),
                fifteenMinutesAgo,
                "CANCELLED");

        if (hasActiveOrder) {
            throw new CustomOrderException("Table " + order.getTableNo() + " already had order in the last 15 mins");
        }

        PizzaOrderEntity entity = mapToEntity(order);
        PizzaOrderEntity savedEntity = repository.save(entity);
        return mapToModel(savedEntity);
    }

    public List<PizzaOrder> getAllOrders() {
        return repository.findAll().stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelOrder(int id) {
        PizzaOrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order " + id + " doesn't exist"));

        entity.setStatus("CANCELLED");
        repository.save(entity);
    }

    @Transactional
    public void deleteOrder(int id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Order " + id + " doesn't exist");
        }
        repository.deleteById(id);
    }

    private PizzaOrderEntity mapToEntity(PizzaOrder model) {
        PizzaOrderEntity entity = new PizzaOrderEntity();
        entity.setId(model.getId());
        entity.setCrust(model.getCrust());
        entity.setTopping(model.getTopping());
        entity.setSize(model.getSize());
        entity.setTableNo(model.getTableNo());
        entity.setStatus(model.getStatus());
        entity.setCreatedAt(model.getCreatedAt());
        return entity;
    }

    private PizzaOrder mapToModel(PizzaOrderEntity entity) {
        return new PizzaOrder(
                entity.getId(),
                entity.getCrust(),
                entity.getTopping(),
                entity.getSize(),
                entity.getTableNo(),
                entity.getStatus(),
                entity.getCreatedAt());
    }
}
