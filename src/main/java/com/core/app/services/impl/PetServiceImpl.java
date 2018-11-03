package com.core.app.services.impl;

import com.core.app.entities.database.pet.Pet;
import com.core.app.repositories.PetRepository;
import com.core.app.services.PetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public void delete(Pet pet) {
        petRepository.delete(pet);
    }

    public List<Pet> findByShelter(ObjectId objectId) {
        return petRepository.findByShelter(objectId);
    }

    public List<Pet> findByName(String name) {
        return petRepository.findByName(name);
    }

    public Page<Pet> findPets(int count, Pageable pageable) {
        return petRepository.findPets(count, pageable);
    }

}
