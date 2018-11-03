package com.core.app.services.impl;

import com.core.app.entities.database.pet.Pet;
import com.core.app.repositories.PetRepository;
import com.core.app.services.PetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public void delete(Pet pet) {
        petRepository.delete(pet);
    }

    public List<Pet> findByShelter(ObjectId objectId) {
        return petRepository.findByShelterId(objectId);
    }

    public List<Pet> findByName(String name) {
        return petRepository.findByName(name);
    }

    public Page<Pet> findPets(int count, Pageable pageable) {
        return petRepository.findPets(count, pageable);
    }

}
