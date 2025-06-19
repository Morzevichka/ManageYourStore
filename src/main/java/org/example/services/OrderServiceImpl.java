package org.example.services;

import jakarta.transaction.Transactional;
import org.example.dto.OrderClientDto;
import org.example.dto.UserSesssion;
import org.example.entity.Client;
import org.example.entity.Order;
import org.example.entity.OrderItem;
import org.example.entity.Product;
import org.example.repository.*;
import org.example.services.impl.OrderService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository = new OrderRepository();
    private final ProductRepository productRepository = new ProductRepository();

    @Override
    public Order findOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.update(order);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public int countOrders() {
        return orderRepository.countAllOrders();
    }

    @Override
    @Transactional
    public void createPurchase(List<Product> products, Client client, BigDecimal totalPrice) {
        if (products.isEmpty() || totalPrice.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Нулевая стоимость или не добавлены товары");
        }
        HashMap<Product, Integer> productsHashMap = new HashMap<>();
        Order order = new Order();

        for (Product product : products) {
            if (product.getQuantity() <= 0) {
                throw new IllegalStateException("Нет в наличии: " + product.getName());
            }
            productsHashMap.merge(product, 1, Integer::sum);
        }

        Set<OrderItem> orderItems = new HashSet<>();
        for (Map.Entry<Product, Integer> productsEntry : productsHashMap.entrySet()) {
            Product product = productsEntry.getKey();
            Integer quantity = productsEntry.getValue();
            product.setQuantity(product.getQuantity() - quantity);

            orderItems.add(new OrderItem(order, product, quantity));

            productRepository.update(product);
        }

        order.setOrderSum(totalPrice);
        if (client != null) {
            order.setClient(client);
        }
        order.setPurchaseTime(Timestamp.valueOf(LocalDateTime.now()));
        order.setWorker(UserSesssion.getInstance().getWorker());
        order.setOrderItems(orderItems);
        orderRepository.save(order);
    }

    public List<OrderClientDto> ordersWithClientNameBetween(LocalDate var1, LocalDate var2) {
        Timestamp from = var1 != null ? Timestamp.valueOf(LocalDateTime.of(var1, LocalTime.MIDNIGHT)) : Timestamp.from(Instant.EPOCH);
        Timestamp to = var2 != null ? Timestamp.valueOf(LocalDateTime.of(var2, LocalTime.MIDNIGHT)) : Timestamp.valueOf(LocalDateTime.now());

        return orderRepository.findOrdersWithClientNameBetween(from, to);
    }
}
