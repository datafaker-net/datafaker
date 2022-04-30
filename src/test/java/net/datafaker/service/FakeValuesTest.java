package net.datafaker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;


class FakeValuesTest {

    private static final String PATH = "address";
    private FakeValues fakeValues;

    @BeforeEach
    void before() {
        fakeValues = new FakeValues(Locale.ENGLISH, "address.yml", PATH);
    }

    @Test
    void supportsPathIsTrueWithTheSameValueAsThePath() {
        assertThat(fakeValues.supportsPath(PATH)).isTrue();
    }

    @Test
    void supportsPathIsFalseWhenValueIsNotTheSame() {
        assertThat(fakeValues.supportsPath("dog")).isFalse();
    }

    @Test
    void getAValueReturnsAValue() {
        assertThat(fakeValues.get(PATH)).isNotNull();
    }

    @Test
    void getAValueDoesNotReturnAValue() {
        assertThat(fakeValues.get("dog")).isNull();
    }

    @Test
    void getAValueWithANonEnglishFile() {
        FakeValues frenchFakeValues = new FakeValues(Locale.FRENCH);
        assertThat(frenchFakeValues.get(PATH)).isNotNull();
    }

    @Test
    void getAValueForHebrewLocale() {
        FakeValues hebrew = new FakeValues(new Locale("iw"));
        assertThat(hebrew.get(PATH)).isNotNull();
    }

    @Test
    void getAValueFromALocaleThatCantBeLoaded() {
        FakeValues fakeValues = new FakeValues(new Locale("nothing"));
        assertThat(fakeValues.get(PATH)).isNull();
    }

    @ParameterizedTest
    @MethodSource("fakeValuesProvider")
    void checkEquals(FakeValues fv1, FakeValues fv2, boolean equals) {
        if (equals) {
            assertThat(fv1).isEqualTo(fv2);
        } else {
            assertThat(fv1).isNotEqualTo(fv2);
        }
    }

    static Stream<Arguments> fakeValuesProvider() {
        Path tmp = Paths.get("tmp");
        return Stream.of(
            of(new FakeValues(Locale.CANADA), new FakeValues(Locale.CANADA), true),
            of(null, new FakeValues(Locale.CANADA), false),
            of(new FakeValues(Locale.CANADA), null, false),
            of(new FakeValues(Locale.CANADA), null, false),
            of(new FakeValues(Locale.ENGLISH), new FakeValues(Locale.ENGLISH, "filepath", "path"), false),
            of(new FakeValues(Locale.ENGLISH, "filepath", null), new FakeValues(Locale.ENGLISH, "filepath", "path"), false),
            of(new FakeValues(Locale.ENGLISH, "filepath", "path"), new FakeValues(Locale.ENGLISH, "filepath", "path"), true),
            of(new FakeValues(Locale.ENGLISH, "filepath", "path", tmp), new FakeValues(Locale.ENGLISH, "filepath", "path", tmp), true),
            of(new FakeValues(Locale.ENGLISH, "filepath", "path", Paths.get("tmp2")), new FakeValues(Locale.ENGLISH, "filepath", "path", tmp), false)
        );
    }
}
