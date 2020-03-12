package org.abelsromero.json.bigjson.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Builder
@Value
public class Book {

    String title;
    List<Author> authors;

    Map<String, String> metadata;
}
