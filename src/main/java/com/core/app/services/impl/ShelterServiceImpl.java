package com.core.app.services.impl;

import com.core.app.entities.database.shelter.Shelter;
import com.core.app.entities.database.shelter.WorkingHours;
import com.core.app.repositories.ShelterRepository;
import com.core.app.services.ShelterService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ShelterServiceImpl implements ShelterService {
    @Autowired
    private final ShelterRepository shelterRepository;

    public ShelterServiceImpl(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }


    @Override
    public Shelter save(Shelter shelter) {
        return shelterRepository.save(shelter);
    }

    @Override
    public void delete(Shelter shelter) {
        shelterRepository.delete(shelter);
    }

    @Override
    public Optional<Shelter> findById(ObjectId objectId) {
        return shelterRepository.findById(objectId);
    }

    @Override
    public Iterable<Shelter> findAll() {
        return shelterRepository.findAll();
    }

    @Override
    public Shelter buildShelter() {
        return Shelter.builder()
                .description("None")
                .paymentInfo("None")
                .photos(new ArrayList<>())
                .title("None")
                .requirements(new ArrayList<>())
                .workingHours(new ArrayList<>())
                .build();
    }
}
