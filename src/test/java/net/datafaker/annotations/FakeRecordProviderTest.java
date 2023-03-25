package net.datafaker.annotations;

import java.sql.Timestamp;
import java.util.Locale;
import java.util.Random;

import net.datafaker.Faker;
import net.datafaker.providers.base.BaseFaker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeRecordProviderTest {

    @Test
    void shouldGenerateRecordWithAnnotationFake() {
        var person = Faker.generate(RecordPerson.class);

        Timestamp expectedBirthday = new BaseFaker(Locale.forLanguageTag("en-de"), new Random(1)).date().birthday();
        assertThat(person).isNotNull();
        assertThat(person.birthTimestamp).isEqualTo(expectedBirthday);
        assertThat(person.firstName).isEqualTo("Darrel");
    }

    @Fake(languageTag = "en-de", seed = 1)
    record RecordPerson(
        @FieldFake(expression = "#{Name.first_name}")
        String firstName,
        @FieldFake(method = "net.datafaker.providers.base.DateAndTime#birthday", seed = 1)
        Timestamp birthTimestamp
    ) {
        private static String constantField;
    }
}
