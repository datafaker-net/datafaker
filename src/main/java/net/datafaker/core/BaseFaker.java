package net.datafaker.core;

import net.datafaker.Bool;
import net.datafaker.FakeCollection;
import net.datafaker.Number;
import net.datafaker.fileformats.Json;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.RandomService;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * Provides utility methods for generating fake strings, such as names, phone
 * numbers, addresses. generate random strings with given patterns
 *
 * @author ren
 */
public class BaseFaker implements Faker {
    private final RandomService randomService;
    private final FakeValuesService fakeValuesService;
    private final Map<Class<?>, Object> providersMap = new IdentityHashMap<>();

    public BaseFaker() {
        this(Locale.ENGLISH);
    }

    public BaseFaker(Locale locale) {
        this(locale, new RandomService());
    }

    public BaseFaker(long seed) {
        this(Locale.ENGLISH, seed);
    }

    public BaseFaker(Locale locale, long seed) {
        this(locale, new RandomService(seed));
    }

    public BaseFaker(Locale locale, RandomService randomService) {
        this(new FakeValuesService(locale, randomService), randomService);
    }

    public BaseFaker(FakeValuesService fakeValuesService, RandomService randomService) {
        this.randomService = randomService;
        this.fakeValuesService = fakeValuesService;
    }

    /**
     * Constructs Faker instance with default argument.
     *
     * @return {@link BaseFaker#BaseFaker()}
     */
    public static Faker instance() {
        return new BaseFaker();
    }

    /**
     * Constructs Faker instance with provided {@link Locale}.
     *
     * @param locale - {@link Locale}
     * @return {@link BaseFaker#BaseFaker(Locale)}
     */
    public static Faker instance(Locale locale) {
        return new BaseFaker(locale);
    }

    /**
     * Constructs Faker instance with provided {@link long}.
     *
     * @param seed - {@link long}
     * @return {@link BaseFaker#BaseFaker(long)}
     */
    public static Faker instance(long seed) {
        return new BaseFaker(seed);
    }

    /**
     * Constructs Faker instance with provided {@link Locale} and {@link long}.
     *
     * @param locale - {@link Locale}
     * @param seed - {@link long}
     * @return {@link BaseFaker#BaseFaker(Locale, long)}
     */
    public static Faker instance(Locale locale, long seed) {
        return new BaseFaker(locale, seed);
    }

    /**
     * Returns the internal locale being used, or the ROOT locale if no locale has been set.
     * @return Returns locale being used
     */
    public Locale getLocale() {
        return fakeValuesService.getLocalesChain().isEmpty() || fakeValuesService.getLocalesChain().get(0) == null
            ? Locale.ROOT : fakeValuesService.getLocalesChain().get(0);
    }

    public <T> T doWith(Callable<T> callable, Locale locale) {
        final Locale current = fakeValuesService.getCurrentLocale();
        T result;
        try {
            fakeValuesService.setCurrentLocale(locale);
            result = callable.call();
            return result;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            fakeValuesService.setCurrentLocale(current);
        }

    }

    /**
     * Returns a string with the '#' characters in the parameter replaced with random digits between 0-9 inclusive.
     * <p>
     * For example, the string "ABC##EFG" could be replaced with a string like "ABC99EFG".
     *
     * @param numberString Template for string generation
     * @return Generated string
     */
    public String numerify(String numberString) {
        return fakeValuesService.numerify(numberString);
    }

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * <p>
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     *
     * @param letterString Template for string generation
     * @return Generated string.
     */
    public String letterify(String letterString) {
        return fakeValuesService.letterify(letterString);
    }

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * <p>
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     */
    public String letterify(String letterString, boolean isUpper) {
        return fakeValuesService.letterify(letterString, isUpper);
    }

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     */
    public String bothify(String string) {
        return fakeValuesService.bothify(string);
    }

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     */
    public String bothify(String string, boolean isUpper) {
        return fakeValuesService.bothify(string, isUpper);
    }

    /**
     * Generates a String that matches the given regular expression.
     */
    public String regexify(String regex) {
        return fakeValuesService.regexify(regex);
    }

    /**
     * Generates a String by example. The output string will have the same pattern as the input string.
     * <p>
     * For example:
     * "AAA" becomes "KRA"
     * "abc" becomes "uio"
     * "948" becomes "345"
     * "A19c" becomes "Z20d"
     *
     * @param example The input string
     * @return The output string based on the input pattern
     */
    public String examplify(String example) {
        return fakeValuesService.examplify(example);
    }

    /**
     * Returns a string with the char2replace characters in the parameter replaced with random option from available options.
     * <p>
     * For example, the string "ABC??EFG" could be replaced with a string like "ABCtestтестEFG"
     * if passed options are new String[]{"test", "тест"}.
     *
     * @param string       Template for string generation
     * @param char2replace Char to replace
     * @param options      Options to use while filling the template
     * @return Generated string
     */
    public String templatify(String string, char char2replace, String... options) {
        return fakeValuesService().templatify(string, char2replace, options);
    }

    /**
     * Returns a string with the characters in the keys of optionsMap parameter replaced with random option from values.
     *
     * <p>
     * For example, the string "ABC$$EFG" could be replaced with a string like "ABCtestтестEFG"
     * if passed for key '$' there is value new String[]{"test", "тест"} in optionsMap
     *
     * @param string     Template for string generation
     * @param optionsMap Map with replacement rules
     * @return Generated string
     */
    public String templatify(String string, Map<Character, String[]> optionsMap) {
        return fakeValuesService().templatify(string, optionsMap);
    }

    /**
     * Returns a string with generated csv based number of lines and column expressions.
     * This method will use comma as default delimiter, always prints header and double quote as default quote.
     *
     * <p>
     * For example, it could generate
     * "name_column","last_name_column"
     * "Sabrina","Kihn"
     *
     * for expression {@code faker.expression("#{csv '1','name_column','#{Name.first_name}','last_name_column','#{Name.last_name}'}");}
     *
     * @param limit             Number of lines
     * @param columnExpressions Even number of expressions.
     *                          The odd numbers args are used for columns names, and even for column values.
     * @return Generated string
     */
    public String csv(int limit, String ... columnExpressions) {
        return fakeValuesService().csv(limit, columnExpressions);
    }

    /**
     * Returns a string with generated csv based number of lines and column expressions.
     *
     * <p>
     * For example, it could generate
     * "Thad" ### "Crist"
     * "Kathryne" ### "Wuckert"
     * "Sybil" ### "Connelly"
     *
     * for expression {@code faker.expression("#{csv ' ### ','"','false','3','name_column','#{Name.first_name}','last_name_column','#{Name.last_name}'}");}
     *
     * @param separator         Delimiter to use
     * @param quote             Quote to use
     * @param withHeader        Print header or not
     * @param limit             Number of lines
     * @param columnExpressions Even number of expressions.
     *                          The odd numbers args are used for columns names, and even for column values.
     * @return Generated string
     */
    public String csv(String separator, char quote, boolean withHeader, int limit, String ... columnExpressions) {
        return fakeValuesService().csv(separator, quote, withHeader, limit, columnExpressions);
    }

    public Json json(String... fieldExpressions) {
        return fakeValuesService().json(fieldExpressions);
    }

    public Json jsona(String... fieldExpressions) {
        return fakeValuesService().jsona(fieldExpressions);
    }

    public RandomService random() {
        return this.randomService;
    }

    public FakeValuesService fakeValuesService() {
        return this.fakeValuesService;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProvider(Class<T> clazz, Supplier<T> valueSupplier) {
        T result = (T) providersMap.get(clazz);
        if (result == null) {
            providersMap.putIfAbsent(clazz, valueSupplier.get());
            result = (T) providersMap.get(clazz);
        }
        return result;
    }

    /**
     *
     * @return builder to build {@code FakeCollection}
     */
    public <T> FakeCollection.Builder<T> collection() {
        return new FakeCollection.Builder<T>().faker(this);
    }

    @SafeVarargs
    public final <T> FakeCollection.Builder<T> collection(Supplier<T>... suppliers) {
        return new FakeCollection.Builder<>(suppliers).faker(this);
    }

    public final <T> FakeCollection.Builder<T> collection(List<Supplier<T>> suppliers) {
        return new FakeCollection.Builder<>(suppliers).faker(this);
    }

    public String resolve(String key) {
        return this.fakeValuesService.resolve(key, this, this);
    }

    public String resolve(String key, Supplier<String> exceptionMessage) {
        return this.fakeValuesService.resolve(key, this, this, exceptionMessage);
    }

    /**
     * Allows the evaluation of native YML expressions to allow you to build your
     * own.
     * <p>
     * The following are valid expressions:
     * <ul>
     * <li>#{regexify '(a|b){2,3}'}</li>
     * <li>#{regexify '\\.\\*\\?\\+'}</li>
     * <li>#{bothify '????','false'}</li>
     * <li>#{Name.first_name} #{Name.first_name} #{Name.last_name}</li>
     * <li>#{number.number_between '1','10'}</li>
     * </ul>
     *
     * @param expression (see examples above)
     * @return the evaluated string expression
     * @throws RuntimeException if unable to evaluate the expression
     */
    public String expression(String expression) {
        return this.fakeValuesService.expression(expression, this);
    }

    /**
     * bool
     * @return Bool provider
     */
    public Bool bool() {
        return getProvider(Bool.class, () -> new Bool(this));
    }

    /**
     * number
     * @return Number provider
     */
    public Number number() {
        return getProvider(Number.class, () -> new Number(this));
    }

}
