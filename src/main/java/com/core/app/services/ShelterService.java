package com.core.app.services;

import com.core.app.entities.database.shelter.Shelter;
import org.bson.types.ObjectId;
import java.util.Optional;

public interface ShelterService {

    Shelter save(Shelter shelter);
    void delete(Shelter shelter);
    Optional<Shelter> findById(ObjectId id);
    Iterable<Shelter> findAll();
    Shelter buildShelter();
}
