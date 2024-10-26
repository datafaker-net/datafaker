package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.isNullOrEmpty;

class MbtiTest extends BaseFakerTest<BaseFaker> {

    private final Mbti mbti = faker.mbti();

    @Test
    void type() {
        assertThat(isNullOrEmpty(mbti.type())).isFalse();
        assertThat(mbti.type()).matches("[A-Za-z,\\-.() ]+");
    }

    @Test
    void name() {
        assertThat(isNullOrEmpty(mbti.name())).isFalse();
        assertThat(mbti.name()).matches("[A-Za-z,\\-.();:'$ ]+");
    }

    @Test
    void characteristic() {
        assertThat(isNullOrEmpty(mbti.characteristic())).isFalse();
    }

    @Test
    void personage() {
        assertThat(isNullOrEmpty(mbti.personage())).isFalse();
        assertThat(mbti.personage()).matches("[A-Za-z,\\-.()';:$ ]+");
    }

    @Test
    void merit() {
        assertThat(isNullOrEmpty(mbti.merit())).isFalse();
    }

    @Test
    void weakness() {
        assertThat(isNullOrEmpty(mbti.weakness())).isFalse();
    }

}
