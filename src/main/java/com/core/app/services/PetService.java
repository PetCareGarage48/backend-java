package com.core.app.services;

import com.core.app.entities.database.pet.Pet;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PetService {

    Pet save(Pet pet);

    void delete(Pet pet);

    List<Pet> findByShelter(ObjectId objectId);

    List<Pet> findByName(String name);

    Page<Pet> findPets(int count, Pageable pageable);


}
