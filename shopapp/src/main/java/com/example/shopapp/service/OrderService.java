package com.example.shopapp.service;

import com.example.shopapp.dto.OrderDTO;
import com.example.shopapp.entity.Order;
import com.example.shopapp.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OrderService {
    Order createOrder(OrderDTO productDTO) throws DataNotFoundException;

    Order getOrderById(Long id) throws DataNotFoundException;

    Page<Order> getAllOrder(PageRequest pageRequest);

    Order updateOrder(Long id, OrderDTO productDTO) throws DataNotFoundException;

    String deleteOrder (Long productId);

    boolean existByName(String name);

//    OrderImage createOrderImage(Long productId, OrderImageDTO productImageDTO) throws Exception;
}
