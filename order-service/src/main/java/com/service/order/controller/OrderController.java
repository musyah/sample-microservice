package com.service.order.controller;

import com.service.order.dto.OrderRequest;
import com.service.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;


    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> createOrder(@RequestBody OrderRequest order) {
        return CompletableFuture.supplyAsync( () -> orderService.placeOrder(order));
    }

    private CompletableFuture<String> fallbackMethod(OrderRequest order, RuntimeException e) {
        return CompletableFuture.supplyAsync( () ->"Oops something is wrong due to "+e.getMessage()+" order after some time");

    }

}
