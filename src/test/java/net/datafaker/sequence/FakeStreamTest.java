package net.datafaker.sequence;

import net.datafaker.AbstractFakerTest;
import net.datafaker.formats.Format;
import net.datafaker.providers.base.Address;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FakeStreamTest extends AbstractFakerTest {

    @Test
    void generateFiniteStream() {
        Stream<String> finiteNames = faker.<String>stream()
            .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
            .minLen(3)
            .maxLen(5)
            .generate();
        assertThat(finiteNames).hasSizeBetween(3, 5);
    }

    @Test
    void generateInfiniteStream() {
        Stream<String> infiniteNames = faker.stream(() -> faker.name().firstName())
            .generate();
        assertThat(infiniteNames.spliterator().estimateSize()).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    void generateStreamOfNames() {
        Stream<String> names = faker.stream(() -> faker.name().firstName())
            .len(5)
            .generate();
        names.forEach(name -> assertThat(name).matches("[a-zA-Z']+"));
    }

    @Test
    void generateStreamOfDigits() {
        Stream<String> digitsInfinite = faker.stream(() -> faker.number().digit())
            .generate();
        digitsInfinite.limit(1_000).forEach(name -> assertThat(name).matches("\\d"));
    }

    @Test
    void generateNullStream() {
        Stream<String> names = faker.<String>stream()
            .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
            .nullRate(1d)
            .len(10)
            .generate();
        names.forEach(name -> assertThat(name).isNull());
    }

    @ParameterizedTest
    @ValueSource(doubles = {Long.MIN_VALUE, Integer.MIN_VALUE, -1, -0.3, 2, 3, Integer.MAX_VALUE, Double.MAX_VALUE})
    void illegalNullRate(double nullRate) {
        assertThatThrownBy(
            () -> faker.stream()
                .suppliers(() -> faker.name().firstName(), () -> faker.name().lastName())
                .nullRate(nullRate)
                .minLen(3)
                .maxLen(5)
                .generate())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Null rate should be between 0 and 1");
    }

    @Test
    void generateStreamPassingSuppliersAsList() {
        BaseFaker faker = new BaseFaker();
        List<Supplier<String>> suppliers = Arrays.asList(() -> faker.name().firstName(), () -> faker.name().lastName());
        Stream<String> stream = faker.stream(suppliers).len(3).generate();

        assertThat(stream.collect(Collectors.toList())).hasSize(3);
    }

    @Test
    void generateStreamWithRepeatableFaker() {
        BaseFaker seededFaker = new BaseFaker(new Random(10L));

        Stream<String> names = faker.<String>stream()
            .faker(seededFaker)
            .suppliers(() -> seededFaker.name().firstName(), () -> seededFaker.name().lastName())
            .minLen(1)
            .maxLen(20)
            .generate();

        List<String> namesList = names.collect(Collectors.toList());
        assertThat(namesList).hasSize(14);
        assertThat(namesList.get(0)).isEqualTo("Flor");
        assertThat(namesList.get(1)).isEqualTo("Brian");
    }

    @Test
    void generateStreamWithDifferentObjects() {
        Stream<Object> objects = faker.stream()
            .suppliers(() -> faker.name().firstName(), () -> faker.random().nextInt(100))
            .maxLen(5)
            .generate();

        objects.forEach(object -> assertThat(object).isInstanceOfAny(Integer.class, String.class));
    }

    @Test
    void checkWrongArguments() {
        assertThatThrownBy(() ->
            faker.stream()
                .suppliers(() -> faker.name().firstName())
                .minLen(10)
                .maxLen(5)
                .generate())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Max length must be not less than min length and not negative");
    }

    @RepeatedTest(10)
    void singletonTest() {
        int limit = 10;
        assertThat(faker.stream()
            .minLen(limit)
            .maxLen(limit)
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build()
            .singleton()
        ).isNotNull();
    }

    @Test
    void isInfiniteTest() {
        FakeSequence<String> infiniteNames = faker.stream(() -> faker.name().firstName())
            .build();
        assertThat(infiniteNames.isInfinite()).isTrue();

        FakeSequence<String> finiteNames = faker.stream(() -> faker.name().firstName())
            .len(5)
            .build();
        assertThat(finiteNames.isInfinite()).isFalse();
    }

    @Test
    void differentNumberOfHeadersAndColumns() {
        assertThatThrownBy(() -> Format.toCsv(
                faker.<Name>stream()
                    .suppliers(faker::name)
                    .minLen(3)
                    .maxLen(5)
                    .build()
            )
            .headers(() -> "firstName", () -> "lastname")
            .columns(Name::firstName, Name::lastName, Name::fullName)
            .build()
            .get())
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void toCsv() {
        String separator = "$$$";
        int limit = 5;
        FakeSequence<Data> stream = faker.<Data>stream()
            .minLen(limit)
            .maxLen(limit)
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build();

        String csv = Format.toCsv(stream)
            .headers(() -> "name", () -> "value", () -> "range", () -> "unit")
            .columns(Data::name, Data::value, Data::range, Data::unit)
            .separator(separator)
            .build()
            .get();

        int numberOfLines = 0;
        int numberOfSeparator = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            } else if (csv.regionMatches(i, separator, 0, separator.length())) {
                numberOfSeparator++;
            }
        }

        assertThat(numberOfLines).isEqualTo(6);
        assertThat(numberOfSeparator).isEqualTo(18); // number of lines * (number of columns - 1)
    }

    @Test
    void toCsvFromInfiniteSequence() {
        String separator = "$$$";
        FakeSequence<Data> infiniteStream = faker.<Data>stream()
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build();

        assertThatThrownBy(() ->
            Format.toCsv(infiniteStream)
                .headers(() -> "name", () -> "value", () -> "range", () -> "unit")
                .columns(Data::name, Data::value, Data::range, Data::unit)
                .separator(separator)
                .build()
                .get()
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The sequence should be finite of size");
    }

    @Test
    void toCsvFromInfiniteSequenceWithLimit() {
        String separator = "$$$";
        int limit = 5;
        FakeSequence<Data> infiniteStream = faker.<Data>stream()
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build();
        String csv = Format.toCsv(infiniteStream)
            .headers(() -> "name", () -> "value", () -> "range", () -> "unit")
            .columns(Data::name, Data::value, Data::range, Data::unit)
            .separator(separator)
            .limit(limit) // <- adding a limit for CSV
            .build()
            .get();

        int numberOfLines = 0;
        int numberOfSeparator = 0;
        for (int i = 0; i < csv.length(); i++) {
            if (csv.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLines++;
            } else if (csv.regionMatches(i, separator, 0, separator.length())) {
                numberOfSeparator++;
            }
        }

        assertThat(numberOfLines).isEqualTo(6);
        assertThat(numberOfSeparator).isEqualTo(18); // number of lines * (number of columns - 1)
    }

    @Test
    void toJson() {
        int limit = 10;
        FakeSequence<Data> stream = faker.<Data>stream()
            .minLen(limit)
            .maxLen(limit)
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build();
        String json = Format.toJson(stream)
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
    void toJsonFromInfiniteSequence() {
        FakeSequence<Data> infiniteStream = faker.<Data>stream()
            .suppliers(BloodPressure::new, Glucose::new, Temperature::new)
            .build();

        assertThatThrownBy(() ->
            Format.toJson(infiniteStream)
                .set("name", Data::name)
                .set("value", Data::value)
                .set("range", Data::range)
                .set("unit", Data::unit)
                .build()
                .generate()
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The sequence should be finite of size");
    }

    @Test
    void toNestedJson() {
        final int limit = 2;
        FakeSequence<Object> stream = faker.stream()
            .suppliers(faker::name)
            .maxLen(limit)
            .minLen(limit)
            .build();

        final String json =
            Format.toJson(stream)
                .set("primaryAddress",
                    Format.toJson()
                        .set("country", () -> faker.address().country())
                        .set("city", () -> faker.address().city())
                        .set("zipcode", () -> faker.address().zipCode())
                        .set("streetAddress", () -> faker.address().streetAddress())
                        .build())
                .set("secondaryAddresses", Format.toJson(faker.<Address>stream()
                        .suppliers(faker::address)
                        .maxLen(1)
                        .minLen(1)
                        .build())
                    .set("country", Address::country)
                    .set("city", Address::city)
                    .set("zipcode", Address::zipCode)
                    .set("streetAddress", Address::streetAddress)
                    .build())
                .set("phones", name -> faker.stream().suppliers(() -> faker.phoneNumber().phoneNumber()).maxLen(3).build().get())
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

    @Test
    void testIterator() {
        int fakeSequenceSize = 100;
        FakeSequence<String> digits = faker
            .stream(() -> faker.number().digit())
            .len(fakeSequenceSize)
            .build();

        int count = 0;
        for (String digit : digits) {
            assertThat(digit).matches("\\d");
            count++;
        }

        assertThat(count).isEqualTo(fakeSequenceSize);
    }

    @Test
    void testIteratorInfinite() {
        FakeSequence<String> digits = faker
            .stream(() -> faker.number().digit())
            .build();

        assertThat(digits.isInfinite()).isTrue();

        int count = 0;
        int amountOfElementsToTake = 1_000;
        for (String digit : digits) {
            assertThat(digit).matches("\\d");
            count++;
            if (count == amountOfElementsToTake) {
                break;
            }
        }

        assertThat(count).isEqualTo(amountOfElementsToTake);
    }
}
