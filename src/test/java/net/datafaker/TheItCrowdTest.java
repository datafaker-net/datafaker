package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TheItCrowdTest extends AbstractFakerTest {

    @Test
    public void actors() {
        assertThat(faker.theItCrowd().actors()).isNotEmpty();
    }

    @Test
    public void characters() {
        assertThat(faker.theItCrowd().characters()).isNotEmpty();
    }

    @Test
    public void emails() {
        assertThat(faker.theItCrowd().emails()).isNotEmpty();
    }

    @Test
    public void quotes() {
        assertThat(faker.theItCrowd().quotes()).isNotEmpty();
    }

}
