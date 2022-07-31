package net.datafaker;

import org.junit.jupiter.api.Test;

public class FakerConcurrencyModificationExceptionTest {

    private final Faker faker = new Faker();

    @Test
    public void test1() {
        faker.random().nextLong();
    }

    @Test
    public void test2() {
        faker.random().nextLong();
    }
}
