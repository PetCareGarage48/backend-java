package com.core.app.services.impl;

import com.core.app.entities.Role;
import com.core.app.entities.database.shelter.Shelter;
import com.core.app.entities.database.user.GlobalAppSettings;
import com.core.app.entities.database.user.TokenEntity;
import com.core.app.entities.database.user.User;
import com.core.app.entities.dto.Response;
import com.core.app.entities.dto.UserCredentials;
import com.core.app.repositories.UserRepository;
import com.core.app.services.*;
import com.core.app.utils.Helper;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.core.app.constants.GeneralConstants.*;
import static com.core.app.constants.HttpConstants.*;

@Service
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private final UserRepository userRepository;
	private final GlobalAppSettingsService settingsService;
	private final TokenService tokenService;
	private final MailService mailService;
	private final ShelterService shelterService;


	@Autowired
	UserServiceImpl(UserRepository userRepository,
                    GlobalAppSettingsService settingsService,
                    TokenService tokenService,
                    MailService mailService, ShelterService shelterService) {
		this.userRepository = userRepository;
		this.settingsService = settingsService;
		this.tokenService = tokenService;
		this.mailService = mailService;
        this.shelterService = shelterService;
    }

	@Override
	public ResponseEntity<Response> register(User user, boolean likeAdmin) {
		GlobalAppSettings appSettings = settingsService.getAppSettings();
		user.setEmail(emailToLowerCase(user));
		return !userExists(user.getEmail())
				? proceedRegistration(appSettings, user, likeAdmin)
				: Helper.buildHttpResponse(HttpStatus.CONFLICT, TRUE, USER_EXISTS, null);
	}

//	@Override
//	public ResponseEntity<Response> verifyAccountViaEmail(ObjectId userId) {
//		ResponseEntity<Response> apiResponse;
//		User user = userRepository.findById(userId).orElse(null);
//		if (user != null) {
//			if (user.isEmailVerified()) {
//				return Helper.buildHttpResponse(HttpStatus.BAD_REQUEST, TRUE, ACCOUNT_ALREADY_VERIFIED, null);
//			}
//			return isVerificationLinkExpired(user.getVerifyingLinkSendingTime())
//					? Helper.buildHttpResponse(HttpStatus.UNAUTHORIZED, TRUE, VERIFICATION_LINK_EXPIRED, resendVerificationUrl + userId)
//					: completeEmailVerification(user);
//		} else {
//			apiResponse = Helper.buildHttpResponse(HttpStatus.NOT_FOUND, TRUE, ACCOUNT_MISSING, null);
//		}
//		return apiResponse;
//	}
//
//	@Override
//	public ResponseEntity<Response> resendEmailVerificationLink(ObjectId userId) {
//		User user = userRepository.findById(userId).orElse(null);
//		if (user != null) {
//			if (!user.isEmailVerified() && isTimePassedFromLastLinkSending(user.getVerifyingLinkSendingTime())) {
//				user.setVerifyingLinkSendingTime(new DateTime());
//				userRepository.save(user);
//				mailService.sendSignUpConfirmation(user);
//				return Helper.buildHttpResponse(HttpStatus.OK, FALSE, VERIFICATION_LINK_RESENT, null);
//			} else {
//				return Helper.buildHttpResponse(HttpStatus.BAD_REQUEST, TRUE, VERIFICATION_LINK_REQUEST_LIMIT_EXCEEDED , null);
//			}
//		} else {
//			return Helper.buildHttpResponse(HttpStatus.NOT_FOUND, TRUE, ACCOUNT_MISSING, null);
//		}
//	}

    @Override
    public ResponseEntity<Response> login(UserCredentials credentials) {
	    User user = userRepository.findByEmail(credentials.getEmail().toLowerCase());
        if (user == null) {
            return Helper.buildHttpResponse(HttpStatus.NOT_FOUND, TRUE, INVALID_CREDENTIALS, 1);
        } else {
			return Helper.passwordsMatch(credentials.getPassword(), user.getPassword())
					? proceedWithLogin(user)
					: Helper.buildHttpResponse(HttpStatus.NOT_FOUND, TRUE, INVALID_CREDENTIALS, null);
		}
    }

    @Override
    public ResponseEntity<Response> logout(String token) {
	    tokenService.deleteToken(token);
        return Helper.buildHttpResponse(HttpStatus.OK, FALSE, LOGOUT_SUCCESSFULLY, null);
    }

	@Override
	public ResponseEntity<Response> get(String userId) {
		if (ObjectId.isValid(userId)) {
			User user = userRepository.findById(new ObjectId(userId)).orElse(null);
			return user != null
					? Helper.buildHttpResponse(HttpStatus.OK, FALSE, SUCCESS, user)
					: Helper.buildHttpResponse(HttpStatus.NOT_FOUND, TRUE, USER_NOT_FOUND, null);
		} else {
			return Helper.buildHttpResponse(HttpStatus.BAD_REQUEST, TRUE, INVALID_USER_ID, null);
		}
	}

    @Override
    public ResponseEntity<Response> initialize() {
	    User user = User.builder()
                .role(Role.USER)
                .shelterId(null)
                .build();
	    return registerWithoutEmailVerification(user);
    }

    @Override
	public Boolean userExists(final String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User buildUser(User user) {
		DateTime registrationTime = new DateTime();
		return User
				.builder()
				.email(user.getEmail())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.password(Helper.hashPassword(user.getPassword()))
				.build();
	}


	private ResponseEntity<Response> proceedWithLogin(User dbUser) {
			return completeLogin(dbUser);

	}

	private ResponseEntity<Response> completeLogin(User dbUser) {
		String token = tokenService.createToken(dbUser);
		if (token != null) {
            tokenService.save(token, dbUser.getId());
            return Helper.buildHttpResponse(HttpStatus.OK, FALSE, LOGIN_SUCCESSFULLY, token);
        } else {
            return Helper.buildHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, TRUE, TRY_AGAIN_LATER, null);
        }
	}



	private String emailToLowerCase(User user) {
		return user.getEmail().toLowerCase();
	}

	private ResponseEntity<Response> proceedRegistration(GlobalAppSettings appSettings, User user, boolean likeAdmin) {
		user = buildUser(user);
		if(likeAdmin) {
            Shelter shelter = shelterService.save(shelterService.buildShelter());
            user.setShelterId(shelter.getId());
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
		return  registerWithoutEmailVerification(user);
	}

	private ResponseEntity<Response> registerWithoutEmailVerification(User user) {
		User createdUser = userRepository.save(user);
        System.out.println(createdUser);
		String token = tokenService.createToken(createdUser);
		TokenEntity tokenEntity = tokenService.save(token, createdUser.getId());
//		mailService.sendSignUpCompleted(createdUser);
		return tokenEntity != null
				? Helper.buildHttpResponse(HttpStatus.OK, FALSE, USER_REGISTERED, token)
				: Helper.buildHttpResponse(HttpStatus.SERVICE_UNAVAILABLE, TRUE, DATA_SAVING_FAILURE, null);
	}

	private ResponseEntity<Response> registerWithEmailVerification(User user) {
		userRepository.save(user);
		mailService.sendSignUpConfirmation(user);
		return Helper.buildHttpResponse(HttpStatus.OK, FALSE, VERIFICATION_LINK_SENT, null);
	}

	private boolean isVerificationLinkExpired(DateTime verificationLinkSentAt) {
		return new DateTime().minusHours(HOURS_FOR_EMAIL_VERIFICATION).isAfter(verificationLinkSentAt);
	}

	private boolean isTimePassedFromLastLinkSending(DateTime verificationLinkSentAt) {
		return new DateTime().minusHours(MINUTES_FOR_NEW_EMAIL_VERIFICATION_LINK).isAfter(verificationLinkSentAt);
	}

	private ResponseEntity<Response> completeEmailVerification(User user) {
		userRepository.save(user);
		String token = tokenService.createToken(user);
		TokenEntity tokenEntity = tokenService.save(token, user.getId());
		mailService.sendSignUpCompleted(user);
		return tokenEntity != null
				? Helper.buildHttpResponse(HttpStatus.OK, FALSE, ACCOUNT_VERIFIED, token)
				: Helper.buildHttpResponse(HttpStatus.SERVICE_UNAVAILABLE, TRUE, DATA_SAVING_FAILURE, null);
	}


}
