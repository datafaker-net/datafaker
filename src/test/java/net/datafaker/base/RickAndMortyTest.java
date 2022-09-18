package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RickAndMortyTest extends AbstractBaseFakerTest {

    @Test
    void character() {
        assertThat(faker.rickAndMorty().character()).matches("^([\\w'-.]+ ?){2,}$");
    }

    @Test
    void location() {
        assertThat(faker.rickAndMorty().location()).matches("^([\\w-.]+ ?){2,}$");
    }

    @Test
    void quote() {
        assertThat(faker.rickAndMorty().quote()).isNotEmpty();
    }
}
