package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EldenRingTest extends AbstractFakerTest{
    @Test
    public void location() {
        assertThat(faker.eldenRing().location()).matches("[A-Za-z0-9 .]+");
    }

    @Test
    public void weapon() {
        assertThat(faker.eldenRing().weapon()).matches("[A-Za-z ]+");
    }

    @Test
    public void skill() {
        assertThat(faker.eldenRing().skill()).matches("[A-Za-z' ]+");
    }

    @Test
    public void spell() {
        assertThat(faker.eldenRing().spell()).matches("[A-Za-z' ]+");
    }

    @Test
    public void npc() {
        assertThat(faker.eldenRing().npc()).matches("[A-Za-z ]+");
    }
}
