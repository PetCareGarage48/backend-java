//package com.core.app.services;
//
//import com.core.app.entities.database.shelter.ShelterTokenEntity;
//import com.core.app.entities.database.user.TokenEntity;
//import org.bson.types.ObjectId;
//
//import java.util.concurrent.CompletableFuture;
//
//public interface TokenService {
//
//    String createToken(ShelterTokenEntity user);
//
//    ObjectId getUserIdFromToken(String token);
//
//    boolean isTokenValid(String token);
//
//    TokenEntity save(String token, ObjectId userId);
//
//    CompletableFuture<Void> updateTokenExpirationTime(String token);
//
//    void deleteToken(String token);
//
//    void deleteUserTokens(ObjectId userId);
//
//}
