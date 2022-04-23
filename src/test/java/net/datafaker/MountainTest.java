package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MountainTest extends AbstractFakerTest {

    @Test
    public void testMountainName() {
        String mountainName = faker.mountain().name();
        assertThat(mountainName).isNotEmpty();
    }

    @Test
    public void testMountainLeague() {
        String mountainLeague = faker.mountain().range();
        assertThat(mountainLeague).isNotEmpty();
    }
}
