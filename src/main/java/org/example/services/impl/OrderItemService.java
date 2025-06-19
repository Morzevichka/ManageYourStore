package org.example.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.example.dto.ProductSalesDto;
import org.example.entity.OrderItem;

public interface OrderItemService {
    OrderItem findOrderItem(Long id);

    void saveOrderItem(OrderItem orderItem);

    void deleteOrderItem(OrderItem orderitem);

    void updateOrderItem(OrderItem orderItem);

    List<OrderItem> findAllOrderItems();

    int totalProductQuantity();
}
