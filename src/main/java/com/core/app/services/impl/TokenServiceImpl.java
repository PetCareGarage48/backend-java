package com.core.app.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.core.app.entities.database.user.TokenEntity;
import com.core.app.entities.database.user.User;
import com.core.app.repositories.TokenRepository;
import com.core.app.services.TokenService;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import static com.core.app.constants.GeneralConstants.*;

@Service
public class TokenServiceImpl implements TokenService{

    private final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private final TokenRepository tokenRepository;

    @Autowired
    TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String createToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            if(user.getShelterId() != null) {
                JWT.create()
                        .withClaim(USER_ID, user.getId().toHexString())
                        .withClaim(USER_ROLE, user.getRole().name())
                        .withClaim(SHELTER_ID, user.getShelterId().toHexString())
                        .withIssuedAt(new Date())
                        .sign(algorithm);
            } else {
                return JWT.create()
                        .withClaim(USER_ID, user.getId().toHexString())
                        .withClaim(USER_ROLE, user.getRole().name())
                        .withIssuedAt(new Date())
                        .sign(algorithm);
            }

        } catch (UnsupportedEncodingException | JWTCreationException exception) {
            logger.error(exception.getMessage());
        }
        return null;
    }

    @Override
    public ObjectId getUserIdFromToken(String token) {
        DecodedJWT jwt = decodeToken(token);
        return jwt != null ? new ObjectId(jwt.getClaim(USER_ID).asString()) : null;
    }

    @Override
    public ObjectId getShelterIdFromToken(String token) {
        DecodedJWT jwt = decodeToken(token);
        return jwt != null ? new ObjectId(jwt.getClaim(SHELTER_ID).asString()) : null;
    }

    @Override
    public boolean isTokenValid(String token) {
        DecodedJWT jwt = decodeToken(token);
        if (jwt != null) {
            TokenEntity tokenEntity = tokenRepository.findByToken(token);
            return tokenEntity != null && tokenEntity.getExpiresAt().isAfter(new DateTime());
        } else {
            return false;
        }
    }

    @Override
    public TokenEntity save(String token, ObjectId userId) {
        System.out.println(userId);
        TokenEntity tokenEntity = TokenEntity.builder()
                .token(token)
                .userId(userId)
                .createdAt(new DateTime())
                .updatedAt(new DateTime())
                .expiresAt(new DateTime(new Date().getTime() + (1000 * 60 * 60 * 24 * 7)))
                .build();
        return tokenRepository.save(tokenEntity);
    }

    @Async
    @Override
    public CompletableFuture<Void> updateTokenExpirationTime(String token) {
        TokenEntity tokenEntity = tokenRepository.findByToken(token);
        tokenEntity.setExpiresAt(new DateTime(new Date().getTime() + (1000 * 60 * 60 * 24 * 7)));
        tokenEntity.setUpdatedAt(new DateTime());
        tokenRepository.save(tokenEntity);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void deleteToken(String token) {
        TokenEntity tokenEntity = tokenRepository.findByToken(token);
        tokenRepository.delete(tokenEntity);
    }

    @Override
    public void deleteTokens(ObjectId userId) {
        tokenRepository.deleteByUserId(userId);
    }

    private DecodedJWT decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (UnsupportedEncodingException | JWTVerificationException exception) {
            logger.error(exception.getMessage());
        }
        return null;
    }

}
