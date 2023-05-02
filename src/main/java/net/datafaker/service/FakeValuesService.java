package net.datafaker.service;

import com.mifmif.common.regex.Generex;
import net.datafaker.internal.helper.COWMap;
import net.datafaker.internal.helper.SingletonLocale;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.Address;
import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Name;
import net.datafaker.providers.base.ProviderRegistration;
import net.datafaker.transformations.CsvTransformer;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JsonTransformer;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.SimpleField;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static net.datafaker.transformations.Field.field;

public class FakeValuesService {
    private static final char[] DIGITS = "0123456789".toCharArray();
    private static final String[] EMPTY_ARRAY = new String[0];
    private static final Logger LOG = Logger.getLogger("faker");
    public static final Supplier<Map<String, Object>> MAP_STRING_OBJECT_SUPPLIER = () -> new COWMap<>(() -> new WeakHashMap<>());
    public static final Supplier<Map<String, String>> MAP_STRING_STRING_SUPPLIER = () -> new COWMap<>(() -> new WeakHashMap<>());

    private final Map<SingletonLocale, FakeValuesInterface> fakeValuesInterfaceMap = new COWMap<>(IdentityHashMap::new);
    public static final SingletonLocale DEFAULT_LOCALE = SingletonLocale.get(Locale.ENGLISH);

    private static final Map<Class<?>, Map<String, Collection<Method>>> CLASS_2_METHODS_CACHE = new COWMap<>(IdentityHashMap::new);
    private static final Map<Class<?>, Constructor<?>> CLASS_2_CONSTRUCTOR_CACHE = new COWMap<>(IdentityHashMap::new);

    private static final JsonTransformer<Object> JSON_TRANSFORMER = JsonTransformer.builder().build();

    private final Map<String, Generex> expression2generex = new COWMap<>(WeakHashMap::new);
    private final COWMap<SingletonLocale, Map<String, String>> key2Expression = new COWMap<>(IdentityHashMap::new);
    private static final Map<String, String[]> ARGS_2_SPLITTED_ARGS = new COWMap<>(WeakHashMap::new);

    private static final Map<String, String[]> KEY_2_SPLITTED_KEY = new COWMap<>(WeakHashMap::new);

    private final COWMap<SingletonLocale, Map<String, Object>> key2fetchedObject = new COWMap<>(IdentityHashMap::new);

    private static final Map<String, String> NAME_2_YAML = new COWMap<>(WeakHashMap::new);

    private static final Map<String, String> REMOVED_UNDERSCORE = new COWMap<>(WeakHashMap::new);
    private static final Map<Class<?>, Map<String, Map<String[], MethodAndCoercedArgs>>> MAP_OF_METHOD_AND_COERCED_ARGS = new COWMap<>(IdentityHashMap::new);

    private static final Map<String, String[]> EXPRESSION_2_SPLITTED = new COWMap<>(WeakHashMap::new);

    private static final Map<RegExpContext, Supplier<?>> REGEXP2SUPPLIER_MAP = new COWMap<>(HashMap::new);

    public FakeValuesService() {
    }

    public void updateFakeValuesInterfaceMap(List<SingletonLocale> locales) {
        for (final SingletonLocale l : locales) {
            fakeValuesInterfaceMap.computeIfAbsent(l, this::getCachedFakeValue);
        }
    }

    private FakeValuesInterface getCachedFakeValue(SingletonLocale locale) {
        if (DEFAULT_LOCALE == locale) {
            return FakeValuesGrouping.getEnglishFakeValueGrouping();
        }

        return FakeValues.of(FakeValuesContext.of(locale.getLocale()));
    }

    /**
     * Allows adding paths to files with custom data. Data should be in YAML format.
     *
     * @param locale the locale for which a path is going to be added.
     * @param path   path to a file with YAML structure
     * @throws IllegalArgumentException in case of invalid path
     */
    public void addPath(Locale locale, Path path) {
        Objects.requireNonNull(locale);
        if (path == null || Files.notExists(path) || Files.isDirectory(path) || !Files.isReadable(path)) {
            throw new IllegalArgumentException("Path should be an existing readable file");
        }
        try {
            addUrl(locale, path.toUri().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Allows adding urls of files with custom data. Data should be in YAML format.
     *
     * @param locale  the locale for which an url is going to be added.
     * @param url     url of a file with YAML structure
     * @throws IllegalArgumentException in case of invalid url
     */
    public void addUrl(Locale locale, URL url) {
        Objects.requireNonNull(locale);
        if (url == null) {
            throw new IllegalArgumentException("url should be an existing readable file");
        }
        final FakeValues fakeValues = FakeValues.of(FakeValuesContext.of(locale, url));
        final SingletonLocale sLocale = SingletonLocale.get(locale);
        fakeValuesInterfaceMap.merge(sLocale, fakeValues,
            (prevValue, newValue) -> {
                FakeValuesGrouping fvg = new FakeValuesGrouping();
                fvg.add(prevValue);
                fvg.add(newValue);
                return fvg;
            });
    }

    /**
     * Fetch a random value from an array item specified by the key
     */
    public Object fetch(String key, FakerContext context) {
        List<?> valuesArray = null;
        final Object o = fetchObject(key, context);
        if (o instanceof List) {
            valuesArray = (List<?>) o;
            final int size = valuesArray.size();
            if (size == 0) {
                return null;
            }
            if (size == 1) {
                return valuesArray.get(0);
            }
        }
        return valuesArray == null
            ? null : valuesArray.get(context.getRandomService().nextInt(valuesArray.size()));
    }

    /**
     * Same as {@link #fetch(String, FakerContext)} except this casts the result into a String.
     */
    public String fetchString(String key, FakerContext context) {
        return (String) fetch(key, context);
    }

    /**
     * Safely fetches a key.
     * <p>
     * If the value is null, it will return an empty string.
     * <p>
     * If it is a list, it will assume it is a list of strings and select a random value from it.
     * <p>
     * If the retrieved value is an slash encoded regular expression such as {@code /[a-b]/} then
     * the regex will be converted to a regexify expression and returned (ex. {@code #regexify '[a-b]'})
     * <p>
     * Otherwise it will just return the value as a string.
     *
     * @param key           the key to fetch from the YML structure.
     * @param defaultIfNull the value to return if the fetched value is null
     * @return see above
     */
    @SuppressWarnings("unchecked")
    public String safeFetch(String key, FakerContext context, String defaultIfNull) {
        Object o = fetchObject(key, context);
        String str;
        if (o == null) return defaultIfNull;
        if (o instanceof List) {
            final List<String> values = (List<String>) o;
            final int size = values.size();
            if (size == 0) {
                return defaultIfNull;
            }
            if (size == 1) {
                return values.get(0);
            }
            return values.get(context.getRandomService().nextInt(size));
        } else if (isSlashDelimitedRegex(str = o.toString())) {
            return "#{regexify '%s'}".formatted(trimRegexSlashes(str));
        } else {
            return (String) o;
        }
    }

    /**
     * Return the object selected by the key from yaml file.
     *
     * @param key key contains path to an object. Path segment is separated by
     *            dot. E.g. name.first_name
     */
    public Object fetchObject(String key, FakerContext context) {
        Object result = null;
        final List<SingletonLocale> localeChain = context.getLocaleChain();
        for (SingletonLocale sLocale : localeChain) {
            // exclude default locale from cache checks
            if (sLocale == DEFAULT_LOCALE && localeChain.size() > 1) {
                continue;
            }
            Map<String, Object> stringObjectMap = key2fetchedObject.get(sLocale);
            if (stringObjectMap != null && (result = stringObjectMap.get(key)) != null) {
                return result;
            }
        }

        String[] path = split(key);
        SingletonLocale local2Add = null;
        for (SingletonLocale sLocale : localeChain) {
            Object currentValue = fakeValuesInterfaceMap.get(sLocale);
            for (int p = 0; currentValue != null && p < path.length; p++) {
                String currentPath = path[p];
                if (currentValue instanceof Map) {
                    currentValue = ((Map<?, ?>) currentValue).get(currentPath);
                } else {
                    currentValue = ((FakeValuesInterface) currentValue).get(currentPath);
                }
            }
            result = currentValue;
            if (result != null) {
                local2Add = sLocale;
                break;
            }
        }
        if (local2Add != null) {
            key2fetchedObject.updateNestedValue(local2Add, MAP_STRING_OBJECT_SUPPLIER, key, result);
        }
        return result;
    }

    private String[] split(String string) {
        String[] result = KEY_2_SPLITTED_KEY.get(string);
        if (result != null) {
            return result;
        }
        int size = 0;
        final char splitChar = '.';
        final int length = string.length();
        for (int i = 0; i < length; i++) {
            if (string.charAt(i) == splitChar) {
                size++;
            }
        }
        result = new String[size + 1];
        final char[] chars = string.toCharArray();
        int start = 0;
        int j = 0;
        for (int i = 0; i < length; i++) {
            if (string.charAt(i) == splitChar) {
                if (i - start > 0) {
                    result[j++] = String.valueOf(chars, start, i - start);
                }
                start = i + 1;
            }
        }
        result[j] = String.valueOf(chars, start, chars.length - start);
        KEY_2_SPLITTED_KEY.putIfAbsent(string, result);
        return result;
    }

    /**
     * Returns a string with the '#' characters in the parameter replaced with random digits between 0-9 inclusive or
     * random digits in the range from 1-9 when Ø (not zero) is used.
     * <p>
     * For example, the string "ABC##EFG" could be replaced with a string like "ABC99EFG".
     */
    public String numerify(String numberString, FakerContext context) {
        return bothify(numberString, context, false, true, false);
    }

    /**
     * Applies both a {@link #numerify(String, FakerContext)} and a {@link #letterify(String, FakerContext)}
     * over the incoming string.
     */
    public String bothify(String string, FakerContext context) {
        return bothify(string, context, false);
    }

    /**
     * Applies both a {@link #numerify(String, FakerContext)} and a {@link #letterify(String, FakerContext, boolean)}
     * over the incoming string.
     */
    public String bothify(String input, FakerContext context, boolean isUpper) {
        return bothify(input, context, isUpper, true, true);
    }

    private String bothify(String input, FakerContext context, boolean isUpper, boolean numerify, boolean letterify) {
        final int baseChar = isUpper ? 65 : 97;
        final char[] res = input.toCharArray();
        for (int i = 0; i < res.length; i++) {
            switch (res[i]) {
                case '#' -> {
                    if (numerify) {
                        res[i] = DIGITS[context.getRandomService().nextInt(10)];
                    }
                }
                case 'Ø' -> {
                    if (numerify) {
                        res[i] = DIGITS[context.getRandomService().nextInt(1, 9)];
                    }
                }
                case '?' -> {
                    if (letterify) {
                        res[i] = (char) (baseChar + context.getRandomService().nextInt(26)); // a-z
                    }
                }
                default -> {
                }
            }
        }

        return String.valueOf(res);
    }

    /**
     * Generates a String that matches the given regular expression.
     */
    public String regexify(String regex, FakerContext context) {
        Generex generex = expression2generex.get(regex);
        if (generex == null) {
            generex = new Generex(regex);
            generex.setSeed(context.getRandomService().nextLong());
            expression2generex.putIfAbsent(regex, generex);
        }
        return generex.random();
    }

    /**
     * Generates a String by example. The output string will have the same pattern as the input string.
     */
    public String examplify(String example, FakerContext context) {
        if (example == null) {
            return null;
        }
        final char[] chars = example.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                chars[i] = letterify("?", context, Character.isUpperCase(chars[i])).charAt(0);
            } else if (Character.isDigit(chars[i])) {
                chars[i] = DIGITS[context.getRandomService().nextInt(10)];
            }
        }

        return String.valueOf(chars);

    }

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * <p>
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     */
    public String letterify(String letterString, FakerContext context) {
        return this.letterify(letterString, context, false);
    }

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * <p>
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     */
    public String letterify(String letterString, FakerContext context, boolean isUpper) {
        return bothify(letterString, context, isUpper, false, true);
    }

    /**
     * Returns a string with the char2replace characters in the parameter replaced with random alphabetic
     * characters from options
     */
    public String templatify(String letterString, char char2replace, FakerContext context, String... options) {
        return templatify(letterString, Map.of(char2replace, options), context);
    }

    /**
     * Returns a string with the optionsMap.getKeys() characters in the parameter replaced with random alphabetic
     * characters from corresponding optionsMap.values()
     */
    public String templatify(String letterString, Map<Character, String[]> optionsMap, FakerContext context) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < letterString.length(); i++) {
            if (optionsMap.containsKey(letterString.charAt(i))) {
                final String[] options = optionsMap.get(letterString.charAt(i));
                Objects.requireNonNull(options, "Array with available options should be non null");
                sb.append(options[context.getRandomService().nextInt(options.length)]);
            } else {
                sb.append(letterString.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * Resolves a key to a method on an object or throws an exception.
     * <p>
     * #{hello} with result in a method call to current.hello();
     * <p>
     * #{Person.hello_someone} will result in a method call to person.helloSomeone();
     */
    public String resolve(String key, Object current, BaseFaker root, FakerContext context) {
        return resolve(key, current, root, () -> key + " resulted in null expression", context);
    }

    public String resolve(String key, AbstractProvider<?> provider, FakerContext context) {
        return resolve(key, provider, provider.getFaker(), () -> key + " resulted in null expression", context);
    }

    /**
     * Resolves a key to a method on an object or throws an exception with specified message.
     * <p>
     * #{hello} with result in a method call to current.hello();
     * <p>
     * #{Person.hello_someone} will result in a method call to person.helloSomeone();
     */
    public String resolve(String key, Object current, ProviderRegistration root, Supplier<String> exceptionMessage, FakerContext context) {
        String expression = root == null ? key2Expression.get(context.getSingletonLocale()).get(key) : null;
        if (expression == null) {
            expression = safeFetch(key, context, null);
            if (root == null) {
                key2Expression.updateNestedValue(context.getSingletonLocale(),
                    MAP_STRING_STRING_SUPPLIER, key, expression);
            }
        }

        if (expression == null) {
            throw new RuntimeException(exceptionMessage.get());
        }

        return resolveExpression(expression, current, root, context);
    }

    /**
     * Resolves an expression using the current faker.
     */
    public String expression(String expression, BaseFaker faker, FakerContext context) {
        return resolveExpression(expression, null, faker, context);
    }

    /**
     * Resolves an expression in file using the current faker.
     */
    public String fileExpression(Path path, BaseFaker faker, FakerContext context) {
        try {
            return Files.readAllLines(path)
                .stream().map(t -> expression(t, faker, context))
                .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates csv based on input column expressions and number of lines.
     * This method uses default separator, quote and always prints header.
     */
    public String csv(int limit, String... columnExpressions) {
        return csv(CsvTransformer.DEFAULT_SEPARATOR, CsvTransformer.DEFAULT_QUOTE, true, limit, columnExpressions);
    }

    /**
     * Generates csv based on input.
     */
    public String csv(String delimiter, char quote, boolean withHeader, int limit, String... columnExpressions) {
        if (columnExpressions.length % 2 != 0) {
            throw new IllegalArgumentException("Total number of column names and column values should be even");
        }
        Field<String, String>[] fields = new Field[columnExpressions.length / 2];
        for (int i = 0; i < columnExpressions.length; i += 2) {
            final int index = i;
            fields[i / 2] = Field.field(columnExpressions[index], () -> columnExpressions[index + 1]);
        }
        Schema<String, String> schema = Schema.of(fields);
        return CsvTransformer.<String>builder().separator(delimiter).quote(quote).header(withHeader)
            .build().generate(schema, limit + 1);
    }

    /**
     * Generates json based on input.
     */
    public String json(String... fieldExpressions) {
        if (fieldExpressions.length % 2 != 0) {
            throw new IllegalArgumentException("Total number of field names and field values should be even");
        }

        List<SimpleField<Object, ?>> fields = new ArrayList<>();
        for (int i = 0; i < fieldExpressions.length; i += 2) {
            final int index = i;
            fields.add(field(fieldExpressions[index], () -> fieldExpressions[index + 1]));
        }
        Schema<Object, ?> schema = Schema.of(fields.toArray(new SimpleField[0]));
        return JSON_TRANSFORMER.generate(schema, 1);
    }

    /**
     * Generates json based on input.
     */
    public String jsona(String... fieldExpressions) {
        if (fieldExpressions.length % 3 != 0) {
            throw new IllegalArgumentException("Total number of field names and field values should be dividable by 3");
        }

        List<SimpleField<Object, ?>> fields = new ArrayList<>();
        for (int i = 0; i < fieldExpressions.length; i += 3) {
            final int index = i;
            if (fieldExpressions[i] != null && Integer.parseInt(fieldExpressions[index]) > 0) {
                Object[] objects = new Object[Integer.parseInt(fieldExpressions[index])];
                Arrays.fill(objects, fieldExpressions[index + 2]);
                fields.add(field(fieldExpressions[index + 1], () -> objects));
            } else {
                fields.add(field(fieldExpressions[index + 1], () -> fieldExpressions[index + 2]));
            }
        }
        Schema<Object, ?> schema = Schema.of(fields.toArray(new SimpleField[0]));
        return JSON_TRANSFORMER.generate(schema, 1);
    }

    /**
     * processes a expression in the style #{X.y} using the current objects as the 'current' location
     * within the yml file (or the {@link BaseFaker} object hierarchy as it were).
     * <p>
     * #{Address.streetName} would get resolved to {@link BaseFaker#address()}'s {@link Address#streetName()}
     * #{address.street} would get resolved to the YAML like locale: faker: address: street:
     * Combinations are supported as well: "#{x} #{y}"
     * <p>
     * Recursive templates are supported.  if "#{x}" resolves to "#{Address.streetName}" then "#{x}" resolves to
     * {@link BaseFaker#address()}'s {@link Address#streetName()}.
     */
    protected String resolveExpression(String expression, Object current, ProviderRegistration root, FakerContext context) {
        int cnt = 0;
        final int expressionLength = expression.length();
        for (int i = 0; i < expressionLength; i++) {
            if (expression.charAt(i) == '}') {
                cnt++;
            }
        }
        if (cnt == 0) {
            return expression;
        }
        final String[] expressions = splitExpressions(expression, cnt, expressionLength);
        final StringBuilder result = new StringBuilder(expressions.length * expressionLength);
        for (int i = 0; i < expressions.length; i++) {
            // odd are expressions, even are not expressions, just strings
            final String expr = expressions[i];
            if (i % 2 == 0) {
                if (!expr.isEmpty()) {
                    result.append(expr);
                }
                continue;
            }
            final RegExpContext regExpContext = RegExpContext.of(expr, current, root, context);
            final Supplier<?> val = REGEXP2SUPPLIER_MAP.get(regExpContext);
            final Object resolved;
            if (val != null) {
                resolved = val.get();
            } else {
                int j = 0;
                final int length = expr.length();
                while (j < length && !Character.isWhitespace(expr.charAt(j))) j++;
                String directive = expr.substring(0, j);
                while (j < length && Character.isWhitespace(expr.charAt(j))) j++;
                final String arguments = j == length ? "" : expr.substring(j);
                final String[] args = splitArguments(arguments);
                resolved = resExp(directive, args, current, root, context, regExpContext);
            }
            if (resolved == null) {
                throw new RuntimeException("Unable to resolve #{" + expr + "} directive for FakerContext " + context + ".");
            }
            result.append(resolveExpression(Objects.toString(resolved), current, root, context));
        }
        return result.toString();
    }

    private String[] splitArguments(String arguments) {
        final int length;
        if (arguments == null || (length = arguments.length()) == 0) {
            return EMPTY_ARRAY;
        }
        String[] res = ARGS_2_SPLITTED_ARGS.get(arguments);
        if (res != null) {
            return res;
        }
        List<String> result = new ArrayList<>();
        int start = 0;
        boolean argsStarted = false;
        for (int i = 0; i < length; i++) {
            if (argsStarted) {
                int cnt = 0;
                while (i < length && arguments.charAt(i) == '\'') {
                    cnt++;
                    i++;
                }
                if (cnt % 2 == 1) {
                    result.add(arguments.substring(start, i - 1).replaceAll("''", "'"));
                    argsStarted = false;
                }
            } else if (arguments.charAt(i) == '\'') {
                argsStarted = true;
                start = i + 1;
            }
        }
        final String[] resultArray = result.toArray(EMPTY_ARRAY);

        ARGS_2_SPLITTED_ARGS.putIfAbsent(arguments, resultArray);
        return resultArray;
    }

    private String[] splitExpressions(String expression, int cnt, int length) {
        String[] result = EXPRESSION_2_SPLITTED.get(expression);
        if (result != null) {
            return result;
        }
        List<String> list = new ArrayList<>(2 * cnt + 1);
        boolean isExpression = false;
        int start = 0;
        int quoteCnt = 0;
        for (int i = 0; i < length; i++) {
            if (isExpression) {
                if (expression.charAt(i) == '}' && quoteCnt % 2 == 0) {
                    list.add(expression.substring(start, i));
                    start = i + 1;
                    isExpression = false;
                } else if (expression.charAt(i) == '\'') {
                    quoteCnt++;
                }
            } else if (i < length - 2 && expression.charAt(i) == '#' && expression.charAt(i + 1) == '{') {
                list.add(expression.substring(start, i));
                isExpression = true;
                start = i + 2;
                i++;
            }
        }
        if (start < length) {
            list.add(expression.substring(start));
        }
        result = list.toArray(EMPTY_ARRAY);
        EXPRESSION_2_SPLITTED.putIfAbsent(expression, result);
        return result;
    }

    private Object resExp(String directive, String[] args, Object current, ProviderRegistration root, FakerContext context, RegExpContext regExpContext) {
        Object res = resolveExpression(directive, args, current, root, context);
        if (res instanceof CharSequence) {
            if (((CharSequence) res).isEmpty()) {
                REGEXP2SUPPLIER_MAP.put(regExpContext, () -> "");
            }
            return res;
        }
        if (res instanceof List) {
            Iterator it = ((List) res).iterator();
            while (it.hasNext()) {
                Object supplier = it.next();
                Object value;
                if (supplier instanceof Supplier<?>) {
                    value = ((Supplier<?>) supplier).get();
                    if (value == null) {
                        it.remove();
                    } else {
                        REGEXP2SUPPLIER_MAP.put(regExpContext, (Supplier<?>) supplier);
                        return value;
                    }
                }
            }
            return null;
        }
        return res;
    }

    /**
     * Search Order
     * <ul>
     *  <li>Search for methods on the current object</li>
     *  <li>local keys in Yaml File</li>
     *  <li>Search for methods on faker child objects</li>
     *   <li>Search for keys in yaml file by transforming object reference to yaml reference</li>
     * </ul>
     *
     * @return null if unable to resolve
     */
    private Object resolveExpression(String directive, String[] args, Object current, ProviderRegistration root, FakerContext context) {
        if (directive.isEmpty()) {
            return directive;
        }
        final int dotIndex = getDotIndex(directive);

        List<Supplier<Object>> res = new ArrayList<>();
        if (args.length == 0) {
            // resolve method references on CURRENT object like #{number_between '1','10'} on Number or
            // #{ssn_valid} on IdNumber
            if (dotIndex == -1) {
                if (current instanceof AbstractProvider) {
                    final Method method = BaseFaker.getMethod((AbstractProvider<?>) current, directive);
                    if (method != null) {
                        res.add(() -> {
                            try {
                                return method.invoke(current);
                            } catch (Exception e) {
                                throw new RuntimeException(e + " " + Arrays.toString(args));
                            }
                        });
                        return res;
                    }
                }
                res.add(resolveFromMethodOn(current, directive, args));
            }
            if (dotIndex > 0) {
                final AbstractProvider<?> ap = BaseFaker.getProvider(directive.substring(0, dotIndex), context);
                final Method method = BaseFaker.getMethod(ap, directive.substring(dotIndex + 1));
                if (method != null) {
                    res.add(() -> {
                        try {
                            return method.invoke(ap);
                        } catch (Exception e) {
                            throw new RuntimeException(e + " " + Arrays.toString(args));
                        }
                    });
                    return res;
                }
            }
        }
        final String simpleDirective = (dotIndex >= 0 || current == null)
            ? directive
            : classNameToYamlName(current) + "." + directive;
        // simple fetch of a value from the yaml file. the directive may have been mutated
        // such that if the current yml object is car: and directive is #{wheel} then
        // car.wheel will be looked up in the YAML file.
        res.add(() -> safeFetch(simpleDirective, context, null));

        // resolve method references on faker object like #{regexify '[a-z]'}
        if (dotIndex == -1 && root != null && (current == null || root.getClass() != current.getClass())) {
            res.add(resolveFromMethodOn(root, directive, args));
        }

        // Resolve Faker Object method references like #{ClassName.method_name}
        if (dotIndex >= 0) {
            res.add(resolveFakerObjectAndMethod(root, directive, dotIndex, args));
        }

        // last ditch effort.  Due to Ruby's dynamic nature, something like 'Address.street_title' will resolve
        // because 'street_title' is a dynamic method on the Address object.  We can't do this in Java so we go
        // thru the normal resolution above, but if we will can't resolve it, we once again do a 'safeFetch' as we
        // did first but FIRST we change the Object reference Class.method_name with a yml style internal reference ->
        // class.method_name (lowercase)
        if (dotIndex >= 0) {
            final String key = javaNameToYamlName(simpleDirective);
            res.add(() -> safeFetch(key, context, null));
        }

        return res;
    }


    /**
     * @param expression input expression
     * @return true if s is non null and is a slash delimited regex (ex. {@code /[ab]/})
     */
    private boolean isSlashDelimitedRegex(String expression) {
        return expression != null && expression.startsWith("/") && expression.endsWith("/");
    }

    /**
     * Given a {@code slashDelimitedRegex} such as {@code /[ab]/}, removes the slashes and returns only {@code [ab]}
     *
     * @param slashDelimitedRegex a non null slash delimited regex (ex. {@code /[ab]/})
     * @return the regex without the slashes (ex. {@code [ab]})
     */
    private String trimRegexSlashes(String slashDelimitedRegex) {
        return slashDelimitedRegex.substring(1, slashDelimitedRegex.length() - 1);
    }

    private int getDotIndex(String directive) {
        return directive.indexOf('.');
    }

    /**
     * @return a yaml style name from the classname of the supplied object (PhoneNumber => phone_number)
     */
    private String classNameToYamlName(Object current) {
        return javaNameToYamlName(current.getClass().getSimpleName());
    }

    /**
     * @return a yaml style name like 'phone_number' from a java style name like 'PhoneNumber'
     */
    private String javaNameToYamlName(String expression) {
        String result = NAME_2_YAML.get(expression);
        if (result != null) {
            return result;
        }

        final int length = expression.length();
        final boolean firstLetterUpperCase = length > 0 && Character.isUpperCase(expression.charAt(0));
        int cnt = firstLetterUpperCase ? 1 : 0;
        for (int i = 1; i < length; i++) {
            if (Character.isUpperCase(expression.charAt(i))) {
                cnt++;
            }
        }
        if (cnt == 0) {
            NAME_2_YAML.putIfAbsent(expression, expression);
            return expression;
        }
        final char[] res = new char[length + (firstLetterUpperCase ? cnt - 1 : cnt)];
        int pos = 0;
        for (int i = 0; i < length; i++) {
            final char c = expression.charAt(i);
            if (cnt > 0) {
                if (Character.isUpperCase(c)) {
                    if (pos > 0) {
                        res[pos++] = '_';
                    }
                    res[pos++] = Character.toLowerCase(c);
                    cnt--;
                } else {
                    res[pos++] = c;
                }
            } else {
                res[pos++] = c;
            }
        }
        result = new String(res);
        NAME_2_YAML.putIfAbsent(expression, result);
        return result;
    }


    /**
     * Given a directive like 'firstName', attempts to resolve it to a method.  For example if obj is an instance of
     * {@link Name} then this method would return {@link Name#firstName()}.  Returns null if the directive is nested
     * (i.e. has a '.') or the method doesn't exist on the <em>obj</em> object.
     */
    private Supplier<Object> resolveFromMethodOn(Object obj, String directive, String[] args) {
        if (obj == null) {
            return null;
        }
        try {
            final MethodAndCoercedArgs accessor = retrieveMethodAccessor(obj, directive, args);
            return (accessor == null)
                ? () -> null
                : () -> invokeAndToString(accessor, obj);
        } catch (Exception e) {
            LOG.log(Level.FINE, "Can't call " + directive + " on " + obj, e);
            return () -> null;
        }
    }

    /**
     * Accepts a {@link BaseFaker} instance and a name.firstName style 'key' which is resolved to the return value of:
     * {@link BaseFaker#name()}'s {@link Name#firstName()} method.
     *
     * @throws RuntimeException if there's a problem invoking the method or it doesn't exist.
     */
    private Supplier<Object> resolveFakerObjectAndMethod(ProviderRegistration faker, String key, int dotIndex, String[] args) {
        final String[] classAndMethod;
        if (dotIndex == -1) {
            classAndMethod = new String[]{key};
        } else {
            classAndMethod = new String[]{key.substring(0, dotIndex), dotIndex == key.length() - 1 ? "" : key.substring(dotIndex + 1)};
        }

        try {
            String fakerMethodName = removeUnderscoreChars(classAndMethod[0]);
            final MethodAndCoercedArgs fakerAccessor = retrieveMethodAccessor(faker, fakerMethodName, EMPTY_ARRAY);
            if (fakerAccessor == null) {
                LOG.fine("Can't find top level faker object named " + fakerMethodName + ".");
                return null;
            }
            Object objectWithMethodToInvoke = fakerAccessor.invoke(faker);
            String nestedMethodName = removeUnderscoreChars(classAndMethod[1]);
            final MethodAndCoercedArgs accessor = retrieveMethodAccessor(objectWithMethodToInvoke, nestedMethodName, args);
            if (accessor == null) {
                return () -> null;
            }

            return () -> invokeAndToString(accessor, objectWithMethodToInvoke);
        } catch (Exception e) {
            LOG.fine(e.getMessage());
            return () -> null;
        }
    }

    private MethodAndCoercedArgs retrieveMethodAccessor(Object object, String methodName, String[] args) {
        Class<?> clazz = object.getClass();
        Map<String[], MethodAndCoercedArgs> accessorMap =
            MAP_OF_METHOD_AND_COERCED_ARGS
                .getOrDefault(clazz, Collections.emptyMap())
                .getOrDefault(methodName, Collections.emptyMap());
        // value could be null
        if (accessorMap.containsKey(args)) {
            return accessorMap.get(args);
        }
        final MethodAndCoercedArgs accessor = accessor(clazz, methodName, args);
        final Map<String, Map<String[], MethodAndCoercedArgs>> stringMapMap =
            MAP_OF_METHOD_AND_COERCED_ARGS.computeIfAbsent(clazz, t -> new COWMap<>(WeakHashMap::new));
        stringMapMap.putIfAbsent(methodName, new COWMap<>(WeakHashMap::new));
        stringMapMap.get(methodName).putIfAbsent(args, accessor);
        if (accessor == null) {
            LOG.fine("Can't find method on "
                + object.getClass().getSimpleName()
                + " called " + methodName + ".");
        }
        return accessor;
    }

    private Object invokeAndToString(MethodAndCoercedArgs accessor, Object objectWithMethodToInvoke) {
        try {
            return accessor.invoke(objectWithMethodToInvoke);
        } catch (Exception e) {
            LOG.fine(e.getMessage());
            return null;
        }
    }


    /**
     * Find an accessor by name ignoring case.
     */
    private MethodAndCoercedArgs accessor(Class<?> clazz, String name, String[] args) {
        final String finalName = name;
        LOG.log(Level.FINE, () -> "Find accessor named " + finalName + " on " + clazz.getSimpleName() + " with args " + Arrays.toString(args));
        name = removeUnderscoreChars(name);
        final Collection<Method> methods;
        if (CLASS_2_METHODS_CACHE.containsKey(clazz)) {
            methods = CLASS_2_METHODS_CACHE.get(clazz).getOrDefault(name, Collections.emptyList());
        } else {
            Method[] classMethods = clazz.getMethods();
            Map<String, Collection<Method>> methodMap =
                classMethods.length == 0 ? Collections.emptyMap() : new HashMap<>(classMethods.length);
            for (Method m : classMethods) {
                final String key = m.getName().toLowerCase(Locale.ROOT);
                methodMap.computeIfAbsent(key, k -> new ArrayList<>());
                methodMap.get(key).add(m);
            }
            CLASS_2_METHODS_CACHE.putIfAbsent(clazz, methodMap);
            methods = methodMap.get(name);
        }
        if (methods == null) {
            return null;
        }
        for (Method m : methods) {
            if (m.getParameterCount() == args.length || m.getParameterCount() < args.length && m.isVarArgs()) {
                final Object[] coercedArguments = args.length == 0 ? EMPTY_ARRAY : coerceArguments(m, args);
                if (coercedArguments != null) {
                    return new MethodAndCoercedArgs(m, coercedArguments);
                }
            }
        }
        return null;
    }

    private String removeUnderscoreChars(String string) {
        String valueWithRemovedUnderscores = REMOVED_UNDERSCORE.get(string);
        if (valueWithRemovedUnderscores != null) {
            return valueWithRemovedUnderscores;
        }
        if (string.indexOf('_') == -1) {
            REMOVED_UNDERSCORE.putIfAbsent(string, string.toLowerCase(Locale.ROOT));
            return string;
        }
        final char[] res = string.toCharArray();
        int offset = 0;
        int length = 0;
        final int strLen = string.length();
        for (int i = strLen - 1; i >= offset; i--) {
            while (i > offset && string.charAt(i - offset) == '_') {
                offset++;
            }
            res[i] = res[i - offset];
            if (res[i] != '_') {
                length++;
            }
        }
        valueWithRemovedUnderscores = String.valueOf(res, strLen - length, length);
        REMOVED_UNDERSCORE.putIfAbsent(string, valueWithRemovedUnderscores.toLowerCase(Locale.ROOT));
        return valueWithRemovedUnderscores;
    }

    /**
     * Coerce arguments in <em>args</em> into the appropriate types (if possible) for the parameter arguments
     * to <em>accessor</em>.
     *
     * @return array of coerced values if successful, null otherwise
     */
    private Object[] coerceArguments(Method accessor, String[] args) {
        final Object[] coerced = new Object[accessor.getParameterCount()];
        final Class<?>[] parameterTypes = accessor.getParameterTypes();
        for (int i = 0; i < accessor.getParameterCount(); i++) {
            final boolean isVarArg = i == accessor.getParameterCount() - 1 && accessor.isVarArgs();
            Class<?> toType = primitiveToWrapper(parameterTypes[i]);
            toType = isVarArg ? toType.getComponentType() : toType;
            try {
                final Object coercedArgument;
                if (toType.isEnum()) {
                    Method method = toType.getMethod("valueOf", String.class);
                    if (isVarArg) {
                        coercedArgument = Array.newInstance(toType, args.length - i);
                        for (int j = i; j < args.length; j++) {
                            String enumArg = args[j].substring(args[j].indexOf(".") + 1);
                            Array.set(coercedArgument, j - i, method.invoke(null, enumArg));
                        }
                    } else {
                        String enumArg = args[i].substring(args[i].indexOf(".") + 1);
                        coercedArgument = method.invoke(null, enumArg);
                    }
                } else {
                    if (isVarArg) {
                        Constructor<?> ctor = CLASS_2_CONSTRUCTOR_CACHE.get(toType);
                        if (ctor == null) {
                            final Constructor<?>[] constructors = toType.getConstructors();
                            for (Constructor<?> c : constructors) {
                                if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == String.class) {
                                    ctor = toType.getConstructor(String.class);
                                    CLASS_2_CONSTRUCTOR_CACHE.putIfAbsent(toType, ctor);
                                    break;
                                }
                            }
                        }
                        if (ctor == null) {
                            return null;
                        }
                        coercedArgument = Array.newInstance(toType, args.length - i);
                        for (int j = i; j < args.length; j++) {
                            Array.set(coercedArgument, j - i, ctor.newInstance(args[j]));
                        }
                    } else if (toType == Character.class) {
                        coercedArgument = args[i] == null ? null : args[i].charAt(0);
                    } else if (Boolean.class == toType) {
                        coercedArgument = Boolean.valueOf(args[i]);
                    } else if (Integer.class == toType) {
                        coercedArgument = Integer.valueOf(args[i]);
                    } else if (Long.class == toType) {
                        coercedArgument = Long.valueOf(args[i]);
                    } else if (Double.class == toType) {
                        coercedArgument = Double.valueOf(args[i]);
                    } else if (Float.class == toType) {
                        coercedArgument = Float.valueOf(args[i]);
                    } else if (Byte.class == toType) {
                        coercedArgument = Byte.valueOf(args[i]);
                    } else if (Short.class == toType) {
                        coercedArgument = Short.valueOf(args[i]);
                    } else if (CharSequence.class.isAssignableFrom(toType)) {
                        coercedArgument = args[i];
                    } else if (BigDecimal.class.isAssignableFrom(toType)) {
                        coercedArgument = new BigDecimal(args[i]);
                    } else if (BigInteger.class.isAssignableFrom(toType)) {
                        coercedArgument = new BigInteger(args[i]);
                    } else {
                        final Constructor<?> ctor = toType.getConstructor(String.class);
                        coercedArgument = ctor.newInstance(args[i]);
                    }
                }
                coerced[i] = coercedArgument;
            } catch (Exception e) {
                LOG.fine("Unable to coerce " + args[i] + " to " + toType.getSimpleName() + " via " + toType.getSimpleName() + "(String) constructor.");
                return null;
            }
        }
        return coerced;
    }

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = new IdentityHashMap<>();

    static {
        PRIMITIVE_WRAPPER_MAP.put(Boolean.TYPE, Boolean.class);
        PRIMITIVE_WRAPPER_MAP.put(Byte.TYPE, Byte.class);
        PRIMITIVE_WRAPPER_MAP.put(Character.TYPE, Character.class);
        PRIMITIVE_WRAPPER_MAP.put(Short.TYPE, Short.class);
        PRIMITIVE_WRAPPER_MAP.put(Integer.TYPE, Integer.class);
        PRIMITIVE_WRAPPER_MAP.put(Long.TYPE, Long.class);
        PRIMITIVE_WRAPPER_MAP.put(Double.TYPE, Double.class);
        PRIMITIVE_WRAPPER_MAP.put(Float.TYPE, Float.class);
        PRIMITIVE_WRAPPER_MAP.put(Void.TYPE, Void.class);
    }

    public static Class<?> primitiveToWrapper(final Class<?> cls) {
        if (cls != null && cls.isPrimitive()) {
            return PRIMITIVE_WRAPPER_MAP.get(cls);
        }
        return cls;
    }

    /**
     * simple wrapper class around an accessor and a list of coerced arguments.
     * this is useful as we get to find the method and coerce the arguments in one
     * shot, returning both when successful.  This saves us from doing it more than once (coercing args).
     */
    private static class MethodAndCoercedArgs {

        private final Method method;

        private final Object[] coerced;

        private MethodAndCoercedArgs(Method m, Object[] coerced) {
            this.method = Objects.requireNonNull(m, "method cannot be null");
            this.coerced = Objects.requireNonNull(coerced, "coerced arguments cannot be null");
        }

        private Object invoke(Object on) throws InvocationTargetException, IllegalAccessException {
            return method.invoke(on, coerced);
        }
    }
}
