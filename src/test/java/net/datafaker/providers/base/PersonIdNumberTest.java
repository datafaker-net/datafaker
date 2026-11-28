package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static java.time.LocalDate.parse;
import static net.datafaker.providers.base.PersonIdNumber.Gender.FEMALE;
import static org.assertj.core.api.Assertions.assertThat;

class PersonIdNumberTest {
    @Test
    void age() {
        PersonIdNumber person = new PersonIdNumber("", parse("1980-02-29"), FEMALE);

        assertThat(person.age(parse("1980-02-29"))).isEqualTo(0);
        assertThat(person.age(parse("1981-02-28"))).isEqualTo(0);
        assertThat(person.age(parse("1981-03-01"))).isEqualTo(1);
        assertThat(person.age(parse("2026-10-11"))).isEqualTo(46);
        assertThat(person.age()).isGreaterThanOrEqualTo(46);
    }
}
