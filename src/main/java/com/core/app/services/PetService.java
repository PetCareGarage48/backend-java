package com.core.app.services;

import com.core.app.entities.database.shelter.Pet;
import org.bson.types.ObjectId;

import java.util.List;

public interface PetService {

    Pet save(Pet pet);
    void delete(Pet pet);
    Pet findById(ObjectId objectId);
    List<Pet> findAll();
    List<Pet> findByShelter(ObjectId objectId);
}
