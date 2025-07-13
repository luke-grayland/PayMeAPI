package com.LukeLabs.PayMeAPI.repositories;

import com.LukeLabs.PayMeAPI.models.entities.SpendEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public interface SpendRepository extends MongoRepository<SpendEntity, UUID> {
    @Query("{'cardId': ?0, 'dateTime': { $gte: ?1 }}")
    List<SpendEntity> findSpendByCardIdInLastDay(UUID cardId, Date date);
}
