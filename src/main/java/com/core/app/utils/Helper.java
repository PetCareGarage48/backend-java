package com.core.app.utils;

import com.core.app.entities.dto.Response;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.core.app.constants.HttpConstants.AUTHORIZATION_HEADER;

@Component
public class Helper {

	private static final Logger logger = LoggerFactory.getLogger(Helper.class);

	@Autowired
	Helper() {

	}

	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public static boolean passwordsMatch(String enteredPassword, String hashedPassword) {
		return BCrypt.checkpw(enteredPassword, hashedPassword);
	}

	public static String getTokenFromHeader(HttpServletRequest request) {
		return request.getHeader(AUTHORIZATION_HEADER);
	}

	public static ResponseEntity<Response> buildHttpResponse(HttpStatus httpStatus, boolean error, String msg, Object data) {
		Response responseBody = Response.builder()
				.status(httpStatus.value())
				.error(error)
				.message(msg)
				.data(data)
				.build();
		return new ResponseEntity<>(responseBody, httpStatus);
	}
}
