package net.datafaker.idnumbers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SwedishIdNumberTest {

    @Test
    void valid() {
        SvSEIdNumber idNumber = new SvSEIdNumber();
        assertThat(idNumber.isValidSwedishSsn("670919-9530")).isTrue();
        assertThat(idNumber.isValidSwedishSsn("811228-9874")).isTrue();
    }

    @Test
    void invalid() {
        SvSEIdNumber idNumber = new SvSEIdNumber();
        assertThat(idNumber.isValidSwedishSsn("8112289873")).isFalse();
        assertThat(idNumber.isValidSwedishSsn("foo228-9873")).isFalse();
        assertThat(idNumber.isValidSwedishSsn("811228-9873")).isFalse();
        assertThat(idNumber.isValidSwedishSsn("811228-9875")).isFalse();
        assertThat(idNumber.isValidSwedishSsn("811200-9874")).isFalse();
        assertThat(idNumber.isValidSwedishSsn("810028-9874")).isFalse();
    }
}
