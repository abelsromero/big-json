package org.abelsromero.json.bigjson.client;

import lombok.Getter;
import org.abelsromero.json.bigjson.model.Book;

import java.beans.ConstructorProperties;
import java.util.Collection;

@Getter
public class BookCollection {

    private final Collection<Book> values;
    private final long size;

    // For automatic jackson deserialization
    @ConstructorProperties({"values", "size"})
    public BookCollection(Collection<Book> values, Integer size) {
        this.values = values;
        this.size = values == null ? 0 : values.size();
    }
}
