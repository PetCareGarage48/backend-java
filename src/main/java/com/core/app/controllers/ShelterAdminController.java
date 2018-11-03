package com.core.app.controllers;

import com.core.app.entities.database.shelter.ShelterAdmin;
import com.core.app.entities.dto.Response;
import com.core.app.entities.dto.UserCredentials;
import com.core.app.services.ShelterAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.core.app.constants.GeneralConstants.AUTHORIZATION;
import static com.core.app.constants.GeneralConstants.SHELTER_ID;

@Controller
@RequestMapping("/v1/shelter/admins")
public class ShelterAdminController {

    private final ShelterAdminService shelterAdminService;

    @Autowired
    public ShelterAdminController(ShelterAdminService shelterAdminService) {
        this.shelterAdminService = shelterAdminService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Response> register(@RequestBody ShelterAdmin user) {
        return shelterAdminService.register(user);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Response> login(@RequestBody UserCredentials userCredentials) {
        return shelterAdminService.login(userCredentials);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Response> logout(@RequestHeader(value = AUTHORIZATION) String token) {
        return shelterAdminService.logout(token);
    }

    @GetMapping
    public ResponseEntity<Response> getUser(@RequestParam(SHELTER_ID) String userId) {
        return shelterAdminService.get(userId);
    }


}
