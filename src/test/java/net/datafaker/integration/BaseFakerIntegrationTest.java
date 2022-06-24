package net.datafaker.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import net.datafaker.core.BaseFaker;
import net.datafaker.core.Faker;

/**
 * The purpose of these tests is to ensure that the Locales have been properly configured
 * and that methods return values. The unit tests should ensure what the values returned
 * are correct. These tests just ensure that the methods can be invoked.
 */
@SuppressWarnings("NewClassNamingConvention")
class BaseFakerIntegrationTest {
    private Faker faker;
    private Locale locale;

    private void init(Locale locale, Long seed) {
        this.locale = locale;
        if (locale != null && seed != null) {
            faker = new BaseFaker(locale, seed);
        } else if (locale != null) {
            faker = new BaseFaker(locale);
        } else if (seed != null) {
            faker = new BaseFaker(seed);
        } else {
            faker = new BaseFaker();
        }
    }   

    @ParameterizedTest
    @MethodSource("dataParameters")
    void testExceptionsNotCoveredInAboveTest(Locale locale, Long seed) {
        init(locale, seed);
        assertThat(faker.bothify("####???")).isNotNull();
        assertThat(faker.letterify("????")).isNotNull();
        assertThat(faker.numerify("####")).isNotNull();
    }

    private static Stream<Arguments> dataParameters() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(Locale.ENGLISH, new Random().nextLong()));
        arguments.add(Arguments.of(new Locale("pt-BR"), null));
        arguments.add(Arguments.of(new Locale("pt-br"), null));
        arguments.add(Arguments.of(new Locale("Pt_br"), null));
        arguments.add(Arguments.of(new Locale("pt", "Br", "x2"), null));
        arguments.add(Arguments.of(null, new Random().nextLong()));
        arguments.add(Arguments.of(null, null));

        String[] ymlFiles = new File("./src/main/resources").list();
        for (String ymlFileName : ymlFiles) {
            arguments.add(Arguments.of(new Locale(StringUtils.substringBefore(ymlFileName, ".")), null));
        }

        return arguments.stream();
    }
}
