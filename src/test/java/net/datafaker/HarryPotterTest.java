package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.isNullOrEmpty;

class HarryPotterTest extends AbstractFakerTest {

    @Test
    void character() {
        assertThat(faker.harryPotter().character()).matches("[A-Za-z,\\-.() ]+");
    }

    @Test
    void location() {
        assertThat(faker.harryPotter().location()).matches("[A-Za-z0-9'. &,/]+");
    }

    @Test
    void quote() {
        assertThat(isNullOrEmpty(faker.harryPotter().quote())).isFalse();
    }

    @Test
    void book() {
        assertThat(faker.harryPotter().book()).matches("Harry Potter and the ([A-Za-z'\\-]+ ?)+");
    }

    @Test
    void house() {
        assertThat(faker.harryPotter().house()).matches("[A-Za-z ]+");
    }

    @Test
    void spell() {
        assertThat(faker.harryPotter().spell()).matches("[A-Za-z ]+");
    }
}
