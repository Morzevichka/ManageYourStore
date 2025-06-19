package org.example.repository;

import org.example.dto.OrderClientDto;
import org.example.entity.Order;
import org.example.repository.impl.GenericRepository;
import org.example.annotation.Query;
import org.example.utils.HibernateUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class OrderRepository extends GenericRepository<Order, Long> {

    public OrderRepository() {
        super(Order.class);
    }

    public int countAllOrders() {
        return ((Number) HibernateUtil.getSessionFactory().fromSession(session ->
                session.createNativeQuery("SELECT COUNT(*) FROM ORDERS").uniqueResult()
        )).intValue();
    }

    public List<OrderClientDto> findOrdersWithClientNameBetween(Timestamp from, Timestamp to) {
        List<Object[]> result = HibernateUtil.getSessionFactory().fromSession(session ->
                session.createNativeQuery("""
                                                SELECT o.ID, 
                                                       o.WORKER_ID, 
                                                       c.FIRST_NAME || ' ' || c.SECOND_NAME, 
                                                       o.PURCHASE_TIME, 
                                                       o.ORDER_SUM
                                                FROM ORDERS o
                                                LEFT JOIN CLIENTS c ON c.ID = o.CLIENT_ID
                                                WHERE o.PURCHASE_TIME BETWEEN ?1 AND ?2
                                           """)
                        .setParameter(1, from)
                        .setParameter(2, to)
                        .getResultList()
        );

        List<OrderClientDto> orders = result
                .stream()
                .map(row -> new OrderClientDto(
                        ((Number) row[0]).longValue(),
                        ((Number) row[1]).longValue(),
                        (String) row[2],
                        (Timestamp) row[3],
                        ((BigDecimal) row[4]).doubleValue()
                ))
                .toList();
        return orders;
    }
}