
package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KpopTest extends AbstractBaseFakerTest {

    @Test
    void iGroups() {
        assertThat(faker.kpop().iGroups()).isNotEmpty();
    }

    @Test
    void iiGroups() {
        assertThat(faker.kpop().iiGroups()).isNotEmpty();
    }

    @Test
    void iiiGroups() {
        assertThat(faker.kpop().iiiGroups()).isNotEmpty();
    }

    @Test
    void girlGroups() {
        assertThat(faker.kpop().girlGroups()).isNotEmpty();
    }

    @Test
    void boyBands() {
        assertThat(faker.kpop().boyBands()).isNotEmpty();
    }

    @Test
    void solo() {
        assertThat(faker.kpop().solo()).isNotEmpty();
    }

}
