package net.datafaker.idnumbers;

import net.datafaker.AbstractFakerTest;
import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class PortugueseIdNumberTest extends AbstractFakerTest {
    private final Faker ptFaker = new Faker(new Locale("pt", "PT"));

    @RepeatedTest(100)
    void testInvalid() {
        assertThat(ptFaker.idNumber().invalid()).matches("[0-9]{9,10}");
    }

    @RepeatedTest(100)
    void testValid() {
        assertThat(ptFaker.idNumber().valid()).matches("[0-9]{9,10}");
    }
}
