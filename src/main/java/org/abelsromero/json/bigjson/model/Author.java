package org.abelsromero.json.bigjson.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Map;

@Builder
@Value
public class Author {

    String name;
    LocalDate birthday;
    Map<String, String> metadata;

}
