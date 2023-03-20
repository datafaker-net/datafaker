package net.datafaker.annotations;

import java.sql.Timestamp;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeRecordProviderTest {

    @Test
    void shouldGenerateRecordWithAnnotationFake() {
        var person = Faker.generate(RecordPerson.class);

        assertThat(person).isNotNull();
        assertThat(person.birthTimestamp).isNotNull();
        assertThat(person.firstName).isNotNull();
    }

    @Fake(languageTag = "en-de", seed = 1)
    record RecordPerson(
        @FieldFake(expression = "#{Name.first_name}")
        String firstName,
        @FieldFake(method = "net.datafaker.providers.base.DateAndTime#birthday")
        Timestamp birthTimestamp
    ) {
        private static String constantField;
    }
}
