package net.datafaker.fakers;

import net.datafaker.AbstractProvider;
import net.datafaker.FakeCollection;
import net.datafaker.fileformats.Json;
import net.datafaker.service.RandomService;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public interface BaseFaker {
    Locale getLocale();
    <T> T doWith(Callable<T> callable, Locale locale);
    <T> T doWith(Callable<T> callable, long seed);
    <T> T doWith(Callable<T> callable, Locale locale, long seed);
    String numerify(String numberString);
    String letterify(String letterString);
    String letterify(String letterString, boolean isUpper);
    String bothify(String string);
    String bothify(String string, boolean isUpper);
    String regexify(String regex);
    String examplify(String example);
    String templatify(String string, char char2replace, String... options);
    String templatify(String string, Map<Character, String[]> optionsMap);
    String csv(int limit, String ... columnExpressions);
    String csv(String separator, char quote, boolean withHeader, int limit, String ... columnExpressions);
    Json json(String... fieldExpressions);
    Json jsona(String... fieldExpressions);
    RandomService random();
    void addPath(Locale locale, Path path);
    <T extends AbstractProvider> T getProvider(Class<T> clazz, Supplier<T> valueSupplier);
    /**
     *
     * @return builder to build {@code FakeCollection}
     */
    default <T> FakeCollection.Builder<T> collection() {
        return new FakeCollection.Builder<T>().faker(this);
    }

    default <T> FakeCollection.Builder<T> collection(Supplier<T>... suppliers) {
        return new FakeCollection.Builder<>(suppliers).faker(this);
    }

    default <T> FakeCollection.Builder<T> collection(List<Supplier<T>> suppliers) {
        return new FakeCollection.Builder<>(suppliers).faker(this);
    }

    String resolve(String key);

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

}
