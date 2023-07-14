package net.datafaker;

import net.datafaker.annotations.Deterministic;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseFaker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.reflections.scanners.Scanners.SubTypes;

class FakerTest extends AbstractFakerTest {

    @Test
    void examplifyUppercaseLetters() {
        assertThat(faker.examplify("ABC")).matches("[A-Z]{3}");
    }

    @Test
    void examplifyLowercaseLetters() {
        assertThat(faker.examplify("abc")).matches("[a-z]{3}");
    }

    @Test
    void examplifyNumbers() {
        assertThat(faker.examplify("489321")).matches("[0-9]{6}");
    }

    @Test
    void examplifyMixed() {
        assertThat(faker.examplify("abc123ABC1zzz")).matches("[a-z]{3}[0-9]{3}[A-Z]{3}[0-9][a-z]{3}");
    }

    @Test
    void examplifyWithSpacesAndSpecialCharacters() {
        assertThat(faker.examplify("The number 4!")).matches("[A-Z][a-z]{2} [a-z]{6} [0-9]!");
    }

    @Test
    void bothifyShouldGenerateLettersAndNumbers() {
        assertThat(faker.bothify("????##@gmail.com")).matches("\\w{4}\\d{2}@gmail.com");
    }

    @Test
    void letterifyShouldGenerateLetters() {
        assertThat(faker.bothify("????")).matches("\\w{4}");
    }

    @Test
    void letterifyShouldGenerateUpperCaseLetters() {
        assertThat(faker.bothify("????", true)).matches("[A-Z]{4}");
    }

    @Test
    void letterifyShouldLeaveNonSpecialCharactersAlone() {
        assertThat(faker.bothify("ABC????DEF")).matches("ABC\\w{4}DEF");
    }

    @Test
    void numerifyShouldGenerateNumbers() {
        assertThat(faker.numerify("####")).matches("\\d{4}");
    }

    @RepeatedTest(25)
    void numerifyShouldGenerateNumbersNotStartingWithZero() {
        assertThat(faker.numerify("Ø###")).matches("[1-9]\\d{3}");
    }

    @RepeatedTest(25)
    void numerifyShouldGenerateNonZeroNumbers() {
        assertThat(faker.numerify("ØØ")).matches("[1-9]{2}");
    }

    @Test
    void numerifyShouldLeaveNonSpecialCharactersAlone() {
        assertThat(faker.numerify("####123")).matches("\\d{4}123");
    }

    @Test
    void templatify() {
        assertThat(faker.templatify("12??34", '?', "тест", "test", "测试测试")).hasSize(12);
        assertThat(faker.templatify("12??34",
            Map.of('1', new String[]{"тест", "test", "测试测试"}))).hasSize(9);
        assertThat(faker.templatify("12??34",
            Map.of('1', new String[]{""}))).hasSize(5);
    }

    @Test
    void regexifyShouldGenerateNumbers() {
        assertThat(faker.regexify("\\d")).matches("\\d");
    }

    @Test
    void regexifyShouldGenerateLetters() {
        assertThat(faker.regexify("\\w")).matches("\\w");
    }

    @Test
    void regexifyShouldGenerateAlternations() {
        assertThat(faker.regexify("(a|b)")).matches("([ab])");
    }

    @Test
    void regexifyShouldGenerateBasicCharacterClasses() {
        assertThat(faker.regexify("(aeiou)")).matches("(aeiou)");
    }

    @Test
    void regexifyShouldGenerateCharacterClassRanges() {
        assertThat(faker.regexify("[a-z]")).matches("[a-z]");
    }

    @Test
    void regexifyShouldGenerateMultipleCharacterClassRanges() {
        assertThat(faker.regexify("[a-z1-9]")).matches("[a-z1-9]");
    }

    @Test
    void regexifyShouldGenerateSingleCharacterQuantifiers() {
        assertThat(faker.regexify("a*b+c?")).matches("a*b+c?");
    }

    @Test
    void regexifyShouldGenerateBracketsQuantifiers() {
        assertThat(faker.regexify("a{2}")).matches("a{2}");
    }

    @Test
    void regexifyShouldGenerateMinMaxQuantifiers() {
        assertThat(faker.regexify("a{2,3}")).matches("a{2,3}");
    }

    @Test
    void regexifyShouldGenerateBracketsQuantifiersOnBasicCharacterClasses() {
        assertThat(faker.regexify("[aeiou]{2,3}")).matches("[aeiou]{2,3}");
    }

    @Test
    void regexifyShouldGenerateBracketsQuantifiersOnCharacterClassRanges() {
        assertThat(faker.regexify("[a-z]{2,3}")).matches("[a-z]{2,3}");
    }

    @Test
    void regexifyShouldGenerateBracketsQuantifiersOnAlternations() {
        assertThat(faker.regexify("(a|b){2,3}")).matches("([ab]){2,3}");
    }

    @Test
    void regexifyShouldGenerateEscapedCharacters() {
        assertThat(faker.regexify("\\.\\*\\?\\+")).matches("\\.\\*\\?\\+");
    }

    @Test
    void badExpressionTooManyArgs() {
        assertThatThrownBy(() -> faker.expression("#{regexify 'a','a'}"))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void badExpressionTooFewArgs() {
        assertThatThrownBy(() -> faker.expression("#{regexify}"))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void badExpressionCouldntCoerce() {
        assertThatThrownBy(() -> faker.expression("#{number.number_between 'x','10'}"))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void expression() {
        assertThat(faker.expression("#{options.option 'a','b','c','d'}")).matches("([abcd])");
        assertThat(faker.expression("#{options.option ''''}")).matches("(')");
        assertThat(faker.expression("#{options.option '12','345','89','54321'}")).matches("(12|345|89|54321)");
        assertThat(faker.expression("#{regexify '(a|b){2,3}'}")).matches("([ab]){2,3}");
        assertThat(faker.expression("#{regexify '\\.\\*\\?\\+'}")).matches("\\.\\*\\?\\+");
        assertThat(faker.expression("#{bothify '????','true'}")).matches("[A-Z]{4}");
        assertThat(faker.expression("#{bothify '????','false'}")).matches("[a-z]{4}");
        assertThat(faker.expression("#{letterify '????','true'}")).matches("[A-Z]{4}");
        assertThat(faker.expression("#{templatify '????','?','1','2','q','r'}")).matches("([12qr]){4}");
        assertThat(faker.expression("#{Name.first_name} #{Name.first_name} #{Name.last_name}")).matches("[a-zA-Z']+ [a-zA-Z']+ [a-zA-Z']+");
        assertThat(faker.expression("#{number.number_between '1','10'}")).matches("[1-9]");
        assertThat(faker.expression("#{color.name}")).matches("[a-z\\s]+");
        assertThat(faker.expression("#{date.past '15','SECONDS','dd/MM/yyyy hh:mm:ss'}"))
            .matches("[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}");
        assertThat(faker.expression("#{date.birthday 'yy DDD hh:mm:ss'}"))
            .matches("[0-9]{2} [0-9]{3} [0-9]{2}:[0-9]{2}:[0-9]{2}");
    }

    @Test
    void jsonExpressionTest() {
        assertThat(faker.expression("#{json 'person','#{json ''first_name'',''#{Name.first_name}'',''last_name'',''#{Name.last_name}''}','address','#{json ''country'',''#{Address.country}'',''city'',''#{Address.city}''}'}"))
            .contains("\"address\": {\"country\":");

        assertThat(
            faker.expression("#{jsona '-1','person'," +
                "'#{json ''first_name'',''#{Name.first_name}'',''last_name'',''#{Name.last_name}''}'," +
                " '2','addesses'," +
                "'#{json ''address''," +
                "''#{json ''''country'''',''''#{Address.country}'''',''''city'''',''''#{Address.city}''''}''}'}"))
            .contains("\"addesses\": [{\"address\": {\"country\": ");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 3, 10, 20, 100})
    void testLimitForCsvExpression(int limit) {
        String csvFullExpression = faker.expression("#{csv ';','\"','false','" + limit + "','first_name','#{Name.first_name}','last_name','#{Name.last_name}'}");
        String csvShortExpression = faker.expression("#{csv '" + limit + "','first_name','#{Name.first_name}','last_name','#{Name.last_name}'}");

        int numberOfLinesFull = 0;
        int numberOfLinesShort = 0;
        for (int i = 0; i < csvFullExpression.length(); i++) {
            if (csvFullExpression.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLinesFull++;
            }
        }

        for (int i = 0; i < csvShortExpression.length(); i++) {
            if (csvShortExpression.regionMatches(i, System.lineSeparator(), 0, System.lineSeparator().length())) {
                numberOfLinesShort++;
            }
        }

        assertThat(numberOfLinesFull).isEqualTo(limit);
        assertThat(numberOfLinesShort).isEqualTo(limit + 1); // + header
    }

    @RepeatedTest(100)
    void numberBetweenRepeated() {
        assertThat(faker.expression("#{number.number_between '1','10'}")).matches("[1-9]");
    }

    @Test
    void regexifyShouldGenerateSameValueForFakerWithSameSeed() {
        long seed = 1L;
        String regex = "\\d";

        String firstResult = new Faker(new Random(seed)).regexify(regex);
        String secondResult = new Faker(new Random(seed)).regexify(regex);

        assertThat(secondResult).isEqualTo(firstResult);
    }

    @Test
    void resolveShouldReturnValueThatExists() {
        assertThat(faker.resolve("address.city_prefix")).isNotEmpty();
    }

    @Test
    void resolveShouldThrowExceptionWhenPropertyDoesntExist() {
        assertThatThrownBy(() -> faker.resolve("address.nothing"))
            .isInstanceOf(RuntimeException.class);
    }

    /*
    Test case for issue https://github.com/datafaker-net/datafaker/issues/87
     */
    @ParameterizedTest
    @ValueSource(strings = {"#{regexify '[a-z]{5}[A-Z]{5}'}", "#{Address.city}"})
    void datafaker87(String expression) {
        int n = 10;
        int counter = 0;
        for (int i = 0; i < n; i++) {
            String expression1 = faker.expression(expression);
            String expression2 = faker.expression(expression);
            if (expression1.equals(expression2)) {
                counter++;
            }
        }

        assertThat(counter).isLessThan(n);
    }

    @Test
    void fakerInstanceCanBeAcquiredViaUtilityMethods() {
        assertThat(new Faker()).isInstanceOf(BaseFaker.class);
        assertThat(new Faker(Locale.CANADA)).isInstanceOf(BaseFaker.class);
        assertThat(new Faker(new Random(1))).isInstanceOf(BaseFaker.class);
        assertThat(new Faker(Locale.CHINA, new Random(2))).isInstanceOf(BaseFaker.class);
    }

    @Test
    void differentLocalesTest() {
        BaseFaker localFaker = new Faker();
        Callable<String> stringCallable = () -> localFaker.name().firstName();
        localFaker.doWith(stringCallable, new Locale("ru_RU"));
        localFaker.doWith(stringCallable, Locale.GERMAN);
        localFaker.doWith(stringCallable, Locale.SIMPLIFIED_CHINESE);
        for (int i = 0; i < 10; i++) {
            assertThat(localFaker.doWith(stringCallable, new Locale("ru_RU"))).matches("[а-яА-ЯЁё ]+");
        }
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    void issue883Test() throws InterruptedException {
        for (int i = 0; i < 10_000_000; i++) {
            Faker f = new Faker();
            String s = f.ancient().god();
            if (i % 1_000_000 == 0) {
                Thread.sleep(10);
            }
        }
    }

    @Test
    void doWithLocaleExceptionTest() {
        BaseFaker localFaker = new BaseFaker();
        assertThatThrownBy(
            () -> localFaker.doWith(() -> {
                throw new Exception();
            }, Locale.JAPAN))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void differentSeeds() {
        BaseFaker localFaker = new Faker();
        Callable<String> stringCallable = () -> localFaker.name().firstName();

        assertThat(localFaker.doWith(stringCallable, 123))
            .isEqualTo(localFaker.doWith(stringCallable, 123));
        assertThat(localFaker.doWith(stringCallable, 987))
            .isNotEqualTo(localFaker.doWith(stringCallable, 123))
            .isEqualTo(localFaker.doWith(stringCallable, 987));

        assertThatThrownBy(
            () -> localFaker.doWith(() -> {
                throw new Exception();
            }, 123))
            .isInstanceOf(RuntimeException.class);
        assertThat(localFaker.doWith(stringCallable, Locale.CANADA, 123))
            .isEqualTo(localFaker.doWith(stringCallable, Locale.CANADA, 123));
        assertThat(localFaker.doWith(stringCallable, Locale.CANADA, 987))
            .isNotEqualTo(localFaker.doWith(stringCallable, Locale.CANADA, 123))
            .isEqualTo(localFaker.doWith(stringCallable, Locale.CANADA, 987));
        assertThatThrownBy(
            () -> localFaker.doWith(() -> {
                throw new Exception();
            }, Locale.ENGLISH, 123))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void shouldNotApplyCachingToMethodsWithParameters() {
        // Test for issue: https://github.com/datafaker-net/datafaker/issues/716.
        // No exception should be thrown

        // Warm up start
        String flight1 = faker.expression("#{Aviation.flight}");
        assertThat(flight1).matches("[A-z0-9]{2}\\d{1,4}");
        // Warm up end

        String flight2 = faker.expression("#{Aviation.flight 'ICAO'}");
        assertThat(flight2).matches("[A-z]{3}\\d{1,4}");
    }

    @Test
    void testDeterministicAndNonDeterministicProvidersReturnValues() {
        final int numberOfTestsPerMethod = 100;
        final Reflections reflections = new Reflections("net.datafaker.providers");
        final Set<Class<?>> classes = reflections.get(SubTypes.of(AbstractProvider.class).asClass());
        for (var clazz : classes) {
            final Collection<Method> methods = Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()) && m.getParameterCount() == 0).collect(Collectors.toSet());
            if (methods.isEmpty()) continue;
            Constructor<AbstractProvider<?>> constructor = null;
            final AbstractProvider<?> ap;
            try {
                final Set<Constructor<AbstractProvider<?>>> constructorsWith1Arg =
                    Arrays.stream(clazz.getDeclaredConstructors())
                        .filter(c -> c.getParameterCount() == 1).map(c -> (Constructor<AbstractProvider<?>>) c)
                        .collect(Collectors.toSet());
                for (var c : constructorsWith1Arg) {
                    final Class<?>[] types = c.getParameterTypes();
                    if (types[0].isAssignableFrom(Faker.class)) {
                        constructor = c;
                        break;
                    }
                }
                assertThat(constructor).isNotNull();
                constructor.setAccessible(true);
                ap = constructor.newInstance(faker);
            } catch (InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            for (Method m : methods) {
                final var set = new HashSet<>();
                try {
                    int currentSize = 0;
                    for (int i = 0; i < numberOfTestsPerMethod && currentSize <= 1; i++) {
                        set.add(m.invoke(ap));
                        currentSize = set.size();
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                if (m.isAnnotationPresent(Deterministic.class)) {
                    assertThat(set)
                        .as("Class: " + ap.getClass().getName()
                            + ", method: " + m.getName() + " should have the same return value")
                        .hasSize(1);
                } else {
                    assertThat(set)
                        .as("Class: " + ap.getClass().getName()
                            + ", method: " + m.getName() + " should generate different return values")
                        .hasSizeGreaterThan(1);
                }
            }
        }
    }
}
