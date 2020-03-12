package org.abelsromero.json.bigjson.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.abelsromero.json.bigjson.model.Book;
import org.abelsromero.json.bigjson.model.GenericCollection;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@RestController()
public class BigJsonController {

    private final ObjectMapper objectMapper;
    private final InstanceGenerator generator;

    private final Gson gson = new GsonBuilder().create();

    private final Collection<Book> instances;

    private static final int GENERATED_INSTANCES = 10_000;

    public BigJsonController(ObjectMapper objectMapper, InstanceGenerator generator) {
        this.objectMapper = objectMapper;
        this.generator = generator;
        instances = generator.getInstances(GENERATED_INSTANCES * 3);
    }


    @GetMapping("/hello")
    public String hello() {
        Collection<Book> instances = generator.getInstances(1);
        return "Hello";
    }

    @GetMapping("/books/singleton")
    public GenericCollection<Collection<Book>> getSingletonBooks() {
        return new GenericCollection(instances);
    }

    @GetMapping("/books/jackson")
    public GenericCollection<Collection<Book>> getBooks() {
        Collection<Book> instances = generator.getInstances(GENERATED_INSTANCES);
        return new GenericCollection(instances);
    }

    @GetMapping("/books/jackson-stream")
    public void getBooksAsStream(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Collection<Book> instances = generator.getInstances(GENERATED_INSTANCES);

        response.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        PrintWriter writer = response.getWriter();
        objectMapper.writeValue(writer, new GenericCollection(instances));
    }

    @GetMapping("/books/gson-stream")
    public void getBooksAsGsonStream(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        PrintWriter writer = response.getWriter();
        gson.toJson("hello", writer);
    }

}
