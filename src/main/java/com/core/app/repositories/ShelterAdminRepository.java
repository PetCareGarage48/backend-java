package com.core.app.repositories;

import com.core.app.entities.database.shelter.ShelterAdmin;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterAdminRepository extends PagingAndSortingRepository<ShelterAdmin, ObjectId> {
 ShelterAdmin findByEmail(String email);
}
