package net.datafaker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Locale;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomFakerTest {
    public static class MyCustomFaker extends Faker {
        public SpaceForce spaceForce() {
            return getProvider(SpaceForce.class, () -> new SpaceForce<>(this));
        }

        public SpaceForceFromFile spaceForceFromFile() {
            return getProvider(SpaceForceFromFile.class, () -> new SpaceForceFromFile<>(this));
        }
    }

    public static class SpaceForce<T extends Faker> {
        private static final String[] ROCKET_NAMES = new String[]{"Appollo", "Soyuz", "Vostok", "Voskhod", "Progress", "Falcon", "Gemini", "Mercury"};
        private final T faker;

        public SpaceForce(T faker) {
            this.faker = faker;
        }

        public String nextRocketName() {
            return ROCKET_NAMES[faker.random().nextInt(ROCKET_NAMES.length)];
        }
    }

    public static class SpaceForceFromFile<T extends Faker> {
        private static final String KEY = "spaceforcefromfile";
        private final T faker;

        public SpaceForceFromFile(T faker) {
            this.faker = faker;
            faker.fakeValuesService().addPath(Locale.ENGLISH, Paths.get("src/test/rockets.yml"));
            faker.fakeValuesService().addPath(Locale.ENGLISH, Paths.get("src/test/rockets2.yml"));
        }

        public String rocketName() {
            return faker.fakeValuesService().resolve(KEY + ".rocketname", null, faker);
        }

        public String rocketName2() {
            return faker.fakeValuesService().resolve(KEY + ".rocketname2", null, faker);
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

    @Test
    public void myRocketTest() {
        MyCustomFaker myFaker = new MyCustomFaker();
        for (int i = 0; i < 10; i++) {
            assertThat(myFaker.spaceForce().nextRocketName(), matchesRegularExpression("[A-Za-z ]+"));
        }
    }

    @Test
    public void myRocketTestExpression() {
        MyCustomFaker myFaker = new MyCustomFaker();
        for (int i = 0; i < 10; i++) {
            assertThat(myFaker.expression("#{SpaceForce.nextRocketName}"), matchesRegularExpression("[A-Za-z ]+"));
        }
    }

    @Test
    public void myRocketTestExpressionFromFile() {
        MyCustomFaker myFaker = new MyCustomFaker();
        for (int i = 0; i < 10; i++) {
            assertThat(myFaker.spaceForceFromFile().rocketName(), matchesRegularExpression("[A-Za-z ]+"));
        }
    }

    @Test
    public void myRocketTestExpressionFromFile2() {
        MyCustomFaker myFaker = new MyCustomFaker();
        for (int i = 0; i < 10; i++) {
            assertTrue(myFaker.spaceForceFromFile().rocketName2().endsWith("2"));
        }
    }
}