package net.datafaker.service;

import com.mifmif.common.regex.Generex;
import net.datafaker.Faker;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class FakeValuesService {
    private static final Pattern LOCALE = Pattern.compile("[-_]");
    private static final Pattern A_TO_Z = Pattern.compile("([A-Z])");
    private static final String DIGITS = "0123456789";
    private static final String[] EMPTY_ARRAY = new String[0];
    private static final Logger LOG = Logger.getLogger("faker");

    private final Map<Locale, FakeValuesInterface> fakeValuesInterfaceMap = new HashMap<>();
    private final RandomService randomService;

    private final List<Locale> localesChain;

    private final Map<Class<?>, Map<String, Collection<Method>>> class2methodsCache = new IdentityHashMap<>();
    private final Map<String, Supplier<String>> expression2function = new WeakHashMap<>();
    private final Map<String, Generex> expression2generex = new WeakHashMap<>();

    /**
     * Resolves YAML file using the most specific path first based on language and country code.
     * 'en_US' would resolve in the following order:
     * <ol>
     * <li>/en-US.yml</li>
     * <li>/en.yml</li>
     * </ol>
     * The search is case-insensitive, so the following will all resolve correctly.  Also, either a hyphen or
     * an underscore can be used when constructing a {@link Locale} instance.  This is legacy behavior and not
     * condoned, but it will work.
     * <ul>
     * <li>EN_US</li>
     * <li>En-Us</li>
     * <li>eN_uS</li>
     * </ul>
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public FakeValuesService(Locale locale, RandomService randomService) {
        if (locale == null) {
            throw new IllegalArgumentException("locale is required");
        }
        this.randomService = randomService;
        locale = normalizeLocale(locale);

        localesChain = localeChain(locale);
        for (final Locale l : localesChain) {
            if (l.equals(Locale.ENGLISH)) {
                fakeValuesInterfaceMap.putIfAbsent(l, FakeValuesGrouping.getEnglishFakeValueGrouping());
            } else {
                fakeValuesInterfaceMap.putIfAbsent(l, new FakeValues(l));
            }
        }
    }

    /**
     * Allows to add paths to files with custom data. Data should be in YAML format.
     *
     * @param locale        the locale for which a path is going to be added.
     * @param path          path to a file with YAML structure
     * @throws IllegalArgumentException in case of invalid path
     */
    public void addPath(Locale locale, Path path) {
        Objects.requireNonNull(locale);
        if (path == null || Files.notExists(path) || Files.isDirectory(path) || !Files.isReadable(path)) {
            throw new IllegalArgumentException("Path should be an existing readable file");
        }
        FakeValues fakeValues = new FakeValues(locale, path);
        FakeValuesInterface existingFakeValues = fakeValuesInterfaceMap.get(locale);
        if (existingFakeValues == null) {
            fakeValuesInterfaceMap.putIfAbsent(locale, fakeValues);
        } else if (existingFakeValues instanceof FakeValuesGrouping) {
            ((FakeValuesGrouping)fakeValuesInterfaceMap.get(locale)).add(fakeValues);
        } else if (existingFakeValues instanceof FakeValues) {
            FakeValuesGrouping fakeValuesGrouping = new FakeValuesGrouping();
            fakeValuesGrouping.add((FakeValues) existingFakeValues);
            fakeValuesGrouping.add(fakeValues);
        } else {
            throw new RuntimeException(fakeValues.getClass() + " not supported (please raise an issue)");
        }
    }

    /**
     * Convert the specified locale into a chain of locales used for message resolution. For example:
     * <p>
     * {@link Locale#FRANCE} (fr_FR) to [ fr_FR, anotherTest, en ]
     *
     * @return a list of {@link Locale} instances
     */
    protected List<Locale> localeChain(Locale from) {
        if (Locale.ENGLISH.equals(from)) {
            return Collections.singletonList(Locale.ENGLISH);
        }

        final Locale normalized = normalizeLocale(from);

        final List<Locale> chain = new ArrayList<>(3);
        chain.add(normalized);
        if (!"".equals(normalized.getCountry()) && !Locale.ENGLISH.getLanguage().equals(normalized.getLanguage())) {
            chain.add(new Locale(normalized.getLanguage()));
        }
        chain.add(Locale.ENGLISH); // default
        return chain;
    }

    /**
     * @return a proper {@link Locale} instance with language and country code set regardless of how
     * it was instantiated.  new Locale("pt-br") will be normalized to a locale constructed
     * with new Locale("pt","BR").
     */
    private Locale normalizeLocale(Locale locale) {
        final String[] parts = LOCALE.split(locale.toString());

        if (parts.length == 1) {
            return new Locale(parts[0]);
        } else {
            return new Locale(parts[0], parts[1]);
        }
    }

    public List<Locale> getLocalesChain() {
        return localesChain;
    }

    /**
     * Fetch a random value from an array item specified by the key
     */
    public Object fetch(String key) {
        List<?> valuesArray = new ArrayList<>();
        if (fetchObject(key) instanceof ArrayList)
            valuesArray = (ArrayList<?>) fetchObject(key);
        return valuesArray == null ? null : valuesArray.get(randomService.nextInt(valuesArray.size()));
    }

    /**
     * Same as {@link #fetch(String)} except this casts the result into a String.
     */
    public String fetchString(String key) {
        return (String) fetch(key);
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
    public String safeFetch(String key, String defaultIfNull) {
        Object o = fetchObject(key);
        if (o == null) return defaultIfNull;
        if (o instanceof List) {
            List<String> values = (List<String>) o;
            if (values.size() == 0) {
                return defaultIfNull;
            }
            return values.get(randomService.nextInt(values.size()));
        } else if (isSlashDelimitedRegex(o.toString())) {
            return String.format("#{regexify '%s'}", trimRegexSlashes(o.toString()));
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
    public Object fetchObject(String key) {
        String[] path = split(key, '.');

        Object result = null;
        for (Locale locale : localesChain) {
            Object currentValue = fakeValuesInterfaceMap.get(locale);
            for (int p = 0; currentValue != null && p < path.length; p++) {
                String currentPath = path[p];
                if (currentValue instanceof Map) {
                    currentValue = ((Map) currentValue).get(currentPath);
                } else {
                    currentValue = ((FakeValuesInterface) currentValue).get(currentPath);
                }
            }
            result = currentValue;
            if (result != null) {
                break;
            }
        }
        return result;
    }

    private String[] split(String string, char sep) {
        int size = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == sep) size++;
        }
        String[] result = new String[size + 1];
        char[] chars = string.toCharArray();
        int start = 0;
        int j = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == sep) {
                if (i - start > 0) {
                    result[j++] = String.valueOf(chars, start, i - start);
                }
                start = i + 1;
            }
        }
        result[j] = String.valueOf(chars, start, chars.length - start);
        return result;
    }

    /**
     * Returns a string with the '#' characters in the parameter replaced with random digits between 0-9 inclusive.
     * <p>
     * For example, the string "ABC##EFG" could be replaced with a string like "ABC99EFG".
     */
    public String numerify(String numberString) {
        char[] res = new char[numberString.length()];
        for (int i = 0; i < numberString.length(); i++) {
            if (numberString.charAt(i) == '#') {
                res[i] = DIGITS.charAt(randomService.nextInt(10));
            } else {
                res[i] = numberString.charAt(i);
            }
        }

        return String.valueOf(res);
    }

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String)}
     * over the incoming string.
     */
    public String bothify(String string) {
        return letterify(numerify(string));
    }

    /**
     * Applies both a {@link #numerify(String)} and a {@link #letterify(String, boolean)}
     * over the incoming string.
     */
    public String bothify(String string, boolean isUpper) {
        return letterify(numerify(string), isUpper);
    }

    /**
     * Generates a String that matches the given regular expression.
     */
    public String regexify(String regex) {
        Generex generex = expression2generex.get(regex);
        if (generex == null) {
            generex = new Generex(regex);
            generex.setSeed(randomService.nextLong());
            expression2generex.put(regex, generex);
        }
        return generex.random();
    }

    /**
     * Generates a String by example. The output string will have the same pattern as the input string.
     */
    public String examplify(String example) {
        if (example == null) {
            return null;
        }
        char[] chars = example.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                chars[i] = letterify("?", Character.isUpperCase(chars[i])).charAt(0);
            } else if (Character.isDigit(chars[i])) {
                chars[i] = DIGITS.charAt(randomService.nextInt(10));
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
    public String letterify(String letterString) {
        return this.letterify(letterString, false);
    }

    /**
     * Returns a string with the '?' characters in the parameter replaced with random alphabetic
     * characters.
     * <p>
     * For example, the string "12??34" could be replaced with a string like "12AB34".
     */
    public String letterify(String letterString, boolean isUpper) {
        return letterHelper((isUpper) ? 65 : 97, letterString); // from ascii table
    }

    /**
     * Returns a string with the char2replace characters in the parameter replaced with random alphabetic
     * characters from options
     */
    public String templatify(String letterString, char char2replace, String... options) {
        return templatify(letterString, Collections.singletonMap(char2replace, options));
    }

    /**
     * Returns a string with the optionsMap.getKeys() characters in the parameter replaced with random alphabetic
     * characters from corresponding optionsMap.values()
     */
    public String templatify(String letterString, Map<Character, String[]> optionsMap) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < letterString.length(); i++) {
            if (optionsMap.containsKey(letterString.charAt(i))) {
                final String[] options = optionsMap.get(letterString.charAt(i));
                Objects.requireNonNull(options, "Array with available options should be non null");
                sb.append(options[randomService.nextInt(options.length)]);
            } else {
                sb.append(letterString.charAt(i));
            }
        }
        return sb.toString();
    }

    private String letterHelper(int baseChar, String letterString) {
        final char[] res = letterString.toCharArray();
        for (int i = 0; i < letterString.length(); i++) {
            if (letterString.charAt(i) == '?') {
                res[i] = (char) (baseChar + randomService.nextInt(26)); // a-z
            }
        }

        return String.valueOf(res);
    }

    /**
     * Resolves a key to a method on an object.
     * <p>
     * #{hello} with result in a method call to current.hello();
     * <p>
     * #{Person.hello_someone} will result in a method call to person.helloSomeone();
     */
    public String resolve(String key, Object current, Faker root) {
        final String expression = safeFetch(key, null);

        if (expression == null) {
            throw new RuntimeException(key + " resulted in null expression");
        }

        return resolveExpression(expression, current, root);
    }

    /**
     * resolves an expression using the current faker.
     */
    public String expression(String expression, Faker faker) {
        return resolveExpression(expression, null, faker);
    }

    /**
     * processes a expression in the style #{X.y} using the current objects as the 'current' location
     * within the yml file (or the {@link Faker} object hierarchy as it were).
     * <p>
     * #{Address.streetName} would get resolved to {@link Faker#address()}'s {@link net.datafaker.Address#streetName()}
     * #{address.street} would get resolved to the YAML like locale: faker: address: street:
     * Combinations are supported as well: "#{x} #{y}"
     * <p>
     * Recursive templates are supported.  if "#{x}" resolves to "#{Address.streetName}" then "#{x}" resolves to
     * {@link Faker#address()}'s {@link net.datafaker.Address#streetName()}.
     */
    protected String resolveExpression(String expression, Object current, Faker root) {
        List<String> expressions = splitExpressions(expression);
        for (int i = 0; i < expressions.size(); i++) {
            // odd are expressions, even are not expressions, just strings
            if (i % 2 != 0) {
                String expr = expressions.get(i);
                String resolved = expression2function.get(expr) == null ? null : expression2function.get(expr).get();
                if (resolved == null) {
                    int j = 0;
                    while (j < expr.length() && !Character.isWhitespace(expr.charAt(j))) j++;
                    String directive = expr.substring(0, j);
                    while (j < expr.length() && Character.isWhitespace(expr.charAt(j))) j++;
                    String arguments = j == expr.length() ? "" : expr.substring(j);
                    String[] args = splitArguments(arguments);

                    resolved = resolveExpression(expr, directive, args, current, root);
                    if (resolved == null) {
                        throw new RuntimeException("Unable to resolve #{" + expr + "} directive.");
                    }
                }
                expressions.set(i, resolveExpression(resolved, current, root));
            }
        }

        return String.join("", expressions);
    }

    private static String[] splitArguments(String arguments) {
        if (arguments == null || arguments.length() == 0) {
            return EMPTY_ARRAY;
        }
        List<String> result = new ArrayList<>();
        int start = 0;
        boolean argsStarted = false;
        for (int i = 0; i < arguments.length(); i++) {
            if (argsStarted) {
                if (arguments.charAt(i) == '\'') {
                    result.add(arguments.substring(start, i));
                    argsStarted = false;
                }
            } else if (arguments.charAt(i) == '\'') {
                argsStarted = true;
                start = i + 1;
            }
        }
        return result.toArray(new String[0]);
    }

    private static List<String> splitExpressions(String expression) {
        List<String> result = new ArrayList<>();
        boolean isExpression = false;
        int start = 0;
        int quoteCnt = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (isExpression) {
                if (expression.charAt(i) == '}' && quoteCnt % 2 == 0) {
                    result.add(expression.substring(start, i));
                    start = i + 1;
                    isExpression = false;
                } else if (expression.charAt(i) == '\'') {
                    quoteCnt++;
                }
            } else if (i < expression.length() - 2 && expression.charAt(i) == '#' && expression.charAt(i + 1) == '{') {
                result.add(expression.substring(start, i));
                isExpression = true;
                start = i + 2;
                i++;
            }
        }
        result.add(start < expression.length() ? expression.substring(start) : "");
        return result;
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
    private String resolveExpression(String expression, String directive, String[] args, Object current, Faker root) {
        // name.name (resolve locally)
        // Name.first_name (resolve to faker.name().firstName())
        final String simpleDirective = (isDotDirective(directive) || current == null)
            ? directive
            : classNameToYamlName(current) + "." + directive;

        String resolved;
        // resolve method references on CURRENT object like #{number_between '1','10'} on Number or
        // #{ssn_valid} on IdNumber
        if (!isDotDirective(directive)) {
            Supplier<String> supplier = resolveFromMethodOn(current, directive, args);
            if (supplier != null && (resolved = supplier.get()) != null) {
                expression2function.put(expression, supplier);
                return resolved;
            }
        }

        // simple fetch of a value from the yaml file. the directive may have been mutated
        // such that if the current yml object is car: and directive is #{wheel} then
        // car.wheel will be looked up in the YAML file.
        Supplier<String> supplier = () -> safeFetch(simpleDirective, null);
        resolved = supplier.get();
        if (resolved != null) {
            expression2function.put(expression, supplier);
            return resolved;
        }

        // resolve method references on faker object like #{regexify '[a-z]'}
        if (!isDotDirective(directive)) {
            supplier = resolveFromMethodOn(root, directive, args);
            if (supplier != null && (resolved = supplier.get()) != null) {
                expression2function.put(expression, supplier);
                return resolved;
            }
        }

        // Resolve Faker Object method references like #{ClassName.method_name}
        if (isDotDirective(directive)) {
            supplier = resolveFakerObjectAndMethod(root, directive, args);
            if (supplier != null && (resolved = supplier.get()) != null) {
                expression2function.put(expression, supplier);
                return resolved;
            }
        }

        // last ditch effort.  Due to Ruby's dynamic nature, something like 'Address.street_title' will resolve
        // because 'street_title' is a dynamic method on the Address object.  We can't do this in Java so we go
        // thru the normal resolution above, but if we will can't resolve it, we once again do a 'safeFetch' as we
        // did first but FIRST we change the Object reference Class.method_name with a yml style internal reference ->
        // class.method_name (lowercase)
        if (isDotDirective(directive)) {
            supplier = () -> safeFetch(javaNameToYamlName(simpleDirective), null);
            resolved = supplier.get();
            if (resolved != null) {
                expression2function.put(expression, supplier);
            }
        }

        return resolved;
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

    private boolean isDotDirective(String directive) {
        return directive.contains(".");
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
        return A_TO_Z.matcher(expression).replaceAll("_$1")
            .substring(1)
            .toLowerCase();
    }


    /**
     * Given a directive like 'firstName', attempts to resolve it to a method.  For example if obj is an instance of
     * {@link net.datafaker.Name} then this method would return {@link net.datafaker.Name#firstName()}.  Returns null if the directive is nested
     * (i.e. has a '.') or the method doesn't exist on the <em>obj</em> object.
     */
    private Supplier<String> resolveFromMethodOn(Object obj, String directive, String[] args) {
        if (obj == null) {
            return null;
        }
        try {
            final MethodAndCoercedArgs accessor = accessor(obj, directive, args);
            return (accessor == null)
                ? () -> null
                : () -> invokeAndToString(accessor, obj);
        } catch (Exception e) {
            LOG.log(Level.FINE, "Can't call " + directive + " on " + obj, e);
            return () -> null;
        }
    }

    /**
     * Accepts a {@link Faker} instance and a name.firstName style 'key' which is resolved to the return value of:
     * {@link Faker#name()}'s {@link net.datafaker.Name#firstName()} method.
     *
     * @throws RuntimeException if there's a problem invoking the method or it doesn't exist.
     */
    private Supplier<String> resolveFakerObjectAndMethod(Faker faker, String key, String[] args) {
        int index = key.indexOf('.');
        final String[] classAndMethod;
        if (index == -1) {
            classAndMethod = new String[]{key};
        } else {
            classAndMethod = new String[]{key.substring(0, index), index == key.length() - 1 ? "" : key.substring(index + 1)};
        }

        try {
            String fakerMethodName = removeChars(classAndMethod[0], '_');
            MethodAndCoercedArgs fakerAccessor = accessor(faker, fakerMethodName, EMPTY_ARRAY);
            if (fakerAccessor == null) {
                LOG.fine("Can't find top level faker object named " + fakerMethodName + ".");
                return null;
            }
            Object objectWithMethodToInvoke = fakerAccessor.invoke(faker);
            String nestedMethodName = removeChars(classAndMethod[1], '_');
            final MethodAndCoercedArgs accessor = accessor(objectWithMethodToInvoke, nestedMethodName, args);
            if (accessor == null) {
                throw new Exception("Can't find method on "
                    + objectWithMethodToInvoke.getClass().getSimpleName()
                    + " called " + nestedMethodName + ".");
            }

            return () -> invokeAndToString(accessor, objectWithMethodToInvoke);
        } catch (Exception e) {
            LOG.fine(e.getMessage());
            return () -> null;
        }
    }

    private String invokeAndToString(MethodAndCoercedArgs accessor, Object objectWithMethodToInvoke) {
        try {
            return string(accessor.invoke(objectWithMethodToInvoke));
        } catch (Exception e) {
            LOG.fine(e.getMessage());
            return null;
        }
    }


    /**
     * Find an accessor by name ignoring case.
     */
    private MethodAndCoercedArgs accessor(Object onObject, String name, String[] args) {
        LOG.log(Level.FINE, () -> "Find accessor named " + name + " on " + onObject.getClass().getSimpleName() + " with args " + Arrays.toString(args));

        final Class clazz = onObject.getClass();
        if (!class2methodsCache.containsKey(clazz)) {
            Map<String, Collection<Method>> methodMap = new HashMap<>();
            for (Method m : clazz.getMethods()) {
                methodMap.computeIfAbsent(m.getName().toLowerCase(Locale.ROOT), k -> new ArrayList<>());
                methodMap.get(m.getName().toLowerCase(Locale.ROOT)).add(m);
            }
            class2methodsCache.put(clazz, methodMap);
        }
        final Collection<Method> methods =
            class2methodsCache.get(clazz).getOrDefault(name.toLowerCase(Locale.ROOT), Collections.emptyList());
        for (Method m : methods) {
            if (m.getParameterTypes().length == args.length || m.getParameterTypes().length < args.length && m.isVarArgs()) {
                final Object[] coercedArguments = coerceArguments(m, args);
                if (coercedArguments != null) {
                    return new MethodAndCoercedArgs(m, coercedArguments);
                }
            }
        }

        if (name.contains("_")) {
            return accessor(onObject, removeChars(name, '_'), args);
        }
        return null;
    }

    private static String removeChars(String string, char char2remove) {
        char[] res = string.toCharArray();
        int offset = 0;
        int length = 0;
        for (int i = string.length() - 1; i >= offset; i--) {
            while (i > offset && string.charAt(i - offset) == char2remove) {
                offset++;
            }
            res[i] = res[i - offset];
            if (res[i] != char2remove) {
                length++;
            }
        }
        return String.valueOf(res, string.length() - length, length);
    }

    /**
     * Coerce arguments in <em>args</em> into the appropriate types (if possible) for the parameter arguments
     * to <em>accessor</em>.
     *
     * @return array of coerced values if successful, null otherwise
     */
    private Object[] coerceArguments(Method accessor, String[] args) {
        final Object[] coerced = new Object[accessor.getParameterTypes().length];
        for (int i = 0; i < accessor.getParameterTypes().length; i++) {
            final boolean isVarArg = i == accessor.getParameterTypes().length - 1 && accessor.isVarArgs();
            Class<?> toType = primitiveToWrapper(accessor.getParameterTypes()[i]);
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
                        final Constructor<?> ctor = toType.getConstructor(String.class);
                        coercedArgument = Array.newInstance(toType, args.length - i);
                        for (int j = i; j < args.length; j++) {
                            Array.set(coercedArgument, j - i, ctor.newInstance(args[j]));
                        }
                    } else if (toType == Character.class) {
                        coercedArgument = args[i] == null ? null : args[i].charAt(0);
                    } else if (CharSequence.class.isAssignableFrom(toType)) {
                        coercedArgument = args[i];
                    } else if (Boolean.class.isAssignableFrom(toType)) {
                        coercedArgument = Boolean.valueOf(args[i]);
                    } else if (Integer.class.isAssignableFrom(toType)) {
                        coercedArgument = Integer.valueOf(args[i]);
                    } else if (Long.class.isAssignableFrom(toType)) {
                        coercedArgument = Long.valueOf(args[i]);
                    } else if (Double.class.isAssignableFrom(toType)) {
                        coercedArgument = Double.valueOf(args[i]);
                    } else if (Float.class.isAssignableFrom(toType)) {
                        coercedArgument = Float.valueOf(args[i]);
                    } else if (Byte.class.isAssignableFrom(toType)) {
                        coercedArgument = Byte.valueOf(args[i]);
                    } else if (Short.class.isAssignableFrom(toType)) {
                        coercedArgument = Short.valueOf(args[i]);
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
        PRIMITIVE_WRAPPER_MAP.put(Void.TYPE, Void.TYPE);
    }

    public static Class<?> primitiveToWrapper(final Class<?> cls) {
        Class<?> convertedClass = cls;
        if (cls != null && cls.isPrimitive()) {
            convertedClass = PRIMITIVE_WRAPPER_MAP.get(cls);
        }
        return convertedClass;
    }


    private String string(Object obj) {
        return (obj == null) ? null : obj.toString();
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
