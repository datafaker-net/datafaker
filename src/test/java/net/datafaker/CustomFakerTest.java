package net.datafaker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Locale;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This is a demo of how to create a custom faker and register a custom faker in it.
 */
public class CustomFakerTest {
    public static class MyCustomFaker extends Faker {
        public Insect insect() {
            return getProvider(Insect.class, () -> new Insect(this));
        }

        public InsectFromFile insectFromFile() {
            return getProvider(InsectFromFile.class, () -> new InsectFromFile(this));
        }
    }

    public static class Insect {
        private static final String[] INSECT_NAMES = new String[]{"Ant", "Beetle", "Butterfly", "Wasp"};
        private final Faker faker;

        public Insect(Faker faker) {
            this.faker = faker;
        }

        public String nextInsectName() {
            return INSECT_NAMES[faker.random().nextInt(INSECT_NAMES.length)];
        }
    }

    public static class InsectFromFile {
        private static final String KEY = "insectsfromfile";
        private final Faker faker;

        public InsectFromFile(Faker faker) {
            this.faker = faker;
            faker.fakeValuesService().addPath(Locale.ENGLISH, Paths.get("src/test/ants.yml"));
            faker.fakeValuesService().addPath(Locale.ENGLISH, Paths.get("src/test/bees.yml"));
        }

        public String ant() {
            return faker.fakeValuesService().resolve(KEY + ".ants", null, faker);
        }

        public String bee() {
            return faker.fakeValuesService().resolve(KEY + ".bees", null, faker);
        }
    }

    @Test
    public void addNullExistingPath() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new Faker().fakeValuesService().addPath(Locale.ENGLISH, null));
    }

    @Test
    public void addNonExistingPath() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new Faker().fakeValuesService().addPath(Locale.ENGLISH, Paths.get("non-existing-file")));
    }

    @RepeatedTest(10)
    public void insectTest() {
        MyCustomFaker myFaker = new MyCustomFaker();
        assertThat(myFaker.insect().nextInsectName(), matchesRegularExpression("[A-Za-z ]+"));
    }

    @RepeatedTest(10)
    public void insectTestExpression() {
        MyCustomFaker myFaker = new MyCustomFaker();
        assertThat(myFaker.expression("#{Insect.nextInsectName}"), matchesRegularExpression("[A-Za-z ]+"));
    }

    @RepeatedTest(10)
    public void insectAntTestExpressionFromFile() {
        MyCustomFaker myFaker = new MyCustomFaker();
        assertThat(myFaker.insectFromFile().ant(), matchesRegularExpression("[A-Za-z ]+"));
    }

    @RepeatedTest(10)
    public void insectBeeTestExpressionFromFile() {
        MyCustomFaker myFaker = new MyCustomFaker();
        assertTrue(myFaker.insectFromFile().bee().endsWith("bee"));
    }
}
