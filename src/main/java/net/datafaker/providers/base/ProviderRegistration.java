package net.datafaker.providers.base;

import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;

import java.net.URL;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ProviderRegistration {
    <B extends ProviderRegistration> B getFaker();

    FakerContext getContext();

    default <PR extends ProviderRegistration, AP extends AbstractProvider<PR>> AP getProvider(
        Class<AP> clazz, Function<PR, AP> valueSupplier) {
        return BaseFaker.getProvider(clazz, valueSupplier, getFaker());
    }

    String resolve(String key);

    String resolve(String key, Supplier<String> message);

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
     * <p>
     * for expression {@code faker.expression("#{csv '1','name_column','#{Name.first_name}','last_name_column','#{Name.last_name}'}");}
     *
     * @param limit             Number of lines
     * @param columnExpressions Even number of expressions.
     *                          The odd numbers args are used for columns names, and even for column values.
     * @return Generated string
     */
    String csv(int limit, String... columnExpressions);

    /**
     * Returns a string with generated csv based number of lines and column expressions.
     *
     * <p>
     * For example, it could generate
     * "Thad" ### "Crist"
     * "Kathryne" ### "Wuckert"
     * "Sybil" ### "Connelly"
     * <p>
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
    String csv(String separator, char quote, boolean withHeader, int limit, String... columnExpressions);

    String json(String... fieldExpressions);

    String jsona(String... fieldExpressions);

    RandomService random();

    String expression(String expression);

    FakeValuesService fakeValuesService();

    default Options options() {
        return getProvider(Options.class, Options::new);
    }

    void addPath(Locale locale, Path path);

    void addUrl(Locale locale, URL url);
}
