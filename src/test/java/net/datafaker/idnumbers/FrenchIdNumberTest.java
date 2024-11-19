package net.datafaker.idnumbers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Random;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import net.datafaker.Faker;
import net.datafaker.helpers.IdNumberPatterns;

class FrenchIdNumberTest {
    private final FrenchIdNumber frenchIdNumber = new FrenchIdNumber();
    private final Faker faker = new Faker(new Locale("fr", "FR"));

    @Test
    void controlKey_samples() {
        assertThat(frenchIdNumber.controlKey("1510246102043")).isEqualTo("25");
        assertThat(frenchIdNumber.controlKey("1850572070008")).isEqualTo("58");
        assertThat(frenchIdNumber.controlKey("1211277131232")).isEqualTo("50");
        assertThat(frenchIdNumber.controlKey("121122A131232")).isEqualTo("64");
        assertThat(frenchIdNumber.controlKey("121122B010232")).isEqualTo("35");
        assertThat(frenchIdNumber.controlKey("1790332046090")).isEqualTo("01");
        assertThat(frenchIdNumber.controlKey("2757548135470")).isEqualTo("48");
        assertThat(frenchIdNumber.controlKey("2092006990120"))
            .as("Unknown birth mount = 20, unknown birth city = 990")
            .isEqualTo("61");
    }

    @RepeatedTest(100)
    void controlKey() {
        int controlKey = new Random().nextInt(1, 97);
        long factorNumber = new Random().nextLong(0, 99999999999L);
        long basePart = 97 * factorNumber + (97 - controlKey);
        assertThat(frenchIdNumber.controlKey("%013d".formatted(basePart))).isEqualTo("%02d".formatted(controlKey));
    }

    @RepeatedTest(100)
    void validFrenchIdNumber() {
        String actual = frenchIdNumber.generateValid(faker);

        assertThat(actual).matches(IdNumberPatterns.FRENCH);
        String basePart = actual.substring(0, 13);
        String controlKey = actual.substring(13);
        assertThat(frenchIdNumber.controlKey(basePart)).isEqualTo(controlKey);
    }

    @RepeatedTest(100)
    void invalidFrenchIdNumber() {
        String actual = frenchIdNumber.generateInvalid(faker);

        assertThat(actual).matches(IdNumberPatterns.FRENCH);
        String basePart = actual.substring(0, 13);
        String controlKey = actual.substring(13);
        assertThat(frenchIdNumber.controlKey(basePart)).isNotEqualTo(controlKey);
    }

}
