package com.core.app.entities.database.shelter;

import com.core.app.entities.database.user.Adoption;
import com.core.app.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {

    @Id
    private ObjectId id;
    private String name;
    private String description;
    private String type;
    private Gender gender;
    private String breed;
    private int age;
    private ArrayList<String> skills;
    private ArrayList<String> photos;
    private ArrayList<Adoption> adoptions;
}
