package com.core.app.services;

import com.core.app.entities.database.shelter.ShelterAdmin;
import com.core.app.entities.database.shelter.ShelterTokenEntity;
import org.bson.types.ObjectId;

import java.util.concurrent.CompletableFuture;

public interface ShelterTokenService {

    String createToken(ShelterAdmin user);

    ObjectId getShelterIdFromToken(String token);

    boolean isTokenValid(String token);

    ShelterTokenEntity save(String token, ObjectId userId);

    CompletableFuture<Void> updateTokenExpirationTime(String token);

    void deleteToken(String token);

    void deleteUserTokens(ObjectId userId);
}
