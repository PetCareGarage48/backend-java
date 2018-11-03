package com.core.app.entities.database.shelter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkingHours {

    private String from;
    private String to;
    private ArrayList<Integer> days;
}
