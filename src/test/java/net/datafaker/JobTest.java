package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JobTest extends AbstractFakerTest {

    @Test
    public void field() {
        assertThat(faker.job().field()).matches("^[A-Z][A-Za-z-]+$");
    }

    @Test
    public void seniority() {
        assertThat(faker.job().seniority()).matches("^[A-Z][a-z]+$");
    }

    @Test
    public void position() {
        assertThat(faker.job().position()).matches("^[A-Z][a-z]+$");
    }

    @Test
    public void keySkills() {
        assertThat(faker.job().keySkills()).matches("([A-Za-z-]+ ?){1,3}");
    }

    @Test
    public void title() {
        assertThat(faker.job().title()).matches("([A-Za-z-]+ ?){2,3}");
    }
}
