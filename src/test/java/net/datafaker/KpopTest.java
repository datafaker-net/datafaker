
package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KpopTest extends AbstractFakerTest {

    @Test
    public void iGroups() {
        assertThat(faker.kpop().iGroups()).isNotEmpty();
    }

    @Test
    public void iiGroups() {
        assertThat(faker.kpop().iiGroups()).isNotEmpty();
    }

    @Test
    public void iiiGroups() {
        assertThat(faker.kpop().iiiGroups()).isNotEmpty();
    }

    @Test
    public void girlGroups() {
        assertThat(faker.kpop().girlGroups()).isNotEmpty();
    }

    @Test
    public void boyBands() {
        assertThat(faker.kpop().boyBands()).isNotEmpty();
    }

    @Test
    public void solo() {
        assertThat(faker.kpop().solo()).isNotEmpty();
    }

}
