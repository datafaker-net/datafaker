package net.datafaker;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class Issue1178Test {

    @Test
    void testExpressionEnglishFails() {
        Faker faker = new Faker(Locale.ENGLISH);
        assertThat(faker.expression("#{name.first_name}")).isNotBlank();
    }

    @Test
    void testExpressionFails() {
        Faker faker = new Faker();
        assertThat(faker.expression("#{name.first_name}")).isNotBlank();
    }

    @Test
    void testExpressionUsFails() {
        Faker faker = new Faker(new Locale("en", "US"));
        assertThat(faker.expression("#{name.first_name}")).isNotBlank();
    }

    @Test
    void testExpressionAUWorks() {
        Faker faker = new Faker(new Locale("en", "AU"));
        assertThat(faker.expression("#{name.first_name}")).isNotBlank();
    }

    @Test
    void testExpressionNLWorks() {
        Faker faker = new Faker(new Locale("nl", "nl"));
        assertThat(faker.expression("#{name.first_name}")).isNotBlank();
    }


    @Test
    void testExpressionNLWithMiddleWorks() {
        Faker faker = new Faker(new Locale("nl", "nl"));
        assertThat(faker.expression("#{name.name_with_middle}")).isNotBlank();
    }

    @Test
    void testExpressionNLWithCity() {
        Faker faker = new Faker();
        assertThat(faker.expression("#{address.city}")).isNotBlank();
    }

    @Test
    void testExpressionNLWithFullAddress() {
        Faker faker = new Faker(new Locale("nl", "nl"));
        assertThat(faker.expression("#{address.full_address}")).isNotBlank();
    }
}
