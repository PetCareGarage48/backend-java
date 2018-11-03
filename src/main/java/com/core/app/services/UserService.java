package com.core.app.services;


import com.core.app.entities.database.User;
import com.core.app.entities.dto.Response;
import com.core.app.entities.dto.UserCredentials;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {

	Boolean userExists(final String email);

	User buildUser(User user);

	ResponseEntity<Response> register(User user);

	ResponseEntity<Response> login(UserCredentials credentials);

	ResponseEntity<Response> logout(String token);

	ResponseEntity<Response> get(String userId);

	ResponseEntity<Response> verifyAccountViaEmail(ObjectId userId);

	ResponseEntity<Response> resendEmailVerificationLink(ObjectId userId);


}
