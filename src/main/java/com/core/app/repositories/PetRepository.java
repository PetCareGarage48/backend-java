package com.core.app.repositories;

import com.core.app.entities.database.pet.Pet;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends PagingAndSortingRepository<Pet, ObjectId> {

    List<Pet> findByName(String name);

    List<Pet> findByShelterId(ObjectId shelterId);

    @Query(value="{'$sample': ?0}")
    Page<Pet> findPets(int count, Pageable pageable);

}
