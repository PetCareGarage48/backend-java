package com.core.app.repositories;

import com.core.app.entities.database.user.GlobalAppSettings;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface GlobalAppSettingsRepository extends MongoRepository<GlobalAppSettings, ObjectId> {

	GlobalAppSettings findTopByOrderById();

}
