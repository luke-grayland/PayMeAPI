package com.LukeLabs.PayMeAPI.repositories;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.scheduling.annotation.Async;

import com.LukeLabs.PayMeAPI.models.Card;

public interface CardRepository extends MongoRepository<Card, UUID> {
    @Override
    public <S extends Card> S save(S entity);

    @Async
    @Query("{'userID': ?0}")
    public CompletableFuture<List<Card>> findByUserIDAsync(int userID);
}
