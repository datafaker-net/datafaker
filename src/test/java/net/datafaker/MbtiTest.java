package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.isNullOrEmpty;

class MbtiTest extends AbstractFakerTest {

    @Test
    public void type() {
        assertThat(isNullOrEmpty(faker.mbti().type())).isFalse();
        assertThat(faker.mbti().type()).matches("[A-Za-z,\\-.() ]+");
    }

    @Test
    public void name(){
        assertThat(isNullOrEmpty(faker.mbti().name())).isFalse();
        assertThat(faker.mbti().name()).matches("[A-Za-z,\\-.();:'$ ]+");
    }
    @Test
    void characteristic() {
        assertThat(isNullOrEmpty(faker.mbti().characteristic())).isFalse();
    }

    @Test
    void personage() {
        assertThat(isNullOrEmpty(faker.mbti().personage())).isFalse();
        assertThat(faker.mbti().personage()).matches("[A-Za-z,\\-.()';:$ ]+");
    }

    @Test
    void merit() {
        assertThat(isNullOrEmpty(faker.mbti().merit())).isFalse();
    }

    @Test
    void weakness() {
        assertThat(isNullOrEmpty(faker.mbti().weakness())).isFalse();
    }

}
