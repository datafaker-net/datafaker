package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * This is a demo of how to create a custom faker and register a custom faker in it.
 */
class CustomFakerTest {
    public static class MyCustomFaker extends BaseFaker {
        public Insect insect() {
            return getProvider(Insect.class, Insect::new, this);
        }

        public InsectFromFile insectFromFile() {
            return getProvider(InsectFromFile.class, InsectFromFile::new, this);
        }
    }

    public static class Insect extends AbstractProvider<IProviders> {
        private static final String[] INSECT_NAMES = new String[]{"Ant", "Beetle", "Butterfly", "Wasp"};

        public Insect(BaseFaker faker) {
            super(faker);
        }

        public String nextInsectName() {
            return INSECT_NAMES[faker.random().nextInt(INSECT_NAMES.length)];
        }
    }

    public static class InsectFromFile extends AbstractProvider<IProviders> {
        private static final String KEY = "insectsfromfile";

        public InsectFromFile(BaseFaker faker) {
            super(faker);
            faker.addPath(Locale.ENGLISH, Paths.get("src/test/ants.yml"));
            faker.addPath(Locale.ENGLISH, Paths.get("src/test/bees.yml"));
        }

        public String ant() {
            return resolve(KEY + ".ants");
        }

        public String bee() {
            return resolve(KEY + ".bees");
        }
    }

    @Test
    void addNullExistingPath() {
        assertThatThrownBy(() -> new BaseFaker().addPath(Locale.ENGLISH, null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addNonExistingPath() {
        assertThatThrownBy(() -> new BaseFaker().addPath(Locale.ENGLISH, Paths.get("non-existing-file")))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @RepeatedTest(10)
    void insectTest() {
        MyCustomFaker myFaker = new MyCustomFaker();
        assertThat(myFaker.insect().nextInsectName()).matches("[A-Za-z ]+");
    }

    @RepeatedTest(10)
    void insectTestExpression() {
        MyCustomFaker myFaker = new MyCustomFaker();
        assertThat(myFaker.expression("#{Insect.nextInsectName}")).matches("[A-Za-z ]+");
    }

    @RepeatedTest(10)
    void insectAntTestExpressionFromFile() {
        MyCustomFaker myFaker = new MyCustomFaker();
        assertThat(myFaker.insectFromFile().ant()).matches("[A-Za-z ]+");
    }

    @RepeatedTest(10)
    void insectBeeTestExpressionFromFile() {
        MyCustomFaker myFaker = new MyCustomFaker();
        assertThat(myFaker.insectFromFile().bee()).endsWith("bee");
    }

    @Test
    void insectBeeTestExpressionFromFileWithoutExtraFaker() {
        BaseFaker faker = new BaseFaker();
        assertThat(faker.getProvider(InsectFromFile. class, f -> new InsectFromFile(f), faker).bee()).endsWith("bee");
    }

    @Test
    void insectTestWithoutExtraFaker() {
        BaseFaker faker = new BaseFaker();
        assertThat(faker.getProvider(Insect. class, f -> new Insect(f), faker).nextInsectName()).matches("[A-Za-z ]+");
    }
}
