package org.example.services;

import org.example.dto.ProductSalesDto;
import org.example.entity.OrderItem;
import org.example.repository.OrderItemRepository;
import org.example.services.impl.OrderItemService;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository = new OrderItemRepository();

    @Override
    public OrderItem findOrderItem(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(OrderItem orderitem) {
        orderItemRepository.delete(orderitem);
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        orderItemRepository.update(orderItem);
    }

    @Override
    public List<OrderItem> findAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public int totalProductQuantity() {
        return orderItemRepository.countTotalProductQuantity();
    }
}
