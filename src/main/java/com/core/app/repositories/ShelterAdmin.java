package com.core.app.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterAdmin extends PagingAndSortingRepository<ShelterAdmin, ObjectId> {
}
