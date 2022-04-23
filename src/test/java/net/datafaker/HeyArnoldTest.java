package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HeyArnoldTest extends AbstractFakerTest {

    @Test
    public void characters() {
        assertThat(faker.heyArnold().characters()).isNotEmpty();
    }

    @Test
    public void locations() {
        assertThat(faker.heyArnold().locations()).isNotEmpty();
    }

    @Test
    public void quotes() {
        assertThat(faker.heyArnold().quotes()).isNotEmpty();
    }
}
