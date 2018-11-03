//package com.core.app.entities.database;
//
//import com.core.app.constants.GeneralConstants;
//import com.core.app.entities.Role;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.bson.types.ObjectId;
//import org.joda.time.DateTime;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Document(collection = GeneralConstants.USERS)
//public class User {
//
//	@JsonSerialize(using = ToStringSerializer.class)
//	private ObjectId id;
//	private Role role;
//	private String firstName;
//	private String lastName;
//	private String email;
//	private String password;
//	private DateTime createdAt;
//	private DateTime lastActivity;
//	private boolean emailVerified;
//	private DateTime verifyingLinkSendingTime;
//	private boolean suspended;
//
//}
