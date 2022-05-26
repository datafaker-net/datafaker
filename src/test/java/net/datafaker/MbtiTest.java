package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.isNullOrEmpty;

class MbtiTest extends AbstractFakerTest {

    @RepeatedTest(1000)
    public void type() {
        assertThat(isNullOrEmpty(faker.mbti().type())).isFalse();
        assertThat(faker.mbti().type()).matches("[A-Za-z,\\-.() ]+");
    }

    @RepeatedTest(1000)
    public void name(){
        assertThat(isNullOrEmpty(faker.mbti().name())).isFalse();
        assertThat(faker.mbti().name()).matches("[A-Za-z,\\-.();:'$ ]+");
    }
    @RepeatedTest(1000)
    void characteristic() {
        assertThat(isNullOrEmpty(faker.mbti().characteristic())).isFalse();
    }

    @RepeatedTest(1000)
    void personage() {
        assertThat(isNullOrEmpty(faker.mbti().personage())).isFalse();
        assertThat(faker.mbti().personage()).matches("[A-Za-z,\\-.()';:$ ]+");
    }

    @RepeatedTest(1000)
    void merit() {
        assertThat(isNullOrEmpty(faker.mbti().merit())).isFalse();
    }

    @RepeatedTest(1000)
    void weakness() {
        assertThat(isNullOrEmpty(faker.mbti().weakness())).isFalse();
    }

}
