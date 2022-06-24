package net.datafaker.core;

import net.datafaker.Bool;
import net.datafaker.FakeCollection;
import net.datafaker.Number;
import net.datafaker.fileformats.Json;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.RandomService;

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
public interface Faker {

    /**
     * Returns the internal locale being used, or the ROOT locale if no locale has been set.
     * @return Returns locale being used
     */
    Locale getLocale();

    /**
     * TODO: Complete javadoc
     * 
     * @param <T>
     * @param callable
     * @param locale
     * @return
     */
    <T> T doWith(Callable<T> callable, Locale locale);

    /**
     * Returns a string with the '#' characters in the parameter replaced with random digits between 0-9 inclusive.
     * <p>
     * For example, the string "ABC##EFG" could be replaced with a string like "ABC99EFG".
     *
     * @param numberString Template for string generation
     * @return Generated string
     */
    String numerify(String numberString);

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * <p>
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     *
     * @param letterString Template for string generation
     * @return Generated string.
     */
    String letterify(String letterString);

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * <p>
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     */
    String letterify(String letterString, boolean isUpper);

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     */
    String bothify(String string);

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     */
    String bothify(String string, boolean isUpper);

    /**
     * Generates a String that matches the given regular expression.
     */
    String regexify(String regex);

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
    String examplify(String example);

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
    String templatify(String string, char char2replace, String... options);

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
    String templatify(String string, Map<Character, String[]> optionsMap);

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
    String csv(int limit, String ... columnExpressions);

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
    String csv(String separator, char quote, boolean withHeader, int limit, String ... columnExpressions);

    Json json(String... fieldExpressions);

    Json jsona(String... fieldExpressions);

    RandomService random();

    FakeValuesService fakeValuesService();

    /**
     * 
     * @param <T>
     * @param clazz
     * @param valueSupplier
     * @return
     */
    <T> T getProvider(Class<T> clazz, Supplier<T> valueSupplier);

    /**
     * 
     * @param <T>
     * @return builder to build {@code FakeCollection}
     */
    <T> FakeCollection.Builder<T> collection();

    /**
     * 
     * @param <T>
     * @param suppliers - vargs of suppliers
     * @return builder to build {@code FakeCollection}
     */
    <T> FakeCollection.Builder<T> collection(Supplier<T>... suppliers);

    /**
     * 
     * @param suppliers - Collection of suppliers
     * @return builder to build {@code FakeCollection}
     */
    <T> FakeCollection.Builder<T> collection(List<Supplier<T>> suppliers);

    /**
     * resolve
     * @param key
     * @return
     */
    String resolve(String key);

    /**
     * 
     * @param key
     * @param exceptionMessage
     * @return
     */
    String resolve(String key, Supplier<String> exceptionMessage);

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
    String expression(String expression);

    /**
     * bool
     * @return Bool provider
     */
    public Bool bool();

    /**
     * number
     * @return Number provider
     */
    public Number number();

}
