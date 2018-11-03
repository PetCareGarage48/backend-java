package com.core.app.services.impl;

import com.auth0.jwt.JWT;
import com.core.app.constants.HttpConstants;
import com.core.app.entities.Role;
import com.core.app.entities.database.User;
import com.core.app.entities.dto.Response;
import com.core.app.repositories.UserRepository;
import com.core.app.services.AdminService;
import com.core.app.services.TokenService;
import com.core.app.services.UserService;
import com.core.app.utils.Helper;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.core.app.constants.GeneralConstants.*;
import static com.core.app.constants.GeneralConstants.SUCCESS;
import static com.core.app.constants.HttpConstants.*;

@Service
public class AdminServiceImpl implements AdminService {

	private final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	private final UserRepository userRepository;
	private final UserService userService;
	private final TokenService tokenService;

	@Value("${admin.email}")
	private String superAdminEmail;
	@Value("${admin.password}")
	private String superAdminPassword;

	@Autowired
	AdminServiceImpl(UserRepository userRepository,
	                 UserService userService,
	                 TokenService tokenService) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.tokenService = tokenService;
	}

	@Override
	public void registerSuperAdminAccount() {
		DateTime registrationTime = new DateTime();
		if (!userService.userExists(superAdminEmail)) {
			userRepository.save(User
					.builder()
					.role(Role.SUPER_ADMIN)
					.email(superAdminEmail)
					.password(Helper.hashPassword(superAdminPassword))
					.createdAt(registrationTime)
					.lastActivity(registrationTime)
					.emailVerified(TRUE)
					.suspended(FALSE)
					.build());
		}
	}

	@Override
	public ResponseEntity<Response> getAllUsersPage(Optional<Integer> page, Optional<Integer> size) {
		String message;
		try {
			Page<User> users = userRepository.findByRole(
					Role.USER.name(),
					PageRequest.of(page.orElse(DEFAULT_PAGE), size.orElse(DEFAULT_SIZE))
			);
			return Helper.buildHttpResponse(HttpStatus.OK, FALSE, SUCCESS, users);
		} catch (Exception e) {
			message = e.getMessage();
			logger.error(message);
		}
		return Helper.buildHttpResponse(HttpStatus.BAD_REQUEST, TRUE, message, null);
	}

	@Override
	public ResponseEntity<Response> changeSuspendedStatus(ObjectId userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			user.setSuspended(!user.isSuspended());
			userRepository.save(user);
			if (user.isSuspended()) {
				tokenService.deleteUserTokens(userId);
			}
			return Helper.buildHttpResponse(HttpStatus.OK, FALSE, HttpConstants.ACCOUNT_STATUS_CHANGED, user);
		}
		return Helper.buildHttpResponse(HttpStatus.NOT_FOUND, TRUE, HttpConstants.USER_NOT_FOUND, null);
	}

	@Override
	public ResponseEntity<Response> deleteUserAccount(ObjectId userId) {
		User user = userRepository.findById(userId).orElse(null);
		return user != null
				? proceedWithDeletingAccount(user)
				: Helper.buildHttpResponse(HttpStatus.NOT_FOUND, TRUE, USER_NOT_FOUND, null);
	}

	@Override
	public ResponseEntity<Response> addNewAdmin(String token, User user) {
		if (JWT.decode(token).getClaim(USER_ROLE).asString().equals(Role.SUPER_ADMIN.name())) {
			user.setEmail(user.getEmail().toLowerCase());
			if (userService.userExists(user.getEmail())) {
				return Helper.buildHttpResponse(HttpStatus.BAD_REQUEST, TRUE, USER_EXISTS, null);
			} else {
				user = userService.buildUser(user);
				user.setRole(Role.ADMIN);
				user.setEmailVerified(TRUE);
				user = userRepository.save(user);
				return Helper.buildHttpResponse(HttpStatus.OK, FALSE, ADMIN_ACCOUNT_CREATED, user);
			}
		} else {
			return Helper.buildHttpResponse(HttpStatus.FORBIDDEN, TRUE, FORBIDDEN, null);
		}
	}

	private ResponseEntity<Response> proceedWithDeletingAccount(User user) {
		tokenService.deleteUserTokens(user.getId());
		userRepository.delete(user);
		return !userService.userExists(user.getEmail())
				? Helper.buildHttpResponse(HttpStatus.OK, FALSE, ACCOUNT_DELETED, null)
				: Helper.buildHttpResponse(HttpStatus.OK, TRUE, FAILED_TO_DELETE_ACCOUNT, null);
	}
}
