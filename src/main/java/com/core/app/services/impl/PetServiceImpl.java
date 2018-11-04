package com.core.app.services.impl;

import com.core.app.entities.database.pet.Pet;
import com.core.app.repositories.PetRepository;
import com.core.app.services.PetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Pet> findById(ObjectId objectId) {
        return petRepository.findById(objectId);
    }

    @Override
    public Iterable<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public Page<Pet> findById(ObjectId id, int size, int page) {
        return petRepository.findById(id, new PageRequest(page, size));
    }

}
