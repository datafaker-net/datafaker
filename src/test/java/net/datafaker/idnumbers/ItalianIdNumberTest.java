package net.datafaker.idnumbers;

import static net.datafaker.providers.base.IdNumber.GenderRequest.ANY;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import net.datafaker.Faker;
import net.datafaker.providers.base.IdNumber.IdNumberRequest;
import net.datafaker.providers.base.PersonIdNumber;

class ItalianIdNumberTest {

    private static final Locale LOCALE = new Locale("it", "IT");
    private final ItalianIdNumber impl = new ItalianIdNumber();

    @Test
    void consonants() {
        assertThat(impl.encodeName("OOOOOP")).isEqualTo("POO");
        assertThat(impl.encodeName("HELLO")).isEqualTo("HLL");
        assertThat(impl.encodeName("HELLO")).isEqualTo("HLL");
        assertThat(impl.encodeName("ANNA")).isEqualTo("NNA");
        assertThat(impl.encodeName("AIROLI")).isEqualTo("RLA");
        assertThat(impl.encodeName("AIROUE")).isEqualTo("RAI");
        assertThat(impl.encodeName("FO")).isEqualTo("FOX");
        assertThat(impl.encodeName("OF")).isEqualTo("FOX");
        assertThat(impl.encodeName("F")).isEqualTo("FXX");
        assertThat(impl.encodeName("D'AMICO")).isEqualTo("DMC");
        assertThat(impl.encodeName("D`AMICO")).isEqualTo("DMC");
        assertThat(impl.encodeName("D_AMICO")).isEqualTo("DMC");
        assertThat(impl.encodeName("DE ROSA")).isEqualTo("DRS");
        assertThat(impl.encodeName("ÜÜLAR ÄHO")).isEqualTo("LRH");
        assertThat(impl.encodeName("")).isEqualTo("XXX");
    }

    /**
     * Example from <a href="https://fiscomania.com/carattere-controllo/">web</a>
     */
    @Test
    void checksum() {
        assertThat(impl.checksum("BBBTTT20H12X122")).isEqualTo('H');
        assertThat(impl.checksum("AAAAAAAAAAAAAAA"))
            .as("'A' is 1 on odd positions, 'A' is 0 on even positions. So, 1*8 + 7*0 = 8 -> 'I'")
            .isEqualTo('I');
        assertThat(impl.checksum("101010101010101"))
            .as("'1' is 0 on odd positions, '0' is 0 on even positions. So, 0*8 + 0*0 = 0 -> 'A'")
            .isEqualTo('A');
        assertThat(impl.checksum("H01010101010101"))
            .as("'H' is 17 on odd positions, '0' is 0 on even positions. So, 17 + 0*7 + 0*0 = 17 -> 'R'")
            .isEqualTo('R');
    }

    @RepeatedTest(100)
    void checksumShouldMatchForValidCodes() {
        PersonIdNumber personIdNumber = impl.generateValid(new Faker(LOCALE), new IdNumberRequest(1, 200, ANY));
        String idNumber = personIdNumber.idNumber();
        assertThat(impl.checksum(idNumber.substring(0, 15))).isEqualTo(idNumber.charAt(15));
    }

    @RepeatedTest(10)
    void checksumShouldNotMatchForInvalidCodes() {
        String idNumber = impl.generateInvalid(new Faker(LOCALE));
        assertThat(impl.checksum(idNumber.substring(0, 15))).isNotEqualTo(idNumber.charAt(15));
    }

}
