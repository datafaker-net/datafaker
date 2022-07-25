package net.datafaker;

import java.util.Locale;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegressionTest extends AbstractFakerTest {

    @Test
    void bulgarianStreetName() {
        Faker localFaker = new Faker(new Locale("bg"));
        assertThat(localFaker.address().streetName()).isNotEmpty();
    }
}
