package com.core.app.controllers;


import com.core.app.entities.database.pet.Pet;
import com.core.app.entities.database.shelter.Shelter;
import com.core.app.entities.dto.Response;
import com.core.app.services.PetService;
import com.core.app.services.TokenService;
import com.core.app.utils.Helper;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/v1/pets")
public class PetController {

    @Autowired
    private final PetService petService;
    private final TokenService tokenService;

    public PetController(PetService petService, TokenService tokenService) {
        this.petService = petService;
        this.tokenService = tokenService;
    }

    @ApiOperation(value = "Get all pets")
    @GetMapping
    public ResponseEntity<Response> findAllPets() {
        Iterable<Pet> pets = petService.findAll();
        return Helper.buildHttpResponse(HttpStatus.OK, false, "List of pets", pets);
    }

    @ApiOperation(value = "Update pet")
    @PutMapping("/pet")
    public ResponseEntity<Response> update(@RequestBody Pet pet, HttpServletRequest request) {
        ObjectId shelterId = this.tokenService.getShelterIdFromToken(Helper.getTokenFromHeader(request));
        if (shelterId != null) {
            if(shelterId.equals(pet.getShelterId())) {
                Pet updatedPet = petService.save(pet);
                return Helper.buildHttpResponse(HttpStatus.OK, false, "Pet is updated", updatedPet);
            }
        }
        return Helper.buildHttpResponse(HttpStatus.FORBIDDEN, true, "You are not admin in this shelter", null);

    }

    @ApiOperation(value = "Create pet")
    @PostMapping("/pet")
    public ResponseEntity<Response> save(@RequestBody Pet pet, HttpServletRequest request) {
        ObjectId shelterId = this.tokenService.getShelterIdFromToken(Helper.getTokenFromHeader(request));
        if (shelterId != null) {
            if(shelterId.equals(pet.getShelterId())) {
                Pet createdPet = petService.save(pet);
                return Helper.buildHttpResponse(HttpStatus.OK, false, "Pet is created", createdPet);
            }
        }
        return Helper.buildHttpResponse(HttpStatus.FORBIDDEN, true, "You are not admin in this shelter", null);
    }

}
