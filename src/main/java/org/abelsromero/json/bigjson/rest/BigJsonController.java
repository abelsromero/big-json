package org.abelsromero.json.bigjson.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.abelsromero.json.bigjson.model.Book;
import org.abelsromero.json.bigjson.model.GenericCollection;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@RestController
@RequestMapping("/books")
public class BigJsonController {

    private final ObjectMapper objectMapper;
    private final InstanceGenerator generator;

    private final Gson gson = new GsonBuilder().create();

    private Collection<Book> cache;

    private static final int DEFAULT_GENERATED_INSTANCES = 1_000;

    public BigJsonController(ObjectMapper objectMapper, InstanceGenerator generator) {
        this.objectMapper = objectMapper;
        this.generator = generator;
    }

    @GetMapping
    GenericCollection<Collection<Book>> getBooksAsPojos(@RequestParam(required = false) Integer size) {
        final Collection<Book> instances = generator.getInstances(calculateSize(size));
        return new GenericCollection(instances);
    }

    @GetMapping("/writer")
    void getBooksAsStream(@RequestParam(required = false) Integer size,
                          @RequestParam(required = false) String serializer,
                          @RequestParam(required = false) boolean use_cache,
                          HttpServletResponse response) throws IOException {

        response.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        final Collection<Book> instances = use_cache ? getFromCache(calculateSize(size)) : generator.getInstances(calculateSize(size));

        final PrintWriter writer = response.getWriter();
        if (serializer.equals("gson"))
            gson.toJson("hello", writer);
        else
            objectMapper.writeValue(writer, new GenericCollection(instances));
    }

    private Collection<Book> getFromCache(int size) {
        if (cache == null)
            cache = generator.getInstances(size);

        if (size > cache.size())
            cache.addAll(generator.getInstances(size - cache.size()));

        return cache;
    }

    private int calculateSize(@RequestParam(required = false) Integer limit) {
        return limit != null || isInvalid(limit) ? limit : DEFAULT_GENERATED_INSTANCES;
    }

    private boolean isInvalid(Integer limit) {
        return limit.intValue() < 1;
    }

}
