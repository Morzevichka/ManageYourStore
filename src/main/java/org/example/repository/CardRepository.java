package org.example.repository;

import org.example.entity.Card;
import org.example.repository.impl.GenericRepository;
import org.example.annotation.Query;

import java.util.Optional;

public class CardRepository extends GenericRepository<Card, Long> {

    public CardRepository() {
        super(Card.class);
    }

    @Query(value = "SELECT * FROM CARDS WHERE NAME = ?1")
    public Optional<Card> findByName(String name) {
        return getSingleResultQuery(name);
    }
}
