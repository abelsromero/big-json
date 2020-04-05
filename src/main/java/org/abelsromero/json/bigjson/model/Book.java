package org.abelsromero.json.bigjson.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Builder
@Value
public class Book {

    String title;
    Integer year;
    Genre genre;
    Optional<String> editor;
    Optional<String> summary;

    List<Author> authors;

    Map<String, String> metadata;
}
