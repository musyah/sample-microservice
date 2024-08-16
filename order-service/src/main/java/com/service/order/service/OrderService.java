package com.service.order.service;

import com.service.order.dto.InventoryResponse;
import com.service.order.dto.OrderLineItemsDTO;
import com.service.order.dto.OrderRequest;
import com.service.order.entity.Order;
import com.service.order.entity.OrderLineItems;
import com.service.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    @Autowired
    private final WebClient.Builder webClient;

    private final OrderRepository orderRepository ;
    public String placeOrder(OrderRequest request) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

     List<OrderLineItems> orderLineItems  = request.getItems().stream().map(this::mapToDto).toList();

     if(!checkValidity(orderLineItems.stream().map(OrderLineItems::getSkuCode).toList()))
         return "out of stock";

     order.setOrderLineItemsList(orderLineItems);

     orderRepository.save(order);
     return "order placed successfully";
    }

    private boolean checkValidity(List<String> orderLineItems) {
        return Arrays.stream(webClient.build().get()
                .uri("http://inventory/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCodes",orderLineItems).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class).block()).allMatch(InventoryResponse::isAvailable)  ;
    }

    private OrderLineItems mapToDto(OrderLineItemsDTO orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
