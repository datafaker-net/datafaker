package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends AbstractFakerTest {

    @Test
    public void testName() {
        assertThat(faker.app().name()).matches("([\\w-]+ ?)+");
    }

    @Test
    public void testVersion() {
        assertThat(faker.app().version()).matches("\\d\\.(\\d){1,2}(\\.\\d)?");
    }

    @Test
    public void testAuthor() {
        assertThat(faker.app().author()).matches("([\\w']+[-&,\\.]? ?){2,9}");
    }
}
