package com.core.app.services;

import com.core.app.entities.database.pet.Pet;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PetService {

    Pet save(Pet pet);

    void delete(Pet pet);

    List<Pet> findByShelter(ObjectId objectId);

    List<Pet> findByName(String name);

    Optional<Pet> findById(ObjectId objectId);

    Iterable<Pet> findAll();

    Page<Pet> findPets(int count, Pageable pageable);


}
