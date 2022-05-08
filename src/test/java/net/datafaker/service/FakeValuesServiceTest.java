package net.datafaker.service;

import net.datafaker.AbstractFakerTest;
import net.datafaker.Faker;
import net.datafaker.Superhero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class FakeValuesServiceTest extends AbstractFakerTest {

    private static final Long MILLIS_IN_AN_HOUR = 1000 * 60 * 60L;
    private static final Long MILLIS_IN_A_DAY = MILLIS_IN_AN_HOUR * 24;

    @Spy
    private Faker mockedFaker;

    @Mock
    private RandomService randomService;

    private FakeValuesService fakeValuesService;

    @BeforeEach
    public void before() {
        super.before();
        MockitoAnnotations.openMocks(this);

        // always return the first element
        when(randomService.nextInt(anyInt())).thenReturn(0);

        fakeValuesService = Mockito.spy(new FakeValuesService(new Locale("test"), randomService));
    }

    @Test
    void fetchStringShouldReturnValue() {
        assertThat(fakeValuesService.fetchString("property.dummy")).isEqualTo("x");
    }

    @Test
    void fetchShouldReturnValue() {
        assertThat(fakeValuesService.fetch("property.dummy")).isEqualTo("x");
    }

    @Test
    void fetchObjectShouldReturnValue() {
        assertThat((Iterable<?>) fakeValuesService.fetchObject("property.dummy")).isEqualTo(Arrays.asList("x", "y", "z"));
    }

    @Test
    void safeFetchShouldReturnValueInList() {
        doReturn(0).when(randomService).nextInt(Mockito.anyInt());
        assertThat(fakeValuesService.safeFetch("property.dummy", null)).isEqualTo("x");
    }

    @Test
    void safeFetchShouldReturnSimpleList() {
        assertThat(fakeValuesService.safeFetch("property.simple", null)).isEqualTo("hello");
    }

    @Test
    void safeFetchShouldReturnEmptyStringWhenPropertyDoesntExist() {
        assertThat(fakeValuesService.safeFetch("property.dummy2", "")).isEmpty();
    }

    @Test
    void bothify2Args() {
        final DummyService dummy = mock(DummyService.class);

        Faker f = new Faker();

        String value = fakeValuesService.resolve("property.bothify_2", dummy, f);
        assertThat(value).matches("[A-Z]{2}\\d{2}");
    }

    @Test
    void regexifyDirective() {
        final DummyService dummy = mock(DummyService.class);

        String value = fakeValuesService.resolve("property.regexify1", dummy, mockedFaker);
        assertThat(value).isIn("55", "44", "45", "54");
        verify(mockedFaker).regexify("[45]{2}");
    }

    @Test
    void regexifySlashFormatDirective() {
        final DummyService dummy = mock(DummyService.class);

        String value = fakeValuesService.resolve("property.regexify_slash_format", dummy, mockedFaker);
        assertThat(value).isIn("55", "44", "45", "54");
        verify(mockedFaker).regexify("[45]{2}");
    }

    @Test
    void regexifyDirective2() {
        final DummyService dummy = mock(DummyService.class);

        String value = fakeValuesService.resolve("property.regexify_cell", dummy, mockedFaker);
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
        final String actual = fakeValuesService.resolve("property.simpleResolution", dummy, mockedFaker);

        // then
        assertThat(actual).isEqualTo("Yo!");
        verify(dummy).hello();
        verifyNoMoreInteractions(mockedFaker);
    }

    @Test
    void resolveKeyToPropertyWithAPropertyWithAnObject() {
        // given
        final Superhero person = mock(Superhero.class);
        final DummyService dummy = mock(DummyService.class);
        doReturn(person).when(mockedFaker).superhero();
        doReturn("Luke Cage").when(person).name();

        // when
        final String actual = fakeValuesService.resolve("property.advancedResolution", dummy, mockedFaker);

        // then
        assertThat(actual).isEqualTo("Luke Cage");
        verify(mockedFaker).superhero();
        verify(person).name();
    }

    @Test
    void resolveKeyToPropertyWithAList() {
        // property.resolutionWithList -> #{hello}
        // #{hello} -> DummyService.hello

        // given
        final DummyService dummy = mock(DummyService.class);
        doReturn(0).when(randomService).nextInt(Mockito.anyInt());
        doReturn("Yo!").when(dummy).hello();

        // when
        final String actual = fakeValuesService.resolve("property.resolutionWithList", dummy, mockedFaker);

        // then
        assertThat(actual).isEqualTo("Yo!");
        verify(dummy).hello();
    }

    @Test
    void resolveKeyWithMultiplePropertiesShouldJoinResults() {
        // given
        final Superhero person = mock(Superhero.class);
        final DummyService dummy = mock(DummyService.class);
        doReturn(person).when(mockedFaker).superhero();

        doReturn("Yo Superman!").when(dummy).hello();
        doReturn("up up and away").when(person).descriptor();

        // when
        String actual = fakeValuesService.resolve("property.multipleResolution", dummy, mockedFaker);

        // then
        assertThat(actual).isEqualTo("Yo Superman! up up and away");

        verify(mockedFaker).superhero();
        verify(person).descriptor();
        verify(dummy).hello();
    }

    @Test
    void testLocaleChain() {
        final List<Locale> chain = fakeValuesService.localeChain(Locale.SIMPLIFIED_CHINESE);

        assertThat(chain).contains(Locale.SIMPLIFIED_CHINESE, Locale.CHINESE, Locale.ENGLISH);
    }

    @Test
    void testLocaleChainEnglish() {
        final List<Locale> chain = fakeValuesService.localeChain(Locale.ENGLISH);

        assertThat(chain).contains(Locale.ENGLISH);
    }

    @Test
    void testLocaleChainLanguageOnly() {
        final List<Locale> chain = fakeValuesService.localeChain(Locale.CHINESE);

        assertThat(chain).contains(Locale.CHINESE, Locale.ENGLISH);
    }

    @Test
    void testLocalesChainGetter() {
        final List<Locale> chain = fakeValuesService.getLocalesChain();

        assertThat(chain).contains(new Locale("test"), Locale.ENGLISH);
    }

    @Test
    void testLocalesChainGetterRu() {
        final FakeValuesService FVS = new FakeValuesService(new Locale("ru"), randomService);
        final List<Locale> processedChain = FVS.localeChain(new Locale("ru"));
        final List<Locale> chain = FVS.getLocalesChain();

        assertThat(chain).isEqualTo(processedChain);
    }

    @Test
    void expressionWithInvalidFakerObject() {
        expressionShouldFailWith("#{ObjectNotOnFaker.methodName}",
            "Unable to resolve #{ObjectNotOnFaker.methodName} directive.");
    }

    @Test
    void expressionWithValidFakerObjectButInvalidMethod() {
        expressionShouldFailWith("#{Name.nonExistentMethod}",
            "Unable to resolve #{Name.nonExistentMethod} directive.");
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
        expressionShouldFailWith("#{Number.number_between 'x','y'}",
            "Unable to resolve #{Number.number_between 'x','y'} directive.");
    }

    @Test
    void futureDateExpression() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);

        Date now = new Date();
        Date nowPlus10Days = new Date(now.getTime() + MILLIS_IN_A_DAY * 10);

        Date date = dateFormat.parse(fakeValuesService.expression("#{date.future '10','TimeUnit.DAYS'}", faker));

        assertThat(date.getTime()).isGreaterThan(now.getTime());
        assertThat(date.getTime()).isLessThan(nowPlus10Days.getTime());
    }

    @Test
    void pastDateExpression() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);

        Date now = new Date();
        Date nowMinus5Hours = new Date(now.getTime() - MILLIS_IN_AN_HOUR * 5);

        Date date = dateFormat.parse(fakeValuesService.expression("#{date.past '5','TimeUnit.HOURS'}", faker));

        assertThat(date.getTime()).isGreaterThan(nowMinus5Hours.getTime());
        assertThat(date.getTime()).isLessThan(now.getTime());
    }

    @Test
    void expressionWithFourArguments() {

        assertThat(fakeValuesService.expression("#{Internet.password '5','8','true','true'}", faker)).matches("[\\w\\d!%#$@_^&*]{5,8}");
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/test.txt_null", "qwerty", "src"})
    void fileExpressionTestFailure(String filename) {
        assertThatThrownBy(() -> fakeValuesService.fileExpression(Paths.get(filename), faker))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void fileNoExpressionTest() {
        try {
            Path tmpPath = Files.createTempFile("tmp", "file");
            assertThat(String.join("", Files.readAllLines(tmpPath))).isEqualTo(fakeValuesService.fileExpression(tmpPath, faker));
        } catch (IOException e) {
            fail("Fail ", e);
        }
    }

    @Test
    void fileExpressionTest() {
        try {
            Path path = Paths.get("src/test/test.txt");
            assertThat(String.join(System.lineSeparator(), Files.readAllLines(path)))
                .isNotEqualTo(fakeValuesService.fileExpression(path, faker));
        } catch (IOException e) {
            fail("Fail ", e);
        }
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
        expressionShouldFailWith("#{x}", "Unable to resolve #{x} directive.");
    }

    private void expressionShouldFailWith(String expression, String errorMessage) {
        assertThatThrownBy(() -> fakeValuesService.expression(expression, faker))
            .isInstanceOf(RuntimeException.class)
            .hasMessage(errorMessage);
    }

    @Test
    void resolveUsingTheSameKeyTwice() {
        // #{hello} -> DummyService.hello

        // given
        final DummyService dummy = mock(DummyService.class);
        when(dummy.hello()).thenReturn("1").thenReturn("2");

        // when
        final String actual = fakeValuesService.resolve("property.sameResolution", dummy, mockedFaker);

        // then
        assertThat(actual).isEqualTo("1 2");
        verifyNoMoreInteractions(mockedFaker);
    }

    @Test
    void FakeValuesServiceWithNullLocaleTest() {
        RandomService r = new RandomService();
        assertThatThrownBy(() -> new FakeValuesService(null, new RandomService()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("locale is required");
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
