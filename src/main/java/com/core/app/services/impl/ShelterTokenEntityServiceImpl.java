package com.core.app.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.core.app.entities.database.shelter.ShelterAdmin;
import com.core.app.entities.database.shelter.ShelterTokenEntity;
import com.core.app.repositories.ShelterTokenRepository;
import com.core.app.services.ShelterTokenService;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import static com.core.app.constants.GeneralConstants.SHELTER_ID;
import static com.core.app.constants.GeneralConstants.TOKEN_SECRET;

public class ShelterTokenEntityServiceImpl implements ShelterTokenService {

    private final ShelterTokenRepository shelterTokenRepository;

    @Autowired
    public ShelterTokenEntityServiceImpl(ShelterTokenRepository shelterTokenRepository) {
        this.shelterTokenRepository = shelterTokenRepository;
    }


    @Override
    public String createToken(ShelterAdmin admin) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            return JWT.create()
                    .withClaim(SHELTER_ID, admin.getId().toHexString())
                    .withIssuedAt(new Date())
                    .sign(algorithm);
        } catch (UnsupportedEncodingException | JWTCreationException exception) {
        }
        return null;
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
            ShelterTokenEntity tokenEntity = shelterTokenRepository.findByToken(token);
            return tokenEntity != null && tokenEntity.getExpiresAt().isAfter(new DateTime());
        } else {
            return false;
        }
    }

    @Override
    public ShelterTokenEntity save(String token, ObjectId shelterId) {
        ShelterTokenEntity tokenEntity = ShelterTokenEntity.builder()
                .token(token)
                .shelterId(shelterId)
                .createdAt(new DateTime())
                .updatedAt(new DateTime())
                .expiresAt(new DateTime(new Date().getTime() + (1000 * 60 * 60 * 24 * 7)))
                .build();
        return shelterTokenRepository.save(tokenEntity);
    }

    @Override
    public CompletableFuture<Void> updateTokenExpirationTime(String token) {
        ShelterTokenEntity tokenEntity = shelterTokenRepository.findByToken(token);
        tokenEntity.setExpiresAt(new DateTime(new Date().getTime() + (1000 * 60 * 60 * 24 * 7)));
        tokenEntity.setUpdatedAt(new DateTime());
        shelterTokenRepository.save(tokenEntity);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void deleteToken(String token) {
        ShelterTokenEntity tokenEntity = shelterTokenRepository.findByToken(token);
        shelterTokenRepository.delete(tokenEntity);
    }

    @Override
    public void deleteUserTokens(ObjectId shelterId) {
        shelterTokenRepository.deleteByShelterId(shelterId);
    }

    private DecodedJWT decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (UnsupportedEncodingException | JWTVerificationException exception) {
        }
        return null;
    }
}
