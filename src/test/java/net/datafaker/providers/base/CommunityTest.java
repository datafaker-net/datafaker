package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommunityTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testCharacter() {
        assertThat(faker.community().character()).isNotEmpty();
    }

    @Test
    void testQuote() {
        assertThat(faker.community().quote()).isNotEmpty();
    }
}
