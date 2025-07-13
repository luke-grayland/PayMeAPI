package com.LukeLabs.PayMeAPI.repositories;

import com.LukeLabs.PayMeAPI.models.entities.KYCProfileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface KYCProfileRepository extends MongoRepository<KYCProfileEntity, UUID> {
    Optional<KYCProfileEntity> findByUserId(int userId);
}
