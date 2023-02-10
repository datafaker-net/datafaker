package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NationTest extends BaseFakerTest<BaseFaker> {

    Nation nation = faker.nation();

    @Test
    void nationality() {
        assertThat(nation.nationality()).matches("\\P{Cc}+");
    }

    @Test
    void language() {
        assertThat(nation.language()).matches("[A-Za-z ]+");
    }

    @Test
    void capitalCity() {
        assertThat(nation.capitalCity()).matches("[A-Za-z .'()-]+");
    }

    @Test
    void flag() {
        String flag = nation.flag();

        // all utf8 emoji flags are at least 4 characters long and start with the same char
        assertThat(flag).hasSizeGreaterThanOrEqualTo(4);
        assertThat(flag.charAt(0)).isEqualTo('\uD83C');
    }

    @Test
    void isoLanguage() {
        assertThat(nation.isoLanguage()).matches("[a-z]{2}");
    }

    @Test
    void isoCountry() {
        assertThat(nation.isoCountry()).matches("[A-Z]{2}");
    }
}
