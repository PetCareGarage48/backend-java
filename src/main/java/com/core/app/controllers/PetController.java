package com.core.app.controllers;


import com.core.app.entities.database.pet.Pet;
import com.core.app.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity findPets(int count, Pageable pageable) {
        return new ResponseEntity<>(petService.findPets(count, pageable), OK);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Pet pet) {
        // petService.update(pet);
        return new ResponseEntity<>(OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Pet pet) {
        petService.save(pet);
        return new ResponseEntity(OK);
    }

}
