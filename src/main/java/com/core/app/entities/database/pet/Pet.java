package com.core.app.entities.database.pet;

import com.core.app.entities.database.user.Adoption;
import com.core.app.entities.enums.Gender;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.omg.CORBA.Object;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private ObjectId shelterId;
    private String name;
    private String description;
    private String type;
    private Gender gender;
    private int age;
    private ArrayList<String> photos;
    private ArrayList<Adoption> adoptions;
}
