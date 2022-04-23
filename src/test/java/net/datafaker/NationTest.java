package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NationTest extends AbstractFakerTest {

    @Test
    public void nationality() {
        assertThat(faker.nation().nationality()).matches("\\P{Cc}+");
    }

    @Test
    public void language() {
        assertThat(faker.nation().language()).matches("[A-Za-z ]+");
    }

    @Test
    public void capitalCity() {
        assertThat(faker.nation().capitalCity()).matches("[A-Za-z .'()-]+");
    }

    @Test
    public void flag() {
        String flag = faker.nation().flag();

        // all utf8 emoji flags are at least 4 characters long and start with the same char
        assertThat(flag.length()).isGreaterThanOrEqualTo(4);
        assertThat(flag.charAt(0)).isEqualTo('\uD83C');
    }

    @Test
    public void isoLanguage() {
        assertThat(faker.nation().isoLanguage()).matches("[a-z]{2}");
    }

    @Test
    public void isoCountry() {
        assertThat(faker.nation().isoCountry()).matches("[A-Z]{2}");
    }
}
