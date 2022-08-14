package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunityTest extends AbstractFakerTest {

    @Test
    void testCharacter() {
        assertThat(faker.community().character()).isNotEmpty();
    }

    @Test
    void testQuote() {
        assertThat(faker.community().quote()).isNotEmpty();
    }
}
