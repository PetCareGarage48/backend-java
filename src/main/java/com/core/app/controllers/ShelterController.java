package com.core.app.controllers;

import com.core.app.entities.database.shelter.Shelter;
import com.core.app.entities.dto.Response;
import com.core.app.services.ShelterService;
import com.core.app.utils.Helper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/v1/shelters")
public class ShelterController {

    private final ShelterService shelterService;

    @Autowired
    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping()
    public ResponseEntity<Response> getAllShelters() {
        Iterable<Shelter> shelters =  shelterService.findAll();
        return Helper.buildHttpResponse(HttpStatus.OK, false, "List of shelters", shelters);
    }

    @GetMapping("/shelter")
    public ResponseEntity<Response> getShelterById(@RequestParam String shelterId) {
        Optional<Shelter> shelter = shelterService.findById(new ObjectId(shelterId));
        if(shelter != null) {
            return Helper.buildHttpResponse(HttpStatus.OK, false, "Shelter", shelter);
        } else {
            return Helper.buildHttpResponse(HttpStatus.NOT_FOUND, true, "Shelter", null);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/shelter")
    public ResponseEntity<Response> updateShelter(@RequestBody Shelter shelter) {
        Shelter updatedShelter = shelterService.save(shelter);
        return Helper.buildHttpResponse(HttpStatus.OK, false, "Shelter updated", updatedShelter);
    }


}

