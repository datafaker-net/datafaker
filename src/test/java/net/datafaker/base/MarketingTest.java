package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MarketingTest extends BaseFakerTest<BaseFaker> {

    @Test
    void buzzwords() {
        assertThat(faker.marketing().buzzwords()).isNotEmpty();
    }

}
