package com.core.app.services;

import com.core.app.entities.database.user.User;
import com.core.app.entities.dto.Response;
import com.core.app.entities.dto.UserCredentials;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

public interface UserService {

    Boolean userExists(final String email);

    User buildUser(User user);

    ResponseEntity<Response> register(User user, boolean likeAdmin);

    ResponseEntity<Response> login(UserCredentials credentials);

    ResponseEntity<Response> logout(String token);

    ResponseEntity<Response> get(String userId);

    ResponseEntity<Response> initialize();


//    ResponseEntity<Response> verifyAccountViaEmail(ObjectId userId);
//
//    ResponseEntity<Response> resendEmailVerificationLink(ObjectId userId);

}
