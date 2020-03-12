package org.abelsromero.json.bigjson;

import org.abelsromero.json.bigjson.model.Book;
import org.abelsromero.json.bigjson.rest.InstanceGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


class InstanceGeneratorTests {

    private final InstanceGenerator generator = new InstanceGenerator();

    @Test
    void should_build_0_instances() {
        // when
        final Collection<Book> instances = generator.getInstances(0);
        // then
        assertThat(instances).hasSize(0);
    }

    @Test
    void should_build_10_instances() {
        // when
        final Collection<Book> instances = generator.getInstances(10);
        // then
        assertThat(instances).hasSize(10);
    }

}
