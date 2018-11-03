package com.core.app.controllers;

import com.core.app.entities.database.shelter.Shelter;
import com.core.app.entities.dto.Response;
import com.core.app.services.ShelterService;
import com.core.app.services.TokenService;
import com.core.app.utils.Helper;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/v1/shelters")
public class ShelterController {

    private final ShelterService shelterService;
    private final TokenService tokenService;

    @Autowired
    public ShelterController(ShelterService shelterService, TokenService tokenService) {
        this.shelterService = shelterService;
        this.tokenService = tokenService;
    }

    @ApiOperation(value = "Get all shelters")
    @GetMapping()
    public ResponseEntity<Response> getAllShelters() {
        Iterable<Shelter> shelters =  shelterService.findAll();
        return Helper.buildHttpResponse(HttpStatus.OK, false, "List of shelters", shelters);
    }

    @ApiOperation(value = "Get shelter by Id")
    @GetMapping("/shelter")
    public ResponseEntity<Response> getShelterById(@RequestParam String shelterId) {
        Optional<Shelter> shelter = shelterService.findById(new ObjectId(shelterId));
        if(shelter != null) {
            return Helper.buildHttpResponse(HttpStatus.OK, false, "Shelter", shelter);
        } else {
            return Helper.buildHttpResponse(HttpStatus.NOT_FOUND, true, "Shelter", null);
        }
    }

    @ApiOperation(value = "Update shelter")
    @RequestMapping(method = RequestMethod.PUT, value = "/shelter")
    public ResponseEntity<Response> updateShelter(@RequestBody Shelter shelter, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectId shelterId = this.tokenService.getShelterIdFromToken(Helper.getTokenFromHeader(request));
        System.out.println(shelterId);
        if (shelterId != null) {
            if(shelterId.equals(shelter.getId())) {
                Shelter updatedShelter = shelterService.save(shelter);
                return Helper.buildHttpResponse(HttpStatus.OK, false, "Shelter updated", updatedShelter);
            }
        }
        return Helper.buildHttpResponse(HttpStatus.FORBIDDEN, true, "You are not admin in this shelter", null);
    }
}

