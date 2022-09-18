package net.datafaker.base;

import net.datafaker.AbstractFakerTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MarketingTest extends AbstractFakerTest {

    @Test
    void buzzwords() {
        assertThat(faker.marketing().buzzwords()).isNotEmpty();
    }

}
