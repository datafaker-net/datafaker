package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SeinfeldTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.seinfeld().character()).isNotEmpty();
    }

    @Test
    public void quote() {
        assertThat(faker.seinfeld().quote()).isNotEmpty();
    }

    @Test
    public void business() {
        assertThat(faker.seinfeld().business()).isNotEmpty();
    }

}
