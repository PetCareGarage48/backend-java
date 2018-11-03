package com.core.app.services;

import com.core.app.entities.database.user.TokenEntity;
import com.core.app.entities.database.User;
import org.bson.types.ObjectId;

import java.util.concurrent.CompletableFuture;

public interface TokenService {

    String createToken(User user);

    ObjectId getUserIdFromToken(String token);

    boolean isTokenValid(String token);

    TokenEntity save(String token, ObjectId userId);

    CompletableFuture<Void> updateTokenExpirationTime(String token);

    void deleteToken(String token);

    void deleteUserTokens(ObjectId userId);

}
