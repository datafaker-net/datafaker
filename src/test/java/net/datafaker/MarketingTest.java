package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MarketingTest extends AbstractFakerTest {

    @Test
    public void buzzwords() {
        assertThat(faker.marketing().buzzwords()).isNotEmpty();
    }

}
