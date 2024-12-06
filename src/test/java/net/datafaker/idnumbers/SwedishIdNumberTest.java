package net.datafaker.idnumbers;

import net.datafaker.Faker;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static net.datafaker.providers.base.IdNumber.GenderRequest.ANY;
import static org.assertj.core.api.Assertions.assertThat;

class SwedishIdNumberTest {
    private final Faker faker = new Faker();
    private final SwedenIdNumber impl = new SwedenIdNumber();

    @RepeatedTest(20)
    void swedishSsn_containsPlus_forPersonsOlderThan100Years() {
        PersonIdNumber person = impl.generateValid(faker, new IdNumberRequest(101, 125, ANY));
        assertThat(person.idNumber()).matches("(\\d{6})\\+(\\d{4})");
        assertThat(SwedenIdNumber.isValidSwedishSsn(person.idNumber())).as(person.idNumber()).isTrue();
    }

    @RepeatedTest(20)
    void swedishSsn_containsMinus_forPersonsYoungerThan100Years() {
        PersonIdNumber person = impl.generateValid(faker, new IdNumberRequest(23, 99, ANY));
        assertThat(person.idNumber()).matches("(\\d{6})-(\\d{4})");
        assertThat(SwedenIdNumber.isValidSwedishSsn(person.idNumber())).as(person.idNumber()).isTrue();
    }

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

    @Test
    void isYearOver100YearsAgo() {
        LocalDate today = LocalDate.of(2024, 6, 1);
        assertThat(SwedenIdNumber.isYearOver100YearsAgo("1900", today)).isTrue();
        assertThat(SwedenIdNumber.isYearOver100YearsAgo("1918", today)).isTrue();
        assertThat(SwedenIdNumber.isYearOver100YearsAgo("1924", today)).isTrue();
        assertThat(SwedenIdNumber.isYearOver100YearsAgo("1925", today)).isFalse();
        assertThat(SwedenIdNumber.isYearOver100YearsAgo("1990", today)).isFalse();
        assertThat(SwedenIdNumber.isYearOver100YearsAgo("2003", today)).isFalse();
        assertThat(SwedenIdNumber.isYearOver100YearsAgo("2035", today)).isFalse();
    }

    @Test
    void findYearBeginningFromSsn() {
        assertThat(SwedenIdNumber.findYearBeginningFromSsn("670919-9530")).isEqualTo("19");
        assertThat(SwedenIdNumber.findYearBeginningFromSsn("811228-9874")).isEqualTo("19");
        assertThat(SwedenIdNumber.findYearBeginningFromSsn("000225-9873")).isEqualTo("20");
        assertThat(SwedenIdNumber.findYearBeginningFromSsn("000225+9877")).isEqualTo("19");
        assertThat(SwedenIdNumber.findYearBeginningFromSsn("991221+4146")).isEqualTo("18");
        assertThat(SwedenIdNumber.findYearBeginningFromSsn("981227+0262")).isEqualTo("18");
    }
}
