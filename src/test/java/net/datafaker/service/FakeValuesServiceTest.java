package net.datafaker.service;

import net.datafaker.AbstractFakerTest;
import net.datafaker.internal.helper.SingletonLocale;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.BaseProviders;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FakeValuesServiceTest extends AbstractFakerTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss")
            .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true).toFormatter();

    @Spy
    private BaseFaker mockedFaker;

    @Mock
    private RandomService randomService;

    @Spy
    private FakeValuesService fakeValuesService;
    private FakerContext context;

    @BeforeEach
    final void before() {
        // always return the first element
        when(randomService.nextInt(anyInt())).thenReturn(0);
        context = new FakerContext(new Locale("test"), randomService);
        when(mockedFaker.getContext()).thenReturn(context);
        fakeValuesService.updateFakeValuesInterfaceMap(context.getLocaleChain());
    }

    @Test
    void fetchStringShouldReturnValue() {
        assertThat(fakeValuesService.fetchString("property.dummy", mockedFaker.getContext())).isEqualTo("x");
    }

    @Test
    void fetchShouldReturnValue() {
        assertThat(fakeValuesService.fetch("property.dummy", mockedFaker.getContext())).isEqualTo("x");
    }

    @Test
    void fetchObjectShouldReturnValue() {
        List<String> dummy = fakeValuesService.fetchObject("property.dummy", mockedFaker.getContext());
        assertThat(dummy).isEqualTo(List.of("x", "y", "z"));
    }

    @Test
    void safeFetchShouldReturnValueInList() {
        doReturn(0).when(randomService).nextInt(anyInt());
        assertThat(fakeValuesService.safeFetch("property.dummy", mockedFaker.getContext(), null)).isEqualTo("x");
    }

    @Test
    void safeFetchShouldReturnSimpleList() {
        assertThat(fakeValuesService.safeFetch("property.simple", mockedFaker.getContext(), null)).isEqualTo("hello");
    }

    @Test
    void safeFetchShouldReturnEmptyStringWhenPropertyDoesntExist() {
        assertThat(fakeValuesService.safeFetch("property.dummy2", mockedFaker.getContext(), "")).isEmpty();
    }

    @Test
    void bothify2Args() {
        final DummyService dummy = mock(DummyService.class);

        BaseFaker f = new BaseFaker();

        String value = fakeValuesService.resolve("property.bothify_2", dummy, f, context);
        assertThat(value).matches("[A-Z]{2}\\d{2}");
    }

    @Test
    void regexifyDirective() {
        final DummyService dummy = mock(DummyService.class);

        String value = fakeValuesService.resolve("property.regexify1", dummy, mockedFaker, mockedFaker.getContext());
        assertThat(value).isIn("55", "44", "45", "54");
        verify(mockedFaker).regexify("[45]{2}");
    }

    @Test
    void regexifySlashFormatDirective() {
        final DummyService dummy = mock(DummyService.class);

        String value = fakeValuesService.resolve("property.regexify_slash_format", dummy, mockedFaker, mockedFaker.getContext());
        assertThat(value).isIn("55", "44", "45", "54");
        verify(mockedFaker).regexify("[45]{2}");
    }

    @Test
    void regexifyDirective2() {
        final DummyService dummy = mock(DummyService.class);

        String value = fakeValuesService.resolve("property.regexify_cell", dummy, mockedFaker, mockedFaker.getContext());
        assertThat(value).isIn("479", "459");
        verify(mockedFaker).regexify("4[57]9");
    }

    @Test
    void resolveKeyToPropertyWithAPropertyWithoutAnObject() {
        // #{hello} -> DummyService.hello

        // given
        final DummyService dummy = mock(DummyService.class);
        doReturn("Yo!").when(dummy).hello();

        // when
        final String actual = fakeValuesService.resolve("property.simpleResolution", dummy, mockedFaker, mockedFaker.getContext());

        // then
        assertThat(actual).isEqualTo("Yo!");
        verify(dummy).hello();
    }

    @Test
    void resolveKeyToPropertyWithAList() {
        // property.resolutionWithList -> #{hello}
        // #{hello} -> DummyService.hello
        class Property extends AbstractProvider<BaseProviders> {
            private Property(BaseProviders faker) {
                super(faker);
                ClassLoader classLoader = getClass().getClassLoader();
                URL resource = classLoader.getResource("test.yml");
                faker.addUrl(new Locale("test"), resource);
            }

            public String hello() {
                return "Yo!";
            }

            public String hello2() {
                return "Yo2!";
            }

            public String resolutionWithList() {
                return resolve("property.resolutionWithList");
            }
        }
        class PropertyFaker extends BaseFaker {
            private PropertyFaker() {
                super(new Locale("test"));
            }

            public Property property() {
                return getProvider(Property.class, Property::new);
            }
        }
        var testFaker = new PropertyFaker();
        Property provider = testFaker.getProvider("Property");
        String actual = provider.resolutionWithList();
        assertThat(actual).startsWith("Yo");
    }

    @Test
    void testLocaleChain() {
        final List<SingletonLocale> chain = context.localeChain(Locale.SIMPLIFIED_CHINESE);

        assertThat(chain).map(SingletonLocale::getLocale).contains(Locale.SIMPLIFIED_CHINESE, Locale.CHINESE, Locale.ENGLISH);
    }

    @Test
    void testLocaleChainEnglish() {
        final List<SingletonLocale> chain = new FakerContext(Locale.ENGLISH, null).localeChain(Locale.ENGLISH);

        assertThat(chain).map(SingletonLocale::getLocale).contains(Locale.ENGLISH);
    }

    @Test
    void testLocaleChainLanguageOnly() {
        final List<SingletonLocale> chain = new FakerContext(Locale.CHINESE, null).localeChain(Locale.CHINESE);

        assertThat(chain).map(SingletonLocale::getLocale).contains(Locale.CHINESE, Locale.ENGLISH);
    }

    @Test
    void testLocalesChainGetter() {
        final List<SingletonLocale> chain = context.getLocaleChain();

        assertThat(chain).map(SingletonLocale::getLocale).contains(new Locale("test"), Locale.ENGLISH);
    }

    @Test
    void testLocalesChainGetterRu() {
        final FakerContext FVS = new FakerContext(new Locale("ru"), randomService);
        final List<SingletonLocale> processedChain = FVS.localeChain(new Locale("ru"));
        final List<SingletonLocale> chain = FVS.getLocaleChain();

        assertThat(chain).isEqualTo(processedChain);
    }

    @Test
    void testFakerContextSetLocale() {
        final FakerContext fakerContext = new FakerContext(new Locale("en"), randomService);
        fakerContext.setLocale(new Locale("uk"));
        assertThat(fakerContext.getLocale()).isEqualTo(new Locale("uk"));
    }

    @Test
    void testFakerContextSetRandomService() {
        final FakerContext fakerContext = new FakerContext(Locale.US, randomService);
        fakerContext.setRandomService(new RandomService());
        assertThat(fakerContext.getRandomService()).usingRecursiveComparison().isEqualTo(new RandomService());
    }

    @ParameterizedTest
    @MethodSource("fakerContexts")
    void checkFakerContextEquality(FakerContext fc1, FakerContext fc2, boolean equals) {
        if (equals) {
            assertThat(fc1).usingRecursiveComparison().isEqualTo(fc2);
        } else {
            assertThat(fc1).usingRecursiveComparison().isNotEqualTo(fc2);
        }
    }

    static Stream<Arguments> fakerContexts() {
        return Stream.of(
            Arguments.of(new FakerContext(new Locale("en"), new RandomService()), new FakerContext(new Locale("uk"), new RandomService()), false),
            Arguments.of(new FakerContext(new Locale("en"), new RandomService()), null, false),
            Arguments.of(new FakerContext(Locale.US, new RandomService()), new FakerContext(Locale.US, new RandomService()), true)
        );
    }

    @Test
    void expressionWithInvalidFakerObject() {
        expressionShouldFailWithContaining("#{ObjectNotOnFaker.methodName}",
            "Unable to resolve #{ObjectNotOnFaker.methodName} directive");
    }

    @Test
    void expressionWithValidFakerObjectButInvalidMethod() {
        expressionShouldFailWithContaining("#{Name.nonExistentMethod}",
            "Unable to resolve #{Name.nonExistentMethod} directive");
    }

    /**
     * Two things are important here:
     * 1) the message in the exception should be USEFUL
     * 2) a {@link RuntimeException} should be thrown.
     * <p>
     * if the message changes, it's ok to update the test provided
     * the two conditions above are still true.
     */
    @Test
    void expressionWithValidFakerObjectValidMethodInvalidArgs() {
        expressionShouldFailWithContaining("#{Number.number_between 'x','y'}",
            "Unable to resolve #{Number.number_between 'x','y'} directive");
    }

    @RepeatedTest(100)
    void futureDateExpression() {
        LocalDateTime now = LocalDateTime.now(OffsetDateTime.now(ZoneId.systemDefault()).getOffset());
        LocalDateTime nowPlus10Days = now.plusDays(10);
        String expression = fakeValuesService.expression("#{date.future '10','TimeUnit.DAYS'}", faker, context);
        LocalDateTime date = LocalDateTime.parse(expression, DATE_TIME_FORMATTER);
        assertThat(date).isStrictlyBetween(now, nowPlus10Days);
    }

    @RepeatedTest(100)
    void pastDateExpression() {
        LocalDateTime now = LocalDateTime.now(OffsetDateTime.now(ZoneId.systemDefault()).getOffset());
        LocalDateTime nowMinus5Hours = now.minusHours(5);
        String expression = fakeValuesService.expression("#{date.past '4','TimeUnit.HOURS'}", faker, context);
        LocalDateTime date = LocalDateTime.parse(expression, DATE_TIME_FORMATTER);
        assertThat(date).isStrictlyBetween(nowMinus5Hours, now);
    }

    @Test
    void expressionWithSingleEnumArg() {
        // https://github.com/datafaker-net/datafaker/issues/1274
        String masterCard = fakeValuesService.expression("#{finance.creditCard 'CreditCardType.MASTERCARD'}", faker, context);
        assertThat(LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(masterCard.replace("-", ""))).isTrue();
    }

    @Test
    void expressionWithFourArguments() {
        assertThat(fakeValuesService.expression("#{Internet.password '5','8','true','true'}", faker, context))
            .matches("[\\w\\d!%#$@_^&*]{5,8}");
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/test.txt_null", "qwerty", "src"})
    void fileExpressionTestFailure(String filename) {
        assertThatThrownBy(() -> fakeValuesService.fileExpression(Paths.get(filename), faker, faker.getContext()))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void fileNoExpressionTest() throws IOException {
        Path tmpPath = Files.createTempFile("tmp", "file");
        assertThat(String.join("", Files.readAllLines(tmpPath)))
            .isEqualTo(fakeValuesService.fileExpression(tmpPath, faker, faker.getContext()));
    }

    @Test
    void fileExpressionTest() throws IOException {
        Path path = Paths.get("src/test/test.txt");
        assertThat(String.join(System.lineSeparator(), Files.readAllLines(path)))
            .isNotEqualTo(fakeValuesService.fileExpression(path, faker, context));
    }

    /**
     * Two things are important here:
     * 1) the message in the exception should be USEFUL
     * 2) a {@link RuntimeException} should be thrown.
     * <p>
     * if the message changes, it's ok to update the test provided
     * the two conditions above are still true.
     */
    @Test
    void expressionCompletelyUnresolvable() {
        expressionShouldFailWithContaining("#{x}", "Unable to resolve #{x} directive");
    }

    private void expressionShouldFailWithContaining(String expression, String errorMessagePattern) {
        assertThatThrownBy(() -> fakeValuesService.expression(expression, faker, context))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining(errorMessagePattern);
    }

    @Test
    void resolveUsingTheSameKeyTwice() {
        // #{hello} -> DummyService.hello

        // given
        final DummyService dummy = mock(DummyService.class);
        when(dummy.hello()).thenReturn("1").thenReturn("2");

        // when
        final String actual = fakeValuesService.resolve("property.sameResolution", dummy, mockedFaker, mockedFaker.getContext());

        // then
        assertThat(actual).isEqualTo("1 2");
    }

    public static class DummyService {
        public String firstName() {
            return "John";
        }

        public String lastName() {
            return "Smith";
        }

        public String hello() {
            return "Hello";
        }
    }
}
