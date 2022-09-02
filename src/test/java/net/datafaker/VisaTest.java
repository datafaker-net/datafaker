package net.datafaker;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

class VisaTest extends AbstractFakerTest {

    @Test
    void cardNumber() {
        AssertionsForClassTypes.assertThat(faker.visa().cardNumber()).matches("4[0-9]{12}(?:[0-9]{3})?");
    }

    @Test
    void cardName() {
        AssertionsForClassTypes.assertThat(faker.visa().cardName()).matches("^[A-Z][a-zA-z ]{1,29}$");
    }

    @Test
    void expiryDate() {
        AssertionsForClassTypes.assertThat(faker.visa().expiryDate()).matches("(?:0[1-9]|1[0-2])/[0-9]{2}");

    }
    @Test
    void csv() {
        AssertionsForClassTypes.assertThat(faker.visa().csv()).matches("[0-9]{3}");
    }
}
