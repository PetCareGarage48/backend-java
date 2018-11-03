package com.core.app.entities.database.shelter;

import com.mongodb.client.model.geojson.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shelter {
    @Id
    private ObjectId id;
    private String title;
    private Point location;
    private String description;
    private String paymentInfo;
    private ArrayList<String> photos;
    private ArrayList<Requirement> requirements;
    private WorkingHours workingHours;
    private String email;
    private ArrayList<ObjectId> pets;
}
