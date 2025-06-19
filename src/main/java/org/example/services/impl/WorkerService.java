package org.example.services.impl;

import org.example.dto.WorkerSalesDto;
import org.example.entity.Role;
import org.example.entity.Worker;

import javax.naming.AuthenticationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface WorkerService {
    Worker findWorker(Long id);

    void saveWorker(Worker worker);

    void deleteWorker(Worker worker);

    void updateWorker(Worker worker);

    List<Worker> findAllWorkers();

    Worker findWorkerByUsername(String name);

    Worker loginWorker(String username, String password) throws AuthenticationException;

    void registerWorker(String firstName, String secondName, String username, String password, Role role, BigDecimal salary);

    void changePassword(String username, String currentPassword, String newPassword);

    List<WorkerSalesDto> workerSalesBetween(LocalDate var1, LocalDate var2);

}
