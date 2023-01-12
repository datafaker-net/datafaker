package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoraemonTest extends MovieFakerTest {

    @Test
    void testCharacter() {
        assertThat(faker.doraemon().character()).isNotEmpty();
    }

    @Test
    void testGadget() {
        assertThat(faker.doraemon().gadget()).isNotEmpty();
    }

    @Test
    void testLocation() {
        assertThat(faker.doraemon().location()).isNotEmpty();
    }

}
