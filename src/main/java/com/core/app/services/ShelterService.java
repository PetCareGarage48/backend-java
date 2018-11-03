package com.core.app.services;

import com.core.app.entities.database.shelter.Shelter;
import org.bson.types.ObjectId;
import java.util.List;

public interface ShelterService {

    Shelter save(Shelter shelter);
    Shelter delete(Shelter shelter);
    Shelter findById(ObjectId objectId);
    List<Shelter> findAll();
}
