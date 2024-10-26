package net.datafaker.idnumbers;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static net.datafaker.idnumbers.SingaporeIdNumber.Type.FOREIGNER_TWENTIETH_CENTURY;
import static net.datafaker.idnumbers.SingaporeIdNumber.Type.FOREIGNER_TWENTY_FIRST_CENTURY;
import static net.datafaker.idnumbers.SingaporeIdNumber.Type.SINGAPOREAN_TWENTIETH_CENTURY;
import static net.datafaker.idnumbers.SingaporeIdNumber.Type.SINGAPOREAN_TWENTY_FIRST_CENTURY;
import static net.datafaker.idnumbers.SingaporeIdNumber.centuryPrefixCitizen;
import static net.datafaker.idnumbers.SingaporeIdNumber.centuryPrefixForeigner;
import static net.datafaker.idnumbers.SingaporeIdNumber.randomBirthDate;
import static org.assertj.core.api.Assertions.assertThat;

class SingaporeIdNumberTest {
    @Test
    void centuryPrefix_forCitizens() {
        assertThat(centuryPrefixCitizen(LocalDate.parse("1999-12-31"))).as("19xx = S").isEqualTo('S');
        assertThat(centuryPrefixCitizen(LocalDate.parse("2000-12-31"))).as("20xx = T").isEqualTo('T');
        assertThat(centuryPrefixCitizen(LocalDate.parse("2001-01-01"))).as("20xx = T").isEqualTo('T');
        assertThat(centuryPrefixCitizen(LocalDate.parse("2101-01-01"))).as("21xx = U").isEqualTo('U');
        assertThat(centuryPrefixCitizen(LocalDate.parse("2201-01-01"))).as("22xx = V").isEqualTo('V');
    }

    @Test
    void centuryPrefix_forForeigner() {
        assertThat(centuryPrefixForeigner(LocalDate.parse("1999-12-31"))).as("19xx = F").isEqualTo('F');
        assertThat(centuryPrefixForeigner(LocalDate.parse("2000-12-31"))).as("20xx = G").isEqualTo('G');
        assertThat(centuryPrefixForeigner(LocalDate.parse("2001-01-01"))).as("20xx = G").isEqualTo('G');
        assertThat(centuryPrefixForeigner(LocalDate.parse("2101-01-01"))).as("21xx = H").isEqualTo('H');
        assertThat(centuryPrefixForeigner(LocalDate.parse("2201-01-01"))).as("22xx = I").isEqualTo('I');
    }

    @Test
    void randomBirthDate_20th_century() {
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            assertThat(randomBirthDate(faker, SINGAPOREAN_TWENTIETH_CENTURY).getYear() / 100).isEqualTo(19);
            assertThat(randomBirthDate(faker, FOREIGNER_TWENTIETH_CENTURY).getYear() / 100).isEqualTo(19);
        }
    }

    @Test
    void randomBirthDate_21th_century() {
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            assertThat(randomBirthDate(faker, SINGAPOREAN_TWENTY_FIRST_CENTURY).getYear() / 100).isEqualTo(20);
            assertThat(randomBirthDate(faker, FOREIGNER_TWENTY_FIRST_CENTURY).getYear() / 100).isEqualTo(20);
        }
    }
}
