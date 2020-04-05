package org.abelsromero.json.bigjson.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.abelsromero.json.bigjson.model.Author;
import org.abelsromero.json.bigjson.model.Book;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
public class BigJsonClient {

    public static final String SERVICE_ENDPOINT = "http://localhost:8080/books/";

    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    public static void main(String[] args) throws IOException, InterruptedException {

        final Integer requestedElements = 1;
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVICE_ENDPOINT + "?size=" + requestedElements))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        printJson(response.body());

        final ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());

        final BookCollection deserializedResponse = objectMapper.readValue(response.body(), BookCollection.class);

        System.out.println("[INFO] Response deserialized!!");

        final Book firstElement = deserializedResponse.getValues().iterator().next();
        System.out.println(firstElement.getTitle());
        System.out.println(firstElement.getGenre());
        System.out.println(firstElement.getYear());
        System.out.println("    Authors count: " + firstElement.getAuthors().size());
        final Author author = firstElement.getAuthors().get(0);
        System.out.println("    Name: " + author.getName());
        System.out.println("    Birthday: " + author.getBirthDay());
        System.out.println("    DeathDay: " + author.getDeathDate());
    }

    private static void printJson(String response) {
        System.out.println(response);
    }

}
