package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BrandTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testSport() {
        assertThat(faker.brand().sport()).isNotEmpty();

        System.out.println(faker.brand().sport());
    }

    @Test
    void testCar() {
        assertThat(faker.brand().car()).isNotEmpty();

        System.out.println(faker.brand().car());
    }

    @Test
    void testWatch() {
        assertThat(faker.brand().watch()).isNotEmpty();

        System.out.println(faker.brand().watch());
    }

}
