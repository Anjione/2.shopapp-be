package com.example.shopapp.service.impl;

import com.example.shopapp.dto.OrderDTO;
import com.example.shopapp.entity.Order;
import com.example.shopapp.entity.User;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.repository.OrderRepository;
import com.example.shopapp.repository.UserRepository;
import com.example.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper; 

    @Override
    public Order createOrder(OrderDTO productDTO) throws DataNotFoundException {
        User user = userRepository.findById(productDTO.getUserId()).orElseThrow(() -> new DataNotFoundException("User not found"));
        return null;
    }

    @Override
    public Order getOrderById(Long id) throws DataNotFoundException {
        return null;
    }

    @Override
    public Page<Order> getAllOrder(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Order updateOrder(Long id, OrderDTO productDTO) throws DataNotFoundException {
        return null;
    }

    @Override
    public String deleteOrder(Long productId) {
        return "";
    }

    @Override
    public boolean existByName(String name) {
        return false;
    }
}
