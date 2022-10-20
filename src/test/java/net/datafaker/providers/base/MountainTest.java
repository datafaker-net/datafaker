package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MountainTest extends BaseFakerTest<BaseFaker> {

    @Test
    void mountainName() {
        String mountainName = faker.mountain().name();
        assertThat(mountainName).isNotEmpty();
    }

    @Test
    void mountainLeague() {
        String mountainLeague = faker.mountain().range();
        assertThat(mountainLeague).isNotEmpty();
    }
}
