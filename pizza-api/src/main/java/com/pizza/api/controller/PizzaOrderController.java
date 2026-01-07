package com.pizza.api.controller;

import com.pizza.api.exception.CustomOrderException;
import com.pizza.api.model.PizzaOrder;
import com.pizza.api.service.PizzaOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PizzaOrderController {

    private final PizzaOrderService service;

    @Autowired
    public PizzaOrderController(PizzaOrderService service) {
        this.service = service;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@Valid @RequestBody PizzaOrder order) {
        try {
            PizzaOrder createdOrder = service.createOrder(order);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (CustomOrderException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/getOrders")
    public ResponseEntity<List<PizzaOrder>> getOrders() {
        List<PizzaOrder> orders = service.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/cancelOrder")
    public ResponseEntity<Void> cancelOrder(@RequestParam int id) {
        service.cancelOrder(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<Void> deleteOrder(@RequestParam int id) {
        service.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
