package net.datafaker.providers.base;

import net.datafaker.annotations.FakeResolver;
import net.datafaker.sequence.FakeCollection;
import net.datafaker.sequence.FakeSequence;
import net.datafaker.sequence.FakeStream;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;
import net.datafaker.transformations.Schema;

import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Provides utility methods for generating fake strings, such as names, phone
 * numbers, addresses. generate random strings with given patterns
 *
 * @author ren
 */
public class BaseFaker implements BaseProviders {
    private final FakerContext context;
    private final FakeValuesService fakeValuesService;
    private static final Map<Class<?>, Map<FakerContext, AbstractProvider<?>>> PROVIDERS_MAP = new IdentityHashMap<>();
    private static final Map<String, Map<FakerContext, AbstractProvider<?>>> CLASSES = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Map<String, Method>> METHODS = new IdentityHashMap<>();

    public BaseFaker() {
        this(Locale.ENGLISH);
    }

    public BaseFaker(Locale locale) {
        this(locale, (Random) null);
    }

    public BaseFaker(Random random) {
        this(Locale.ENGLISH, random);
    }

    public BaseFaker(Locale locale, Random random) {
        this(locale, new RandomService(random));
    }

    public BaseFaker(Locale locale, RandomService randomService) {
        this(new FakeValuesService(), new FakerContext(locale, randomService));
    }

    public BaseFaker(FakeValuesService fakeValuesService, FakerContext context) {
        this.fakeValuesService = fakeValuesService;
        this.context = context;
        fakeValuesService.updateFakeValuesInterfaceMap(context.getLocaleChain());
    }

    public FakerContext getContext() {
        return context;
    }

    public <T> T doWith(Callable<T> callable, Locale locale) {
        final Locale current = context.getLocale();
        T result;
        try {
            context.setCurrentLocale(locale);
            fakeValuesService.updateFakeValuesInterfaceMap(context.getLocaleChain());
            result = callable.call();
            return result;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            context.setCurrentLocale(current);
            fakeValuesService.updateFakeValuesInterfaceMap(context.getLocaleChain());
        }
    }

    public <T> T doWith(Callable<T> callable, long seed) {
        final RandomService current = context.getRandomService();
        T result;
        try {
            context.setRandomService(new RandomService(new Random(seed)));
            result = callable.call();
            return result;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            context.setRandomService(current);
        }
    }

    public <T> T doWith(Callable<T> callable, Locale locale, long seed) {
        final Locale currentLocale = context.getLocale();
        final RandomService currentRandomService = context.getRandomService();
        T result;
        try {
            context.setRandomService(new RandomService(new Random(seed)));
            context.setCurrentLocale(locale);
            result = callable.call();
            return result;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            context.setRandomService(currentRandomService);
            context.setCurrentLocale(currentLocale);
        }
    }


    /**
     * Returns a string with the '#' characters in the parameter replaced with random digits between 0-9 inclusive or
     * random digits in the range from 1-9 when Ø (not zero) is used.
     * <p>
     * For example, the string "ABC##EFG" could be replaced with a string like "ABC99EFG" and the
     * string "Ø##" with a value like "149".
     *
     * @param numberString Template for string generation
     * @return Generated string
     */
    public String numerify(String numberString) {
        return fakeValuesService.numerify(numberString, context);
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
        return fakeValuesService.letterify(letterString, context);
    }

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * <p>
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     */
    public String letterify(String letterString, boolean isUpper) {
        return fakeValuesService.letterify(letterString, context, isUpper);
    }

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     */
    public String bothify(String string) {
        return fakeValuesService.bothify(string, context);
    }

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     */
    public String bothify(String string, boolean isUpper) {
        return fakeValuesService.bothify(string, context, isUpper);
    }

    /**
     * Generates a String that matches the given regular expression.
     */
    public String regexify(String regex) {
        return fakeValuesService.regexify(regex, context);
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
        return fakeValuesService.examplify(example, context);
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
        return fakeValuesService().templatify(string, char2replace, context, options);
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
        return fakeValuesService().templatify(string, optionsMap, context);
    }

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
    public String csv(int limit, String... columnExpressions) {
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
    public String csv(String separator, char quote, boolean withHeader, int limit, String... columnExpressions) {
        return fakeValuesService().csv(separator, quote, withHeader, limit, columnExpressions);
    }

    public String json(String... fieldExpressions) {
        return fakeValuesService().json(fieldExpressions);
    }

    public String jsona(String... fieldExpressions) {
        return fakeValuesService().jsona(fieldExpressions);
    }

    public RandomService random() {
        return this.context.getRandomService();
    }

    public FakeValuesService fakeValuesService() {
        return this.fakeValuesService;
    }

    /**
     * Allows to add paths to files with custom data. Data should be in YAML format.
     *
     * @param locale the locale for which a path is going to be added.
     * @param path   path to a file with YAML structure
     * @throws IllegalArgumentException in case of invalid path
     */
    public void addPath(Locale locale, Path path) {
        fakeValuesService().addPath(locale, path);
    }

    /**
     * Allows to add urls of files with custom data. Data should be in YAML format.
     *
     * @param locale the locale for which an url is going to be added.
     * @param url   url of a file with YAML structure
     * @throws IllegalArgumentException in case of invalid url
     */

    public void addUrl(Locale locale, URL url) {
        fakeValuesService().addUrl(locale, url);
    }

    public static <T> T populate(Class<T> clazz) {
        var fakeFactory = FakeResolver.of(clazz);
        return fakeFactory.generate(null);
    }

    public static <T> T populate(Class<T> clazz, Schema<Object, ?> schema) {
        var fakeFactory = FakeResolver.of(clazz);
        return fakeFactory.generate(schema);
    }

    @SuppressWarnings("unchecked")
    public static <PR extends ProviderRegistration, AP extends AbstractProvider<PR>> AP getProvider(Class<AP> clazz, Function<PR, AP> valueSupplier, PR faker) {
        Map<FakerContext, AbstractProvider<?>> map = PROVIDERS_MAP.get(clazz);
        if (map == null) {
            synchronized (BaseFaker.class) {
                map = PROVIDERS_MAP.get(clazz);
                if (map == null) {
                    PROVIDERS_MAP.put(clazz, new ConcurrentHashMap<>());
                    map = PROVIDERS_MAP.get(clazz);
                }
            }
        }
        final AP result = (AP) map.get(faker.getContext());
        if (result == null) {
            final AP newMapping = valueSupplier.apply(faker);
            CLASSES.put(clazz.getSimpleName(), new ConcurrentHashMap<>());

            METHODS.putIfAbsent(newMapping.getClass(), new ConcurrentHashMap<>());
            for (Method method: clazz.getMethods()) {
                if (method.getParameterCount() > 0) continue;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < method.getName().length(); i++) {
                    char ch = method.getName().charAt(i);
                    if (i > 0 && Character.isUpperCase(ch)) {
                        sb.append('_').append(Character.toLowerCase(ch));
                    } else {
                        sb.append(ch);
                    }
                }
                Map<String, Method> methodMap = METHODS.get(newMapping.getClass());
                if (methodMap == null) {
                    synchronized (BaseFaker.class) {
                        methodMap = METHODS.get(newMapping.getClass());
                        if (methodMap == null) {
                            METHODS.put(newMapping.getClass(), new HashMap<>());
                            methodMap = METHODS.get(newMapping.getClass());
                        }
                    }
                }
                methodMap.put(sb.toString(), method);
            }
            map.putIfAbsent(faker.getContext(), newMapping);
            CLASSES.get(clazz.getSimpleName()).put(faker.getContext(), newMapping);
            return newMapping;
        }
        return result;
    }

    /**
     * @return builder to build {@code FakeCollection}
     */
    public <T> FakeSequence.Builder<T> collection() {
        return new FakeCollection.Builder<T>().faker(this);
    }

    @SafeVarargs
    public final <T> FakeSequence.Builder<T> collection(Supplier<T>... suppliers) {
        return new FakeCollection.Builder<>(suppliers).faker(this);
    }

    public final <T> FakeSequence.Builder<T> collection(List<Supplier<T>> suppliers) {
        return new FakeCollection.Builder<>(suppliers).faker(this);
    }

    /**
     * @return builder to build {@code FakeStream}
     */
    public <T> FakeSequence.Builder<T> stream() {
        return new FakeStream.Builder<T>().faker(this);
    }

    @SafeVarargs
    public final <T> FakeSequence.Builder<T> stream(Supplier<T>... suppliers) {
        return new FakeStream.Builder<>(suppliers).faker(this);
    }

    public final <T> FakeSequence.Builder<T> stream(List<Supplier<T>> suppliers) {
        return new FakeStream.Builder<>(suppliers).faker(this);
    }

    public String resolve(String key) {
        return this.fakeValuesService.resolve(key, this, this, context);
    }

    public String resolve(String key, Supplier<String> message) {
        return this.fakeValuesService.resolve(key, this, this, message, context);
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
        return this.fakeValuesService.expression(expression, this, getContext());
    }

    @Override
    public final <B extends ProviderRegistration> B getFaker() {
        return (B) this;
    }

    public static AbstractProvider<?> getProvider(String className, FakerContext context) {
        final Map<FakerContext, AbstractProvider<?>> map = CLASSES.get(className);
        if (map == null) return null;
        return map.get(context);
    }

    public static Method getMethod(AbstractProvider<?> ap, String methodName) {
        Map<String, Method> map;
        if (ap == null || (map = METHODS.get(ap.getClass())) == null) return null;
        return map.get(methodName);
    }
}
