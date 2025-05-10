package com.LukeLabs.PayMeAPI.repositories;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.LukeLabs.PayMeAPI.models.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.scheduling.annotation.Async;

public interface CardRepository extends MongoRepository<Card, UUID> {
    @Async
    @Query("{'userID': ?0}")
    CompletableFuture<List<Card>> findByUserIDAsync(int userID);
}
