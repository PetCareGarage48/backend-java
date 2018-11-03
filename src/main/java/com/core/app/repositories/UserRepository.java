package com.core.app.repositories;

import com.core.app.entities.database.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, ObjectId> {

    boolean existsByEmail(String email);

    User findByEmail(String email);

    Page<User> findByRole(String role, Pageable pageable);
}
