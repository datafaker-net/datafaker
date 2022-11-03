package net.datafaker.sequence;

import net.datafaker.AbstractFakerTest;
import net.datafaker.formats.Format;
import net.datafaker.providers.base.Address;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import net.datafaker.providers.base.Number;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FakeCollectionTest extends AbstractFakerTest {
    @Test
    void generateCollection() {
        List<String> names = faker.<String>collection()
            .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
            .minLen(3)
            .maxLen(5).build().get();
        assertThat(names).hasSizeBetween(3, 5);
        for (String name : names) {
            assertThat(name).matches("[a-zA-Z']+");
        }
    }

    @Test
    void generateSequence() {
        List<String> digits = faker
            .collection(() -> faker.number().digit())
            .len(3, 10).generate();
        assertThat(digits).hasSizeBetween(3, 10);
        for (String digit : digits) {
            assertThat(digit).matches("\\d");
        }
    }

    @Test
    void generateSequenceOfDefaultSize() {
        List<String> digits = faker
            .collection(() -> faker.number().digit())
            .generate();
        assertThat(digits).hasSize(10);

        digits = faker
            .collection(() -> faker.number().digit())
            .maxLen(-175)
            .generate();
        assertThat(digits).hasSize(10);
    }

    @Test
    void generateEmptySequence() {
        List<Number> digits = faker
            .collection(() -> faker.number().digit())
            .maxLen(0)
            .generate();
        assertThat(digits).isEmpty();
    }

    @Test
    void isInfiniteTest() {
        FakeSequence<String> digits = faker
            .collection(() -> faker.number().digit())
            .build();
        assertThat(digits.isInfinite()).isFalse();

        digits = faker
            .collection(() -> faker.number().digit())
            .minLen(3)
            .maxLen(5)
            .build();
        assertThat(digits.isInfinite()).isFalse();
    }

    @Test
    void generateSequence5() {
        List<String> digits = faker
            .collection(() -> faker.number().digit())
            .len(5).generate();
        assertThat(digits).hasSize(5);
        for (String digit : digits) {
            assertThat(digit).matches("\\d");
        }
    }

    @Test
    void generateNullCollection() {
        List<String> names = faker.<String>collection()
            .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
            .nullRate(1d)
            .minLen(3)
            .maxLen(5).build().get();
        assertThat(names).hasSizeBetween(3, 5);
        for (String name : names) {
            assertThat(name).isNull();
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {Long.MIN_VALUE, Integer.MIN_VALUE, -1, -0.3, 2, 3, Integer.MAX_VALUE, Double.MAX_VALUE})
    void illegalNullRate(double nullRate) {
        assertThatThrownBy(
            () -> faker.collection()
                .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
                .nullRate(nullRate)
                .minLen(3)
                .maxLen(5).build().get())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Null rate should be between 0 and 1");
    }

    @Test
    void generateCollectionPassingSuppliersAsList() {
        BaseFaker seededFaker = new BaseFaker(new Random(10L));
        List<Supplier<String>> suppliers = Arrays.asList(() -> faker.name().firstName(), () -> faker.name().lastName());

        List<String> names = faker.collection(suppliers).faker(seededFaker).len(3).generate();
        assertThat(names).hasSize(3);
    }

    @Test
    void generateCollectionWithRepeatableFaker() {
        BaseFaker seededFaker = new BaseFaker(new Random(10L));

        List<String> names = faker.<String>collection()
            .faker(seededFaker)
            .suppliers(() -> seededFaker.name().firstName(), () -> seededFaker.name().lastName())
            .minLen(1)
            .maxLen(20).build().get();

        assertThat(names).hasSize(14);
        assertThat(names.get(0)).isEqualTo("Flor");
        assertThat(names.get(1)).isEqualTo("Brian");
    }

    @Test
    void generateCollectionWithDifferentObjects() {
        List<Object> objects = faker.collection()
            .suppliers(() -> faker.name().firstName(), () -> faker.random().nextInt(100))
            .maxLen(5).build().get();
        assertThat(objects).hasSize(5);
        for (Object object : objects) {
            assertThat(object).isInstanceOfAny(Integer.class, String.class);
        }
    }

    @Test
    void checkWrongArguments() {
        assertThatThrownBy(() ->
            faker.collection()
                .suppliers(() -> faker.name().firstName())
                .minLen(10)
                .maxLen(5).build().get())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Max length must be not less than min length and not negative");
    }

    @Test
    void differentNumberOfHeadersAndColumns() {
        assertThatThrownBy(() -> Format.toCsv(
                faker.<Name>collection()
                    .suppliers(faker::name)
                    .minLen(3)
                    .maxLen(5)
                    .build())
            .headers(() -> "firstName", () -> "lastname")
            .columns(Name::firstName, Name::lastName, Name::fullName).build().get())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void toCsv() {
        String separator = "$$$";
        int limit = 5;
        String csv = Format.toCsv(
                faker.<Data>collection().minLen(limit).maxLen(limit)
                    .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
                    .build())
            .headers(() -> "name", () -> "value", () -> "range", () -> "unit")
            .columns(Data::name, Data::value, Data::range, Data::unit)
            .separator(separator)
            .build().get();

        int numberOfLines = 0;
        int numberOfSeparator = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            } else if (csv.regionMatches(i, separator, 0, separator.length())) {
                numberOfSeparator++;
            }
        }

        assertThat(limit + 1).isEqualTo(numberOfLines); // limit + 1 line for header
        assertThat((limit + 1) * (4 - 1)).isEqualTo(numberOfSeparator); // number of lines * (number of columns - 1)
    }

    @Test
    void toJson() {
        int limit = 10;
        String json = Format.toJson(
                faker.<Data>collection().minLen(limit).maxLen(limit)
                    .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
                    .build())
            .set("name", Data::name)
            .set("value", Data::value)
            .set("range", Data::range)
            .set("unit", Data::unit)
            .build()
            .generate();

        int numberOfLines = 0;
        for (int i = 0; i < json.length(); i++) {
            if (json.regionMatches(i, "},", 0, "},".length())) {
                numberOfLines++;
            }
        }

        assertThat(limit - 1).isEqualTo(numberOfLines); // limit - 1 since for the last line there is no comma
    }

    @Test
    void toNestedJson() {
        final int limit = 2;
        final String json =
            Format.toJson(faker.collection()
                    .suppliers(faker::name)
                    .maxLen(limit)
                    .minLen(limit)
                    .build())
                .set("primaryAddress", Format.toJson()
                    .set("country", () -> faker.address().country())
                    .set("city", () -> faker.address().city())
                    .set("zipcode", () -> faker.address().zipCode())
                    .set("streetAddress", () -> faker.address().streetAddress())
                    .build())
                .set("secondaryAddresses", Format.toJson(faker.<Address>collection()
                        .suppliers(faker::address)
                        .maxLen(1)
                        .minLen(1)
                        .build())
                    .set("country", Address::country)
                    .set("city", Address::city)
                    .set("zipcode", Address::zipCode)
                    .set("streetAddress", Address::streetAddress)
                    .build())
                .set("phones", name -> faker.collection().suppliers(() -> faker.phoneNumber().phoneNumber()).maxLen(3).build().get())
                .build()
                .generate();

        int numberOfLines = 0;
        for (int i = 0; i < json.length(); i++) {
            if (json.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            }
        }
        assertThat(numberOfLines).isEqualTo(limit - 1); // limit - 1 since for the last line there is no comma
    }

    @RepeatedTest(10)
    void singletonTest() {
        int limit = 10;
        assertThat(faker.<Data>collection().minLen(limit).maxLen(limit)
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build().singleton()).isNotNull();
    }

    @Test
    void testIterator() {
        int fakeSequenceSize = 10;
        FakeSequence<String> digits = faker
            .collection(() -> faker.number().digit())
            .len(fakeSequenceSize)
            .build();

        int count = 0;
        for (String digit : digits) {
            assertThat(digit).matches("\\d");
            count++;
        }

        assertThat(count).isEqualTo(fakeSequenceSize);
    }
}
