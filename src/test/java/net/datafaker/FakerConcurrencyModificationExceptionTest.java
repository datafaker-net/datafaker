package net.datafaker;

import net.datafaker.providers.base.BaseFaker;
import org.junit.jupiter.api.Test;

public class FakerConcurrencyModificationExceptionTest {

    private final BaseFaker faker = new BaseFaker();

    @Test
    void test1() {
        faker.random().nextLong();
    }

    @Test
    void test2() {
        faker.random().nextLong();
    }
}
