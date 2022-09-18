package net.datafaker.base;

import net.datafaker.AbstractFakerTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DcComicsTest extends AbstractFakerTest {

    @Test
    void hero() {
        assertThat(faker.dcComics().hero()).isNotEmpty();
    }

    @Test
    void heroine() {
        assertThat(faker.dcComics().heroine()).isNotEmpty();
    }

    @Test
    void villain() {
        assertThat(faker.dcComics().villain()).isNotEmpty();
    }

    @Test
    void name() {
        assertThat(faker.dcComics().name()).isNotEmpty();
    }

    @Test
    void title() {
        assertThat(faker.dcComics().title()).isNotEmpty();
    }

}
