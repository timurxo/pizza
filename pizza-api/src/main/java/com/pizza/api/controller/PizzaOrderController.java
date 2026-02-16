package com.pizza.api.controller;

import com.pizza.api.model.PizzaOrder;
import com.pizza.api.service.DominosApiService;
import com.pizza.api.service.PizzaOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PizzaOrderController {

    private final PizzaOrderService service;
    private final DominosApiService dominosApiService;

    @Autowired
    public PizzaOrderController(PizzaOrderService service, DominosApiService dominosApiService) {
        this.service = service;
        this.dominosApiService = dominosApiService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<PizzaOrder> createOrder(@Valid @RequestBody PizzaOrder order) {
        PizzaOrder createdOrder = service.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/getOrders")
    public ResponseEntity<List<PizzaOrder>> getOrders() {
        List<PizzaOrder> orders = service.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/getExtraPizzas")
    public ResponseEntity<List<PizzaOrder>> getExtraPizzas() {
        List<PizzaOrder> extraPizzas = dominosApiService.getExtraPizzas();
        return ResponseEntity.ok(extraPizzas);
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
