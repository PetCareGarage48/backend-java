package com.core.app.controllers;


import com.core.app.entities.database.pet.Pet;
import com.core.app.entities.dto.Response;
import com.core.app.services.PetService;
import com.core.app.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/v1/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<Response> findAllPets() {
        Iterable<Pet> pets = petService.findAll();
        return Helper.buildHttpResponse(HttpStatus.OK, false, "List of pets", pets);
    }

    @PutMapping("/pet")
    public ResponseEntity<Response> update(@RequestBody Pet pet) {
        Pet updatedPet = petService.save(pet);
        return Helper.buildHttpResponse(HttpStatus.OK, false, "Pet is updated", updatedPet);
    }

    @PostMapping("/pet")
    public ResponseEntity<Response> save(@RequestBody Pet pet) {
        Pet savedPet = petService.save(pet);
        return Helper.buildHttpResponse(HttpStatus.CREATED, false, "Pet is created", savedPet);
    }

}
