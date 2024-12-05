package net.datafaker.idnumbers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SwedishIdNumberTest {

    @Test
    void validSwedishSsn() {
        assertThat(SwedenIdNumber.isValidSwedishSsn("670919-9530")).isTrue();
        assertThat(SwedenIdNumber.isValidSwedishSsn("811228-9874")).isTrue();
        assertThat(SwedenIdNumber.isValidSwedishSsn("000229-9873")).isTrue();
        assertThat(SwedenIdNumber.isValidSwedishSsn("991221+4146")).isTrue();
        assertThat(SwedenIdNumber.isValidSwedishSsn("991227+0262")).isTrue();
    }

    @Test
    void invalidSwedishSsn() {
        assertThat(SwedenIdNumber.isValidSwedishSsn("020914-0003")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("8112289873")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("foo228-9873")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("811228-9873")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("811228-9875")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("811200-9874")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("810028-9874")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("000229+9873")).isFalse();
    }

    @ParameterizedTest
    @MethodSource("getYears")
    void testIsYearOver100YearsAgo(String year, boolean value) {
        assertThat(SwedenIdNumber.isYearOver100YearsAgo(year, LocalDate.of(2024, 6, 1))).isEqualTo(value);
    }

    private static Stream<Arguments> getYears() {
        return Stream.of(
            Arguments.of("1900", true),
            Arguments.of("1918", true),
            Arguments.of("1924", true),
            Arguments.of("1990", false),
            Arguments.of("2003", false),
            Arguments.of("2035", false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideSsnForFindYearBeginning")
    void testFindYearBeginningFromSsn(String ssn, String expectedYearBeginning) {
        assertThat(SwedenIdNumber.findYearBeginningFromSsn(ssn)).isEqualTo(expectedYearBeginning);
    }

    private static Stream<Arguments> provideSsnForFindYearBeginning() {
        return Stream.of(
            Arguments.of("670919-9530", "19"),
            Arguments.of("811228-9874", "19"),
            Arguments.of("000225-9873", "20"),
            Arguments.of("000225+9877", "19"),
            Arguments.of("991221+4146", "18"),
            Arguments.of("981227+0262", "18")
        );
    }
}
