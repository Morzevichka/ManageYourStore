package org.example.repository;

import org.example.entity.Client;
import org.example.repository.impl.GenericRepository;
import org.example.annotation.Query;

import java.util.Optional;

public class ClientRepository extends GenericRepository<Client, Long> {

    public ClientRepository() {
        super(Client.class);
    }

    @Query(value = "SELECT c FROM Client c WHERE c.phone = ?1", nativeQuery = false)
    public Optional<Client> findByPhoneNumber(String phone) {
        return getSingleResultQuery(phone);
    }
}
