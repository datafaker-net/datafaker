//package net.datafaker.providers.base;
//
//import org.junit.jupiter.api.RepeatedTest;
//import org.junit.jupiter.api.Test;
//
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
///**
// * This is a demo of how to create a custom data provider and register a custom faker to use it.
// */
//class CustomFakerTest {
//    public static class MyCustomFaker extends BaseFaker {
//        public Insect insect() {
//            return getProvider(Insect.class, Insect::new);
//        }
//
//        public InsectFromFile insectFromFile() {
//            return getProvider(InsectFromFile.class, InsectFromFile::new);
//        }
//    }
//
//    public static class Insect extends AbstractProvider<BaseProviders> {
//        private static final String[] INSECT_NAMES = {"Ant", "Beetle", "Butterfly", "Wasp"};
//
//        public Insect(BaseProviders faker) {
//            super(faker);
//        }
//
//        public String nextInsectName() {
//            return INSECT_NAMES[faker.random().nextInt(INSECT_NAMES.length)];
//        }
//
//        public String weightedInsectName() {
//            List<Map<String, Object>> insects = List.of(
//                Map.of("value", "Driver ant", "weight", 6.0),
//                Map.of("value", "Fire ant", "weight", 3.0),
//                Map.of("value", "Harvester ant", "weight", 1.0)
//            );
//
//            return faker.weightedRandomSelector().select(insects);
//        }
//    }
//
//    public static class InsectFromFile extends AbstractProvider<BaseProviders> {
//        private static final String KEY = "insectsfromfile";
//
//        public InsectFromFile(BaseProviders faker) {
//            super(faker);
//            faker.addPath(Locale.ENGLISH, Paths.get("src/test/ants.yml"));
//            faker.addPath(Locale.ENGLISH, Paths.get("src/test/bees.yml"));
//            faker.addPath(Locale.ENGLISH, Paths.get("src/test/weightedAnts.yml"));
//        }
//
//        public String ant() {
//            return resolve(KEY + ".ants");
//        }
//
//        public String bee() {
//            return resolve(KEY + ".bees");
//        }
//
//        public String weightedInsectName() {
//            List<Map<String, Object>> insects = faker.fakeValuesService().fetchWeightedList(KEY + ".weightedAnts", faker.getContext());
//            return faker.weightedRandomSelector().select(insects);
//        }
//    }
//
//    @Test
//    void addNullExistingPath() {
//        assertThatThrownBy(() -> new BaseFaker().addPath(Locale.ENGLISH, null))
//            .isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @Test
//    void addNonExistingPath() {
//        assertThatThrownBy(() -> new BaseFaker().addPath(Locale.ENGLISH, Paths.get("non-existing-file")))
//            .isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @RepeatedTest(10)
//    void insectTest() {
//        MyCustomFaker myFaker = new MyCustomFaker();
//        assertThat(myFaker.insect().nextInsectName()).matches("[A-Za-z ]+");
//    }
//
//    @RepeatedTest(10)
//    void insectTestExpression() {
//        MyCustomFaker myFaker = new MyCustomFaker();
//        assertThat(myFaker.expression("#{Insect.nextInsectName}")).matches("[A-Za-z ]+");
//    }
//
//    @RepeatedTest(10)
//    void insectAntTestExpressionFromFile() {
//        MyCustomFaker myFaker = new MyCustomFaker();
//        assertThat(myFaker.insectFromFile().ant()).matches("[A-Za-z ]+");
//    }
//
//    @RepeatedTest(10)
//    void insectBeeTestExpressionFromFile() {
//        MyCustomFaker myFaker = new MyCustomFaker();
//        assertThat(myFaker.insectFromFile().bee()).endsWith("bee");
//    }
//
//    @Test
//    void insectBeeTestExpressionFromFileWithoutExtraFaker() {
//        BaseFaker faker = new BaseFaker();
//        assertThat(BaseFaker.getProvider(InsectFromFile.class, InsectFromFile::new, faker).bee()).endsWith("bee");
//    }
//
//    @Test
//    void insectTestWithoutExtraFaker() {
//        BaseFaker faker = new BaseFaker();
//        assertThat(BaseFaker.getProvider(Insect.class, Insect::new, faker).nextInsectName()).matches("[A-Za-z ]+");
//    }
//
//    @Test
//    void testMultipleFakerContextsPerOneClassName() {
//        class InsectFaker extends BaseFaker {
//            public InsectFaker(Locale locale) {
//                super(locale);
//            }
//        }
//        BaseFaker faker1 = new InsectFaker(Locale.ENGLISH);
//        BaseFaker faker2 = new InsectFaker(Locale.GERMAN);
//
//        Insect insect1 = faker1.getProvider(Insect.class, Insect::new);
//        Insect insect2 = faker2.getProvider(Insect.class, Insect::new);
//        assertThat(insect1).isNotNull();
//        assertThat(insect2).isNotNull();
//        assertThat(insect1).isNotSameAs(insect2);
//    }
//
//    @Test
//    void weightedInsectNameTest() {
//        MyCustomFaker myFaker = new MyCustomFaker();
//        Map<String, Integer> insectCounts = new HashMap<>();
//        insectCounts.put("Driver ant", 0);
//        insectCounts.put("Fire ant", 0);
//        insectCounts.put("Harvester ant", 0);
//
//        for (int i = 0; i < 100; i++) {
//            String selectedInsect = myFaker.insect().weightedInsectName();
//            insectCounts.put(selectedInsect, insectCounts.get(selectedInsect) + 1);
//        }
//
//        assertThat(insectCounts.get("Driver ant")).isGreaterThan(insectCounts.get("Fire ant"));
//        assertThat(insectCounts.get("Fire ant")).isGreaterThan(insectCounts.get("Harvester ant"));
//    }
//
//    @Test
//    void weightedInsectNameFromFileTest() {
//        MyCustomFaker myFaker = new MyCustomFaker();
//        Map<String, Integer> insectCounts = new HashMap<>();
//        insectCounts.put("Driver ant", 0);
//        insectCounts.put("Fire ant", 0);
//        insectCounts.put("Harvester ant", 0);
//
//        for (int i = 0; i < 100; i++) {
//            String selectedInsect = myFaker.insectFromFile().weightedInsectName();
//            insectCounts.put(selectedInsect, insectCounts.get(selectedInsect) + 1);
//        }
//
//        assertThat(insectCounts.get("Driver ant")).isGreaterThan(insectCounts.get("Fire ant"));
//        assertThat(insectCounts.get("Fire ant")).isGreaterThan(insectCounts.get("Harvester ant"));
//    }
//}
