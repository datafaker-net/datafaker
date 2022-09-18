package net.datafaker;

import net.datafaker.base.BaseFaker;
import org.junit.jupiter.api.Test;

public class FakerConcurrencyModificationExceptionTest {

    private final BaseFaker faker = new BaseFaker();

    @Test
    public void test1() {
        faker.random().nextLong();
    }

    @Test
    public void test2() {
        faker.random().nextLong();
    }
}
