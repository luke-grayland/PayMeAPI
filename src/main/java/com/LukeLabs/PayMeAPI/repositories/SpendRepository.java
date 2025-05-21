package com.LukeLabs.PayMeAPI.repositories;

import com.LukeLabs.PayMeAPI.models.Spend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface SpendRepository extends MongoRepository<Spend, UUID> {
    @Query("{'cardId': ?0}")
    List<Spend> findSpendByCardId(UUID cardId);
}
