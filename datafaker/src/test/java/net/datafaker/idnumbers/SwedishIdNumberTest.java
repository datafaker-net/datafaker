package net.datafaker.idnumbers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SwedishIdNumberTest {

    @Test
    void validSwedishSsn() {
        assertThat(SwedenIdNumber.isValidSwedishSsn("670919-9530")).isTrue();
        assertThat(SwedenIdNumber.isValidSwedishSsn("811228-9874")).isTrue();
    }

    @Test
    void invalidSwedishSsn() {
        assertThat(SwedenIdNumber.isValidSwedishSsn("8112289873")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("foo228-9873")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("811228-9873")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("811228-9875")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("811200-9874")).isFalse();
        assertThat(SwedenIdNumber.isValidSwedishSsn("810028-9874")).isFalse();
    }
}
