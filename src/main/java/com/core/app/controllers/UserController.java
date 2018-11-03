package com.core.app.controllers;

import com.core.app.entities.database.pet.Pet;
import com.core.app.entities.database.user.User;
import com.core.app.entities.dto.Response;
import com.core.app.entities.dto.UserCredentials;
import com.core.app.services.TokenService;
import com.core.app.services.UserService;
import com.core.app.utils.Helper;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.core.app.constants.GeneralConstants.AUTHORIZATION;
import static com.core.app.constants.GeneralConstants.USER_ID;

@Controller
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }


    @ApiOperation(value = "Register shelter admin")
    @PostMapping(value = "/register")
    public ResponseEntity<Response> register(@RequestBody User user, @RequestParam boolean isAdmin) {
        return userService.register(user, isAdmin);
    }

    @ApiOperation("Login shelter admin")
    @PostMapping(value = "/login")
    public ResponseEntity<Response> login(@RequestBody UserCredentials userCredentials) {
        return userService.login(userCredentials);
    }

    @ApiOperation("Logout shelter admin")
    @PostMapping(value = "/logout")
    public ResponseEntity<Response> logout(@RequestHeader(value = AUTHORIZATION) String token) {
        return userService.logout(token);
    }

    @ApiOperation("Get shelter admin by Id")
    @GetMapping
    public ResponseEntity<Response> getUser(@RequestParam(USER_ID) String userId, HttpServletRequest request) {
        ObjectId userIdFromToken = this.tokenService.getUserIdFromToken(Helper.getTokenFromHeader(request));
        if (userIdFromToken != null) {
            if(userIdFromToken.equals(new ObjectId(userId))) {
                return  userService.get(userId);
            }
        }
        return Helper.buildHttpResponse(HttpStatus.FORBIDDEN, true, "You don't have permission", null);
    }

    @ApiOperation("Initialize user")
    @GetMapping("/initialize")
    public ResponseEntity<Response> initialize() {
        return userService.initialize();
    }
}

