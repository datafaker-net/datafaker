package net.datafaker.base;

import net.datafaker.AbstractFakerTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MountainTest extends AbstractFakerTest {

    @Test
    void testMountainName() {
        String mountainName = faker.mountain().name();
        assertThat(mountainName).isNotEmpty();
    }

    @Test
    void testMountainLeague() {
        String mountainLeague = faker.mountain().range();
        assertThat(mountainLeague).isNotEmpty();
    }
}
