package com.core.app.entities.database.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Donation {

    private ObjectId id;
    private int sum;
    private ObjectId shelterId;
    private ObjectId userId;
}
