//package com.core.app.services;
//
//
//import com.core.app.entities.database.User;
//import com.core.app.entities.dto.Response;
//import org.bson.types.ObjectId;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Optional;
//
//public interface AdminService {
//
//	void registerSuperAdminAccount();
//
//	ResponseEntity<Response> changeSuspendedStatus(ObjectId userId);
//
//	ResponseEntity<Response> getAllUsersPage(Optional<Integer> page, Optional<Integer> size);
//
//	ResponseEntity<Response> deleteUserAccount(ObjectId userId);
//
//	ResponseEntity<Response> addNewAdmin(String token, User user);
//}
