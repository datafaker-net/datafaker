package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author CharlotteE67
 */
class PassportTest extends AbstractFakerTest {

    @Test
    void testChValid() {
        String passport = faker.passport().chValid();
        assertThat(passport.charAt(0) == 'E' || passport.charAt(0) == 'G').isTrue();
        if (passport.charAt(0) == 'G') {
            for (int i = 1; i < passport.length(); i++) {
                assertThat(Character.isDigit(passport.charAt(i))).isTrue();
            }
        } else {
            assertThat(passport.charAt(1) == 'I' || passport.charAt(1) == 'O').isFalse();
            assertThat(Character.isLetter(passport.charAt(1)) || Character.isDigit(passport.charAt(1))).isTrue();
            for (int i = 2; i < passport.length(); i++) {
                assertThat(Character.isDigit(passport.charAt(i))).isTrue();
            }
        }
    }

    @Test
    void testChValidLength() {
        assertThat(faker.passport().chValid()).hasSize(9);
    }

    @Test
    void testChInValid() {
        assertThat(faker.passport().chInvalid().matches("E[0-9A-HJ-NP-Z][0-9]{7}")
            && faker.passport().chInvalid().matches("G[0-9]{8}")).isFalse();
    }

    @Test
    void testChInValidNotNull() {
        assertThat(faker.passport().chInvalid()).isNotNull();
    }

    @Test
    void testAmValid() {
        assertThat(faker.passport().amValid().matches("[0-9]{8}")).isTrue();
    }

    @Test
    void testAmValidLength() {
        assertThat(faker.passport().amValid()).hasSize(8);
    }

    @Test
    void testAmInValid() {
        assertThat(faker.passport().amInvalid().matches("[0-9]{8}")).isFalse();
    }

    @Test
    void testAmInValidNotNull() {
        assertThat(faker.passport().amInvalid()).isNotNull();
    }

    @RepeatedTest(100)
    void testChValidFrequently() {
        testChValid();
    }

    @RepeatedTest(100)
    void testChInValidFrequently() {
        testChInValid();
    }
}
