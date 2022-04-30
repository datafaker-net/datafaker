package net.datafaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class RandomFakerTest extends AbstractFakerTest {

    private static final int CONSTANT_SEED_VALUE = 10;
    private Faker faker;
    private Random random;

    @BeforeEach
    public void before() {
        super.before();
        random = new Random();
        faker = new Faker(random);
    }

    @Test
    void testNumerifyRandomnessCanBeControlled() {
        resetRandomSeed();
        final String firstInvocation = faker.numerify("###");

        resetRandomSeed();
        final String secondInvocation = faker.numerify("###");
        assertThat(firstInvocation).isEqualTo(secondInvocation);
    }

    @Test
    void testLetterifyRandomnessCanBeControlled() {
        resetRandomSeed();
        final String firstInvocation = faker.letterify("???");

        resetRandomSeed();
        final String secondInvocation = faker.letterify("???");
        assertThat(firstInvocation).isEqualTo(secondInvocation);
    }

    @Test
    void testNameRandomnessCanBeControlled() {
        resetRandomSeed();
        final String firstInvocation = faker.name().name();

        resetRandomSeed();
        final String secondInvocation = faker.name().name();
        assertThat(firstInvocation).isEqualTo(secondInvocation);
    }

    @Test
    void testEmailRandomnessCanBeControlled() {
        resetRandomSeed();
        final String firstInvocation = faker.internet().emailAddress();

        resetRandomSeed();
        final String secondInvocation = faker.internet().emailAddress();
        assertThat(firstInvocation).isEqualTo(secondInvocation);
    }

    private void resetRandomSeed() {
        random.setSeed(CONSTANT_SEED_VALUE);
    }
}
