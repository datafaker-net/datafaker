package net.datafaker.integration;

import net.datafaker.base.AbstractProvider;
import net.datafaker.base.Address;
import net.datafaker.base.BaseFaker;
import net.datafaker.Faker;
import net.datafaker.base.Name;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withModifier;
import static org.reflections.ReflectionUtils.withParametersCount;
import static org.reflections.ReflectionUtils.withReturnType;

/**
 * The purpose of these tests is to ensure that the Locales have been properly configured
 * and that methods return values. The unit tests should ensure what the values returned
 * are correct. These tests just ensure that the methods can be invoked.
 */
class FakerIntegrationTest {

    /**
     * a collection of Locales -> Exceptions.
     * In the case of 'pt', city_prefix is '' by design. This test fails because it's testing that all string returning
     * methods return a non blank string. But pt city_prefix is blank ,but the test shouldn't fail. So we add put
     * exceptions like this into this collection.
     */
    private static final Map<Locale, SkippedMethods> exceptions = new HashMap<>();

    static {
        // 'it' has an empty suffix list so it never returns a value
        exceptions.put(new Locale("it"), SkippedMethods.of(Name.class, "suffix"));
        exceptions.put(new Locale("es", "mx"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("pt"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("uk"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix", "stateAbbr", "streetSuffix"));
        exceptions.put(new Locale("pt-BR"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("pt-br"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("Pt_br"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("pT_Br"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
        exceptions.put(new Locale("pt", "Br", "x2"), SkippedMethods.of(Address.class, "cityPrefix", "citySuffix"));
    }

    private static class SkippedMethods {
        private final Map<Class, Set<String>> class2methodNames = new HashMap<>();

        public static SkippedMethods of(Class clazz, String ... methodNames) {
            SkippedMethods sm = new SkippedMethods();
            sm.class2methodNames.putIfAbsent(clazz, new HashSet<>());
            sm.class2methodNames.get(clazz).addAll(Arrays.asList(methodNames));
            return sm;
        }
    }

    private Faker init(Locale locale, Random random) {
        if (locale != null && random != null) {
            return new Faker(locale, random);
        } else if (locale != null) {
            return new Faker(locale);
        } else if (random != null) {
            return new Faker(random);
        } else {
            return new Faker();
        }
    }

    @ParameterizedTest
    @MethodSource("dataParameters")
    void testAllFakerMethodsThatReturnStrings(Locale locale, Random random) throws Exception {
        final Faker faker = init(locale, random);
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker);
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.address());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.ancient());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.animal());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.app());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.appliance());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.aquaTeenHungerForce());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.artist());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.australia());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.aviation());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.babylon5());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.backToTheFuture());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.barcode());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.basketball());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.battlefield1());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.beer());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.bigBangTheory());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.bloodtype());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.bojackHorseman());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.book());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.bossaNova());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.breakingBad());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.buffy());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.business());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.camera());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.cat());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.chuckNorris());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.clashOfClans());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.chiquito());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.coin());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.color());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.commerce());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.community());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.company());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.computer());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.country());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.demographic());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.departed());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.dessert());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.device());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.dcComics());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.disease());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.dog());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.dragonBall());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.drivingLicense());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.dumbAndDumber());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.dune());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.educator());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.eldenRing());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.elderScrolls());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.electricalComponents());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.englandfootball());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.esports());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.fallout());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.famousLastWords());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.file());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.finalSpace());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.finance());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.food());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.formula1());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.friends());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.funnyName());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.gameOfThrones());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.garmentSize());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.gender());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.ghostbusters());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.gratefulDead());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.greekPhilosopher());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.hacker());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.harryPotter());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.hashing());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.hearthstone());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.heyArnold());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.hipster());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.hitchhikersGuideToTheGalaxy());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.hobbit());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.hololive());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.house());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.howIMetYourMother());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.idNumber());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.industrySegments());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.internet());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.job());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.kaamelott());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.leagueOfLegends());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.lebowski());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.lordOfTheRings());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.lorem());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.marketing());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.massEffect());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.matz());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.measurement());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.military());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.mountain());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.mountaineering());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.movie());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.music());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.name());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.nation());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.natoPhoneticAlphabet());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.nigeria());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.oscarMovie());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.overwatch());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.phoneNumber());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.photography());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.pokemon());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.princessBride());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.programmingLanguage());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.relationships());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.restaurant());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.rickAndMorty());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.robin());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.rockBand());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.ruPaulDragRace());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.seinfeld());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.shakespeare());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.slackEmoji());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.simpsons());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.space());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.starCraft());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.starTrek());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.stock());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.superhero());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.tea());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.team());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.theItCrowd());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.touhou());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.tron());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.twinPeaks());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.university());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.vehicle());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.verb());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.weather());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.witcher());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.yoda());
        testAllMethodsThatReturnStringsActuallyReturnStrings(faker.zelda());
    }

    private void testAllMethodsThatReturnStringsActuallyReturnStrings(Object object) throws Exception {
        final Locale locale;
        if (object instanceof BaseFaker) {
            locale = ((BaseFaker) object).getContext().getLocale();
        } else {
            locale = ((AbstractProvider) object).getFaker().getContext().getLocale();
        }
        @SuppressWarnings("unchecked")
        Set<Method> methodsThatReturnStrings = getAllMethods(object.getClass(),
            withModifier(Modifier.PUBLIC),
            withReturnType(String.class),
            withParametersCount(0));

        for (Method method : methodsThatReturnStrings) {
            if (isExcepted(object, method, locale)) {
                continue;
            }
            final Object returnValue = method.invoke(object);
            assertThat(returnValue).as("For method " + object.getClass() + "#" + method.getName() + "value is '" + returnValue + "'").isInstanceOf(String.class);
            final String returnValueAsString = (String) returnValue;
            assertThat(returnValueAsString).as("For method " + object.getClass() + "#" + method.getName()).isNotEmpty();
        }
    }

    private boolean isExcepted(Object object, Method method, Locale locale) {
        if (exceptions.containsKey(locale) && exceptions.get(locale).class2methodNames.containsKey(object.getClass())) {
            return exceptions.get(locale).class2methodNames.get(object.getClass()).contains(method.getName());
        }
        return false;
    }

    @ParameterizedTest
    @MethodSource("dataParameters")
    void testExceptionsNotCoveredInAboveTest(Locale locale, Random random) {
        final BaseFaker faker = init(locale, random);
        assertThat(faker.bothify("####???")).isNotNull();
        assertThat(faker.letterify("????")).isNotNull();
        assertThat(faker.numerify("####")).isNotNull();

        assertThat(faker.lorem().paragraph(1)).isNotNull();
        assertThat(faker.lorem().paragraphs(1)).isNotNull();

        assertThat(faker.lorem().sentence(1)).isNotNull();
        assertThat(faker.lorem().sentences(1)).isNotNull();

        assertThat(faker.address().streetAddress()).isNotNull();

        assertThat(faker.lorem().words()).isNotNull();
        assertThat(faker.lorem().words(1)).isNotNull();
    }

    private static Stream<Arguments> dataParameters() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(Locale.ENGLISH, new Random()));
        arguments.add(Arguments.of(new Locale("pt-BR"), null));
        arguments.add(Arguments.of(new Locale("pt-br"), null));
        arguments.add(Arguments.of(new Locale("Pt_br"), null));
        arguments.add(Arguments.of(new Locale("pt", "Br", "x2"), null));
        arguments.add(Arguments.of(null, new Random()));
        arguments.add(Arguments.of(null, null));

        String[] ymlFiles = new File("./src/main/resources").list();
        for (String ymlFileName : ymlFiles) {
            arguments.add(Arguments.of(new Locale(StringUtils.substringBefore(ymlFileName, ".")), null));
        }

        return arguments.stream();
    }
}
