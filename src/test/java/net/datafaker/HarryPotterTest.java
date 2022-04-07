package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.isNullOrEmpty;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class HarryPotterTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.harryPotter().character()).matches("[A-Za-z,\\-.() ]+");
    }

    @Test
    public void location() {
        assertThat(faker.harryPotter().location()).matches("[A-Za-z0-9'. &,/]+");
    }

    @Test
    public void quote() {
        assertFalse(isNullOrEmpty(faker.harryPotter().quote()));
    }

    @Test
    public void book() {
        assertThat(faker.harryPotter().book()).matches("Harry Potter and the ([A-Za-z'\\-]+ ?)+");
    }

    @Test
    public void house() {
        assertThat(faker.harryPotter().house()).matches("[A-Za-z ]+");
    }

    @Test
    public void spell() {
        assertThat(faker.harryPotter().spell()).matches("[A-Za-z ]+");
    }
}
