package net.datafaker.idnumbers;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.parseInt;
import static net.datafaker.providers.base.PersonIdNumber.Gender.FEMALE;
import static net.datafaker.providers.base.PersonIdNumber.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

class AlbanianIdNumberTest {
    private final AlbanianIdNumber generator = new AlbanianIdNumber();
    private final Faker faker = new Faker();

    @RepeatedTest(100)
    void valid() {
        String pin = generator.generateValid(faker);
        assertThat(pin.length()).isEqualTo(10);
    }

    @RepeatedTest(100)
    void invalid() {
        String pin = generator.generateInvalid(faker);
        assertThat(pin.length()).isEqualTo(10);
        assertThat(parseInt(pin.substring(2, 4)))
            .as(() -> "Presumably invalid PIN: '%s'".formatted(pin))
            .isGreaterThan(92);
    }

    @Test
    void yy() {
        assertThat(generator.yy(1806)).isEqualTo("06");
        assertThat(generator.yy(1812)).isEqualTo("12");
        assertThat(generator.yy(1829)).isEqualTo("29");
        assertThat(generator.yy(1880)).isEqualTo("80");
        assertThat(generator.yy(1888)).isEqualTo("88");
        assertThat(generator.yy(1900)).isEqualTo("A0");
        assertThat(generator.yy(1911)).isEqualTo("B1");
        assertThat(generator.yy(1929)).isEqualTo("C9");
        assertThat(generator.yy(1981)).isEqualTo("I1");
        assertThat(generator.yy(2003)).isEqualTo("K3");
        assertThat(generator.yy(2016)).isEqualTo("L6");
        assertThat(generator.yy(2099)).isEqualTo("T9");
    }

    @Test
    void mm() {
        assertThat(generator.mm(1, MALE)).isEqualTo("01");
        assertThat(generator.mm(2, MALE)).isEqualTo("02");
        assertThat(generator.mm(9, MALE)).isEqualTo("09");
        assertThat(generator.mm(12, MALE)).isEqualTo("12");
        assertThat(generator.mm(1, FEMALE)).isEqualTo("51");
        assertThat(generator.mm(2, FEMALE)).isEqualTo("52");
        assertThat(generator.mm(8, FEMALE)).isEqualTo("58");
        assertThat(generator.mm(12, FEMALE)).isEqualTo("62");
    }

    @Test
    void dd() {
        assertThat(generator.dd(1)).isEqualTo("01");
        assertThat(generator.dd(9)).isEqualTo("09");
        assertThat(generator.dd(10)).isEqualTo("10");
        assertThat(generator.dd(31)).isEqualTo("31");
    }

    @Test
    void checksum() {
        assertThat(generator.checksum("J11024000")).isEqualTo('R');
        assertThat(generator.checksum("J45405000")).isEqualTo('O');
        assertThat(generator.checksum("J00923000")).isEqualTo('N');
        assertThat(generator.checksum("I05101999")).isEqualTo('I');
    }

    @Test
    void checksumOfFirstChar() {
        assertThat(generator.checksumOfFirstChar('0')).isEqualTo(0);
        assertThat(generator.checksumOfFirstChar('1')).isEqualTo(1);
        assertThat(generator.checksumOfFirstChar('3')).isEqualTo(3);
        assertThat(generator.checksumOfFirstChar('9')).isEqualTo(9);
        assertThat(generator.checksumOfFirstChar('A')).isEqualTo(1);
        assertThat(generator.checksumOfFirstChar('B')).isEqualTo(2);
        assertThat(generator.checksumOfFirstChar('V')).isEqualTo(22);
        assertThat(generator.checksumOfFirstChar('W')).isEqualTo(0);
    }
}
