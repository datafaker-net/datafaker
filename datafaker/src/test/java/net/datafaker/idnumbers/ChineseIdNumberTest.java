package net.datafaker.idnumbers;

import net.datafaker.AbstractFakerTest;
import net.datafaker.providers.base.BaseFaker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class ChineseIdNumberTest extends AbstractFakerTest {
    private final BaseFaker faker = new BaseFaker(new Locale("zh", "CN"));

    @RepeatedTest(10)
    void testValidChineseIdNumber() {
        String idNumber = faker.idNumber().valid();
        final int length = idNumber.length();
        assertThatSsnNumberValid(length, idNumber);
    }

    @ParameterizedTest
    @CsvSource({
        "420302198411073380",
        "310104196302135471",
        "330109197812225952",
        "130303200202132943",
        "370405198908011564",
        "350403198610147315",
        "640106198409178736",
        "469002199907068677",
        "440307199907065608",
        "150301196606308919",
        "21030319900605273X"
    })
    void testChecksumOfChineseIdNumber(String idNumber) {
        String first17Digit = idNumber.substring(0, idNumber.length() - 1);
        assertThat(ChineseIdNumber.idNumber(first17Digit.toCharArray())).isEqualTo(idNumber);
    }

    @RepeatedTest(100)
    void testValidZhCnIdNumber() {
        ChineseIdNumber id = new ChineseIdNumber();
        String idNumber = id.generateValid(faker);
        assertThatSsnNumberValid(idNumber.length(), idNumber);
    }

    private static void assertThatSsnNumberValid(int idNumber, String idNumber1) {
        boolean isSatisfied = idNumber == 18;
        for (int j = 0; j < idNumber; j++) {
            char ch = idNumber1.charAt(j);
            if (j != idNumber - 1) {
                if (ch > '9' || ch < '0') {
                    isSatisfied = false;
                    break;
                }
            } else {
                if ((ch > '9' || ch < '0') && ch != 'X') {
                    isSatisfied = false;
                    break;
                }
            }
        }
        assertThat(isSatisfied).isTrue();
    }
}
