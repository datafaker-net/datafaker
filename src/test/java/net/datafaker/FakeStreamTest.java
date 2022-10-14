package net.datafaker;

import net.datafaker.providers.base.BaseFaker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Random;
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
    void generateNullCollection() {
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
            .suppliers(
                FakeCollectionTest.BloodPressure::new,
                FakeCollectionTest.Glucose::new,
                FakeCollectionTest.Temperature::new)
            .build()
            .singleton()
        ).isNotNull();
    }

    @Test
    void isInfiniteTest() {
        FakeStream<String> infiniteNames = faker.stream(() -> faker.name().firstName()).build();
        assertThat(infiniteNames.isInfinite()).isTrue();

        FakeStream<String> finiteNames = faker.stream(() -> faker.name().firstName()).len(5).build();
        assertThat(finiteNames.isInfinite()).isFalse();
    }
}
