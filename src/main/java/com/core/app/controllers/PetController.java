package com.core.app.controllers;


import com.core.app.entities.database.pet.Pet;
import com.core.app.entities.dto.Response;
import com.core.app.services.PetService;
import com.core.app.services.TokenService;
import com.core.app.utils.Helper;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/v1/pets")
public class PetController {

    @Autowired
    private final PetService petService;
    private final TokenService tokenService;

    private final static int NOT_ADOPT = 0;

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

    @ApiOperation(value = "Get all pets with status")
    @GetMapping
    public ResponseEntity<Response> findAllPetsByStatus(@RequestParam int id) {
        List<Pet> pets = petService.findByStatus(id);
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

    @ApiOperation(value = "Save image of pet")
    @PostMapping("/pet/photo/save")
    public ResponseEntity<Response> save(@RequestParam String id, @RequestParam MultipartFile multipartFile) {

        File convFile = new File(multipartFile.getOriginalFilename());
        Path path = null;
        try {
            multipartFile.transferTo(convFile);
            byte[] bytes = multipartFile.getBytes();
            path = Paths.get("C://hackathon//" + multipartFile.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException ex) {
            throw new IllegalArgumentException("Something is wrong with your pictures.");
        }

        Pet pet = petService.findById(new ObjectId(id)).get();
        List<String> urls = pet.getPhotos();

        try {
            urls.add(convFile.toURI().toURL().toString());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Something is wrong with your pictures.");
        }

        petService.save(pet);

        return Helper.buildHttpResponse(HttpStatus.CREATED, false, "Image is saved of pet: " + pet.getName(), pet);
    }

    @ApiOperation(value = "Get page of pets")
    @GetMapping("/pet")
    public ResponseEntity<Response> getPet(@RequestParam ObjectId id, int size, int page) {
        Iterable<Pet> pets = petService.findById(id, size, page);
        return Helper.buildHttpResponse(HttpStatus.OK, false, "List of pets", pets);
    }

    @ApiOperation(value = "Get page of pets")
    @GetMapping("/pet/status")
    public ResponseEntity<Response> getPetByStatus(@RequestParam ObjectId id) {
        List<Pet> pets = petService.findByStatus(NOT_ADOPT);
        return Helper.buildHttpResponse(HttpStatus.OK, false, "List of not adopted pets", pets);

    }

}