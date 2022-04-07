package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FriendsTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.friends().character()).matches("[A-Za-z .,]+");
    }

    @Test
    public void location() {
        assertThat(faker.friends().location()).matches("[\\w.', ]+");
    }

    @Test
    public void quote() {
        assertThat(faker.friends().quote()).isNotEmpty();
    }
}
