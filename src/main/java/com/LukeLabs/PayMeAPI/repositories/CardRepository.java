package com.LukeLabs.PayMeAPI.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.LukeLabs.PayMeAPI.models.Card;

public interface CardRepository extends MongoRepository<Card, UUID> {
    List<Card> findByUserID(int userID);
}
