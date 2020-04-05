package org.abelsromero.json.bigjson.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Builder
@Value
public class Author {

    String name;
    LocalDate birthDay;
    Optional<LocalDate> deathDate;
    Map<String, String> metadata;

}
