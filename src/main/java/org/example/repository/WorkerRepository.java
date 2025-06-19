package org.example.repository;

import org.example.dto.WorkerSalesDto;
import org.example.entity.Worker;
import org.example.repository.impl.GenericRepository;
import org.example.annotation.Query;
import org.example.utils.HibernateUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class WorkerRepository extends GenericRepository<Worker, Long> {

    public WorkerRepository() {
        super(Worker.class);
    }

    @Query(value = "SELECT * FROM WORKERS WHERE USERNAME = ?1")
    public Optional<Worker> findByUsername(String username) {
        return getSingleResultQuery(username);
    }

    public List<WorkerSalesDto> findWorkerSalesBetween(Timestamp from, Timestamp to) {
        List<Object[]> result = HibernateUtil.getSessionFactory().fromSession(session ->
                session.createNativeQuery("""
                                               SELECT w.ID, 
                                                      w.FIRST_NAME || ' ' || w.SECOND_NAME, 
                                                      w.USERNAME, 
                                                      w.ROLE, 
                                                      SUM(o.ORDER_SUM)
                                               FROM ORDERS o
                                               LEFT JOIN WORKERS W ON o.WORKER_ID = w.ID
                                               WHERE o.PURCHASE_TIME BETWEEN ?1 AND ?2
                                               GROUP BY w.ID, w.FIRST_NAME, w.SECOND_NAME, w.USERNAME, w.ROLE
                                          """)
                        .setParameter(1, from)
                        .setParameter(2, to)
                        .getResultList()
        );

        List<WorkerSalesDto> workers = result
                .stream()
                .map(row -> new WorkerSalesDto(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        ((Number) row[4]).doubleValue()
                ))
                .toList();
        return workers;
    }
}
