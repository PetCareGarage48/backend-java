package com.core.app.repositories;

import com.core.app.entities.database.shelter.Shelter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterRepository extends PagingAndSortingRepository<Shelter, ObjectId> {

}
