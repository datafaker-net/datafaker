package net.datafaker.service;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EvenlyDistributedRandomLongGeneratorTest {
    @Test
    void singleNumber() {
        EvenlyDistributedRandomLongGenerator generator = new EvenlyDistributedRandomLongGenerator(8L, 9L);
        assertThat(generator.nextLong()).isEqualTo(8L);
        assertThat(generator.nextLong()).isEqualTo(8L);
        assertThat(generator.nextLong()).isEqualTo(8L);
        assertThat(generator.nextLong()).isEqualTo(8L);
    }

    @Test
    void shortInterval() {
        EvenlyDistributedRandomLongGenerator generator = new EvenlyDistributedRandomLongGenerator(6L, 8L);
        assertThat(Set.of(generator.nextLong(), generator.nextLong())).containsExactlyInAnyOrder(6L, 7L);
    }

    @Test
    void bigNumbers() {
        long i = 7609772473929375770L;
        EvenlyDistributedRandomLongGenerator generator = new EvenlyDistributedRandomLongGenerator(i, i + 6);
        assertThat(Set.of(generator.nextLong(), generator.nextLong(), generator.nextLong(), generator.nextLong(),
            generator.nextLong(i, i + 6), generator.nextLong(i, i + 6))).containsExactlyInAnyOrder(i, i + 1, i + 2, i + 3, i + 4, i + 5);
    }
}
