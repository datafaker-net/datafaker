package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest extends AbstractFakerTest {

    @Test
    void testName() {
        assertThat(faker.app().name()).matches("([\\w-]+ ?)+");
    }

    @Test
    void testVersion() {
        assertThat(faker.app().version()).matches("\\d\\.(\\d){1,2}(\\.\\d)?");
    }

    @Test
    void testAuthor() {
        assertThat(faker.app().author()).matches("([\\w']+[-&,\\.]? ?){2,9}");
    }
}
