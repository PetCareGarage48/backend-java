package com.core.app.repositories;

import com.core.app.entities.database.shelter.ShelterTokenEntity;
import com.core.app.entities.database.user.TokenEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterTokenRepository extends MongoRepository <ShelterTokenEntity,ObjectId> {
    ShelterTokenEntity findByToken(String token);

    List<ShelterTokenEntity> findByShelterId(ObjectId shelterId);

    void deleteByShelterId(ObjectId shelterId);
}
