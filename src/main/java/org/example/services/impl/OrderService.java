package org.example.services.impl;

import org.example.dto.OrderClientDto;
import org.example.entity.Client;
import org.example.entity.Order;
import org.example.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    Order findOrder(Long id);

    void saveOrder(Order order);

    void deleteOrder(Order order);

    void updateOrder(Order order);

    List<Order> findAllOrders();

    int countOrders();

    void createPurchase(List<Product> products, Client client, BigDecimal totalPrice);

    List<OrderClientDto> ordersWithClientNameBetween(LocalDate var1, LocalDate var2);
}
