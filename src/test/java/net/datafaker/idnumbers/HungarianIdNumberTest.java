package net.datafaker.idnumbers;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.IdNumber.GenderRequest;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static net.datafaker.providers.base.PersonIdNumber.Gender.FEMALE;
import static net.datafaker.providers.base.PersonIdNumber.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

class HungarianIdNumberTest {
    private final BaseFaker faker = new BaseFaker();
    private final HungarianIdNumber hungarianIdNumber = new HungarianIdNumber();

    @RepeatedTest(10)
    void validUsesRequestedAgeAndGender() {
        PersonIdNumber female = hungarianIdNumber.generateValid(faker, new IdNumberRequest(1, 10, GenderRequest.FEMALE));
        assertThat(female.gender()).isEqualTo(FEMALE);
        assertThat(female.age()).isBetween(1, 10);
        assertThat(female.idNumber()).startsWith("4" + DateTimeFormatter.ofPattern("yyMMdd").format(female.birthDate()));

        PersonIdNumber male = hungarianIdNumber.generateValid(faker, new IdNumberRequest(30, 40, GenderRequest.MALE));
        assertThat(male.gender()).isEqualTo(MALE);
        assertThat(male.age()).isBetween(30, 40);
        assertThat(male.idNumber()).startsWith(HungarianIdNumber.firstDigit(male.birthDate().getYear(), MALE) + DateTimeFormatter.ofPattern("yyMMdd").format(male.birthDate()));
    }

    @Test
    void firstDigit() {
        assertThat(HungarianIdNumber.firstDigit(1899, MALE)).isEqualTo(3);
        assertThat(HungarianIdNumber.firstDigit(1900, MALE)).isEqualTo(1);
        assertThat(HungarianIdNumber.firstDigit(1901, MALE)).isEqualTo(1);
        assertThat(HungarianIdNumber.firstDigit(1899, FEMALE)).isEqualTo(4);
        assertThat(HungarianIdNumber.firstDigit(1900, FEMALE)).isEqualTo(2);
        assertThat(HungarianIdNumber.firstDigit(1901, FEMALE)).isEqualTo(2);
        assertThat(HungarianIdNumber.firstDigit(1999, MALE)).isEqualTo(1);
        assertThat(HungarianIdNumber.firstDigit(2000, MALE)).isEqualTo(3);
        assertThat(HungarianIdNumber.firstDigit(1999, FEMALE)).isEqualTo(2);
        assertThat(HungarianIdNumber.firstDigit(2000, FEMALE)).isEqualTo(4);
    }

    @Test
    void checkDigit() {
        assertThat(HungarianIdNumber.getCheckDigit("807159215")).isEqualTo(3);
        assertThat(HungarianIdNumber.getCheckDigit("100000000")).isEqualTo(1);
        assertThat(HungarianIdNumber.getCheckDigit("010000000")).isEqualTo(2);
        assertThat(HungarianIdNumber.getCheckDigit("001000000")).isEqualTo(3);
        assertThat(HungarianIdNumber.getCheckDigit("000000001")).isEqualTo(9);
        assertThat(HungarianIdNumber.getCheckDigit("170010103")).isEqualTo(10);
    }
}
