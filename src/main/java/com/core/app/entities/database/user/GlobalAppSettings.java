package com.core.app.entities.database.user;

import com.core.app.constants.GeneralConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = GeneralConstants.APP_SETTINGS)
public class GlobalAppSettings {

	private ObjectId id;
	private boolean emailVerificationEnabled;
}
