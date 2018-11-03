package com.core.app.entities.database.user;

import com.core.app.constants.GeneralConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = GeneralConstants.TOKENS)
public class TokenEntity {

    private ObjectId id;
    private String token;
    private ObjectId userId;
    private DateTime createdAt;
    private DateTime updatedAt;
    private DateTime expiresAt;
}
