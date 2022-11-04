package net.datafaker.providers.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OnePieceTest extends MovieFakerTest {


    @Test
    void character() {
        assertThat(faker.onePiece().character()).isNotEmpty();
    }

    @Test
    void sea() {
        assertThat(faker.onePiece().sea()).isNotEmpty();
    }

    @Test
    void island() {
        assertThat(faker.onePiece().island()).isNotEmpty();
    }

    @Test
    void location() {
        assertThat(faker.onePiece().location()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.onePiece().quote()).isNotEmpty();
    }

    @Test
    void akumasNoMi() {
        assertThat(faker.onePiece().akumasNoMi()).isNotEmpty();
    }
}
