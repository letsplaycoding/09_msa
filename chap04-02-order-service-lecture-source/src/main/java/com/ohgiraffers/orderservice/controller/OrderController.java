package com.ohgiraffers.orderservice.controller;

import com.ohgiraffers.orderservice.dto.OrderDTO;
import com.ohgiraffers.orderservice.service.OrderService;
import com.ohgiraffers.orderservice.vo.ResponseOrder;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/users/{userId}")
    public ResponseEntity<List<ResponseOrder>> getUserOrders(@PathVariable("userId") String userId) {
        List<OrderDTO> orderDTOList = orderService.getUserOrders(userId);
        
        List<ResponseOrder> returnValue = orderDTOResponseOrder(orderDTOList);

//        return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    private List<ResponseOrder> orderDTOResponseOrder(List<OrderDTO> orderDTOList) {
        List<ResponseOrder> responseList = new ArrayList<>();

        for (OrderDTO orderDTO : orderDTOList) {
            ResponseOrder responseOrder = new ResponseOrder();
            responseOrder.setOrderDate(orderDTO.getOrderDate());
            responseOrder.setOrderTime(orderDTO.getOrderTime());
            responseOrder.setOrderMenus(orderDTO.getOrderMenus());

            responseList.add(responseOrder);
        }

        return responseList;
    }

}
