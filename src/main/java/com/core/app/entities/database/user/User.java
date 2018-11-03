package com.core.app.entities.database.user;

import com.core.app.entities.Role;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	private Role role;
	private ObjectId shelterId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
