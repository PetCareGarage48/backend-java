package com.core.app.repositories;


import com.core.app.entities.database.user.TokenEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TokenRepository extends MongoRepository<TokenEntity, ObjectId>{

    TokenEntity findByToken(String token);

    List<TokenEntity> findByUserId(ObjectId userId);

    void deleteByUserId(ObjectId userId);

}
