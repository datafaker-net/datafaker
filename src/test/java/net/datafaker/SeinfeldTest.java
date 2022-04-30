package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SeinfeldTest extends AbstractFakerTest {

    @Test
    void character() {
        assertThat(faker.seinfeld().character()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.seinfeld().quote()).isNotEmpty();
    }

    @Test
    void business() {
        assertThat(faker.seinfeld().business()).isNotEmpty();
    }

}
