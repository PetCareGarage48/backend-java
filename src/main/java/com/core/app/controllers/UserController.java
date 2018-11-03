package com.core.app.controllers;

import com.core.app.entities.database.User;
import com.core.app.entities.dto.Response;
import com.core.app.entities.dto.UserCredentials;
import com.core.app.services.TokenService;
import com.core.app.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.core.app.constants.GeneralConstants.AUTHORIZATION;
import static com.core.app.constants.GeneralConstants.USER_ID;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@Autowired
	UserController(UserService userService, TokenService tokenService) {
		this.userService = userService;
	}

	@PostMapping(value = "/register")
	public ResponseEntity<Response> register(@RequestBody User user) {
		return userService.register(user);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<Response> login(@RequestBody UserCredentials userCredentials) {
		return userService.login(userCredentials);
	}

    @PostMapping(value = "/logout")
    public ResponseEntity<Response> logout(@RequestHeader(value = AUTHORIZATION) String token) {
	    return userService.logout(token);
    }

	@GetMapping
	public ResponseEntity<Response> getUser(@RequestParam(USER_ID) String userId) {
		return userService.get(userId);
	}

	@GetMapping(value = "/verify/account/{userId}")
	public String verifyAccount(@PathVariable ObjectId userId, Model model) {
		model.addAttribute("result", userService.verifyAccountViaEmail(userId).getBody());
		return "emailVerified";
	}

	@GetMapping(value = "/verify/account/resend/email/{userId}")
	public String resendEmailVerificationLink(@PathVariable ObjectId userId, Model model) {
		model.addAttribute("result", userService.resendEmailVerificationLink(userId).getBody());
		return "emailVerified";
	}




}
