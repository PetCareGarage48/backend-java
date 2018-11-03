package com.core.app.services;

import com.core.app.entities.database.shelter.ShelterAdmin;
import com.core.app.entities.dto.Response;
import com.core.app.entities.dto.UserCredentials;
import com.core.app.repositories.ShelterAdminRepository;
import org.springframework.http.ResponseEntity;

public interface ShelterAdminService {

    Boolean shelterAdminExists(final String email);

    ResponseEntity<Response> buildShelterAdmin(ShelterAdmin user);

    ResponseEntity<Response> register(ShelterAdmin user);

    ResponseEntity<Response> login(UserCredentials credentials);

    ResponseEntity<Response> logout(String token);

    ResponseEntity<Response> get(String userId);
}
