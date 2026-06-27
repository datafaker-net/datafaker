package net.datafaker.junit;

import net.datafaker.Faker;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * {@link ArgumentsProvider} backing {@link FakerSource}.
 * <p>
 * Resolves datafaker provider methods dynamically.
 *
 * <h3>Path resolution strategy</h3>
 * <p>
 * The descriptor path (before {@code #}) is resolved left-to-right as a chain.
 * Each segment is tried in this order:
 * <ol>
 *   <li>Instance field on the test class (or its superclasses)</li>
 *   <li>Static field on the test class (or its superclasses)</li>
 *   <li>No-arg method call on the current object (for chaining, e.g. {@code faker.address()})</li>
 * </ol>
 */
public class FakerSourceProvider implements ArgumentsProvider, AnnotationConsumer<FakerSource> {

    // "field#method(ParamType)" or "field.chain#method" or "field.chain" or "field#method"
    private static final Pattern DESCRIPTOR = Pattern.compile("^([\\w.]+)(?:#(\\w+))?(?:\\(([^)]*)\\))?$");

    private String               code;
    private String[]             params;
    private FakerSource.Param[]  multiParams;
    private String               locale;
    private boolean              distinct;
    private int                  repeat;

    @Override
    public void accept(FakerSource annotation) {
        code = annotation.code().trim();
        params = annotation.params();
        multiParams = annotation.multiParams();
        locale = annotation.locale().trim();
        distinct = annotation.distinct();
        repeat = Math.max(1, annotation.repeat());

        if (this.params.length > 0 && this.multiParams.length > 0) {
            throw new FakerSourceException("specify either 'params' or 'multiParams', but not both");
        }
    }

    /**
     * Overrides the new JUnit 5.12 entry point to avoid the deprecation-driven
     * {@code JUnitException} ("does not override provideArguments(ParameterDeclarations,…)").
     * Delegates to the classic {@link #provideArguments(ExtensionContext)} so all
     * existing logic stays in one place.
     */
    @Override
    public Stream<? extends Arguments> provideArguments(ParameterDeclarations parameters, ExtensionContext context) throws Exception {
        return provideArguments(context);
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Class<?> testClass = context.getRequiredTestClass();
        Object testInstance = context.getTestInstance().orElse(null);

        Descriptor desc = parseDescriptor(code);
        Object provider = locale.isEmpty()
            ? resolvePath(testClass, testInstance, desc.path())
            : resolvePathOnFaker(createLocaleFaker(), desc.path());

        String targetMethod = desc.methodName();

        // No "#method" in descriptor → the provider object itself is the result
        // (e.g. "BELGIAN_FAKER.address" returns an Address provider directly)
        if (targetMethod == null) {
            List<Arguments> result = new ArrayList<>(repeat);
            for (int i = 0; i < repeat; i++) {
                result.add(Arguments.of(provider));
            }
            Stream<Arguments> stream = result.stream();
            if (distinct) {
                Set<Object> seen = new LinkedHashSet<>();
                stream = stream.filter(a -> seen.add(a.get()[0]));
            }
            return stream;
        }

        Class<?> targetClass = provider.getClass();
        List<List<Object>> valuesPerPosition = resolveValuesPerPosition(desc, testClass, targetClass);
        List<Object[]> combos = createCartesianProduct(valuesPerPosition);

        List<Arguments> result = new ArrayList<>(combos.size() * repeat);
        for (Object[] combo : combos) {
            Method method = resolveMethod(targetClass, targetMethod, combo);
            method.setAccessible(true);

            for (int i = 0; i < repeat; i++) {
                Object fakerOutput = method.invoke(provider, combo);

                // test method receives: [fakerOutput, param1, param2, ...]
                Object[] testArgs = new Object[combo.length + 1];
                testArgs[0] = fakerOutput;
                System.arraycopy(combo, 0, testArgs, 1, combo.length);

                result.add(Arguments.of(testArgs));
            }
        }
        Stream<Arguments> stream = result.stream();
        if (distinct) {
            Set<Object> seen = new LinkedHashSet<>();
            stream = stream.filter(a -> seen.add(a.get()[0]));
        }
        return stream;
    }

    /**
     * Resolves a dot-separated path against the test class.
     * <p>
     * Each segment is resolved in order:
     * <ol>
     *   <li>Instance field on the test class hierarchy</li>
     *   <li>Static field on the test class hierarchy</li>
     *   <li>No-arg method call on the current object</li>
     * </ol>
     * <p>
     * Example paths:
     * <ul>
     *   <li>{@code "faker"}             → field {@code faker}</li>
     *   <li>{@code "address"}           → field {@code faker}, then {@code faker.address()}</li>
     *   <li>{@code "NL_FAKER.address"}  → static field {@code NL_FAKER}, then {@code .address()}</li>
     *   <li>{@code "NL_FAKER.address()} → same, explicit call syntax</li>
     * </ul>
     */
    private Object resolvePath(Class<?> testClass, Object testInstance, String path) throws Exception {
        String[] segments = path.split("\\.");
        Object current = null;

        for (int i = 0; i < segments.length; i++) {
            String seg = segments[i].replace("()", "").trim(); // tolerate trailing ()

            if (i == 0) {
                // first segment: look for a field (instance or static)
                Field field = findField(testClass, seg);
                if (field != null) {
                    current = getFieldValue(field, testClass, testInstance);
                } else {
                    // No field found – treat the first segment as a no-arg method
                    // on the 'faker' field (e.g. code = "address#latitude" where
                    // 'address' is not a field but faker.address() exists).
                    current = invokeViaFakerField(testClass, testInstance, seg);
                }
            } else {
                // subsequent segments: chain as no-arg method calls on current
                current = invokeNoArgMethod(current, seg);
            }
        }

        return current;
    }

    /**
     * Creates a fresh Faker for the configured locale.
     *
     * @return the instantiated Faker object
     */
    private Object createLocaleFaker() {
        try {
            return new Faker(Locale.forLanguageTag(locale));
        } catch (Exception ex) {
            throw new FakerSourceException(ex, "cannot create Faker for locale '%s'", locale);
        }
    }

    /**
     * Like {@link #resolvePath} but starts from an externally provided object
     * (the locale-specific Faker) instead of the test class fields.
     *
     * @param faker the root object to start navigation from
     * @param path the dot-separated path to resolve
     * @return the resolved object value
     * @throws Exception if reflection invocation fails
     */
    private Object resolvePathOnFaker(Object faker, String path) throws Exception {
        String[] segments = path.split("\\.");
        Object current = faker;
        for (String segment : segments) {
            current = invokeNoArgMethod(current, segment.replace("()", "").trim());
        }
        return current;
    }

    /**
     * Resolves a name that is not a field by looking for a no-arg method with
     * that name on the 'faker' instance field.
     *
     * @param testClass the class under test
     * @param testInstance the instance under test, might be null
     * @param methodName name of the method to look for on the faker field
     * @return the object returned by the invoked method
     * @throws Exception if target field or method cannot be read/invoked
     */
    private Object invokeViaFakerField(Class<?> testClass, Object testInstance, String methodName) throws Exception {
        Field fakerField = findField(testClass, "faker");
        if (fakerField != null) {
            Object fakerInstance = getFieldValue(fakerField, testClass, testInstance);
            if (fakerInstance != null) {
                try {
                    Method m = fakerInstance.getClass().getMethod(methodName);
                    m.setAccessible(true);
                    return m.invoke(fakerInstance);
                } catch (NoSuchMethodException ignored) {}
            }
        }
        throw new FakerSourceException("cannot resolve '%s' – not a field of %s and not a no-arg method on 'faker'",
            methodName, testClass.getSimpleName());
    }

    /**
     * Calls a public no-arg method on {@code target}.
     *
     * @param target the object to invoke the method on
     * @param methodName the name of the method
     * @return the method invocation result
     * @throws Exception if invocation fails
     */
    private Object invokeNoArgMethod(Object target, String methodName) throws Exception {
        try {
            Method m = target.getClass().getMethod(methodName);
            m.setAccessible(true);
            return m.invoke(target);
        } catch (NoSuchMethodException ex) {
            throw new FakerSourceException("no no-arg method '%s' on %s", methodName, target.getClass().getName());
        }
    }

    /**
     * Gathers all valid parameter values per parameter index based on the descriptor configuration.
     *
     * @param desc the parsed descriptor
     * @param testClass the current test class context
     * @param providerClass the class of the resolved datafaker provider
     * @return a list of lists containing available values per position
     */
    private List<List<Object>> resolveValuesPerPosition(Descriptor desc, Class<?> testClass, Class<?> providerClass) {
        List<List<Object>> result = new ArrayList<>();
        int paramCount = desc.paramTypeNames().size();

        for (int pos = 0; pos < paramCount; pos++) {
            String typeStr = desc.paramTypeNames().get(pos);
            Class<?> paramType = resolveType(typeStr, testClass, providerClass);

            if (pos < params.length) {
                // shortcut path: flat params array
                result.add(List.of(resolveSingleParamEntry(params[pos], paramType)));
            } else if (pos < multiParams.length) {
                // advanced path: multiParams array
                List<Object> resolvedValues = new ArrayList<>();
                for (String literal : multiParams[pos].value()) {
                    resolvedValues.add(resolveSingleParamEntry(literal, paramType));
                }
                result.add(resolvedValues);
            } else if (paramType.isEnum()) {
                result.add(allConstants(paramType));
            } else {
                throw new FakerSourceException("missing value for non-enum parameter type '%s'", typeStr);
            }
        }
        return result;
    }

    /**
     * Casts or parses a single string literal value to its proper target type wrapper.
     *
     * @param entry the raw string literal value
     * @param paramType the expected target type class
     * @return the type-safe value object
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object resolveSingleParamEntry(String entry, Class<?> paramType) {
        String trimmed = entry.trim();
        if (paramType.isEnum()) {
            String constantName = trimmed.contains(".") ? trimmed.substring(trimmed.lastIndexOf('.') + 1) : trimmed;
            return Enum.valueOf((Class<Enum>) paramType, constantName);
        } else if (paramType == short.class || paramType == Short.class) {
            return Short.parseShort(trimmed);
        } else if (paramType == int.class || paramType == Integer.class) {
            return Integer.parseInt(trimmed);
        } else if (paramType == long.class || paramType == Long.class) {
            return Long.parseLong(trimmed);
        } else if (paramType == boolean.class || paramType == Boolean.class) {
            return Boolean.parseBoolean(trimmed);
        }
        return trimmed;
    }

    /**
     * Resolves a target type class by checking built-in primitives, provider inner classes,
     * fully qualified names, package names and test class inner scopes.
     *
     * @param name simple or qualified name of the type
     * @param context the test class context
     * @param providerClass the provider target class
     * @return the resolved class object
     */
    private Class<?> resolveType(String name, Class<?> context, Class<?> providerClass) {
        switch (name) {
            case "int"     -> { return int.class; }
            case "long"    -> { return long.class; }
            case "boolean" -> { return boolean.class; }
            case "String", "java.lang.String" -> { return String.class; }
        }

        // "Outer.Inner" syntax: resolve Outer first, then walk into Inner
        if (name.contains(".") && !name.contains("$")) {
            String[] parts = name.split("\\.", 2);
            Class<?> resolved = resolveTypeSimple(parts[0], context, providerClass);
            if (resolved != null) {
                return findNestedClass(resolved, parts[1]);
            }
        }

        Class<?> result = resolveTypeSimple(name, context, providerClass);
        if (result != null) {
            return result;
        }
        throw new FakerSourceException("cannot resolve type '%s'", name);
    }

    /**
     * Resolves a simple (non-dotted) type name against the provider, classpath,
     * test package, and test class inner types. Returns {@code null} if not found.
     */
    private Class<?> resolveTypeSimple(String name, Class<?> context, Class<?> providerClass) {
        // inner classes of the resolved provider (e.g. Finance.CreditCardType)
        for (Class<?> inner : providerClass.getDeclaredClasses()) {
            if (inner.getSimpleName().equals(name)) {
                return inner;
            }
        }
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ignored) {}

        try {
            return Class.forName(context.getPackageName() + "." + name);
        } catch (ClassNotFoundException ignored) {}

        for (Class<?> inner : context.getDeclaredClasses()) {
            if (inner.getSimpleName().equals(name)) {
                return inner;
            }
        }
        return null;
    }

    /**
     * Walks into a nested class by simple name, supporting further dot-separated segments.
     * e.g. findNestedClass(Http.class, "Browser") returns Http.Browser.
     */
    private Class<?> findNestedClass(Class<?> outer, String simpleName) {
        if (simpleName.contains(".")) {
            String[] parts = simpleName.split("\\.", 2);
            for (Class<?> inner : outer.getDeclaredClasses()) {
                if (inner.getSimpleName().equals(parts[0])) {
                    return findNestedClass(inner, parts[1]);
                }
            }
        } else {
            for (Class<?> inner : outer.getDeclaredClasses()) {
                if (inner.getSimpleName().equals(simpleName)) {
                    return inner;
                }
            }
        }
        throw new FakerSourceException("cannot resolve nested type '%s' on %s", simpleName, outer.getSimpleName());
    }

    /**
     * Parses the string-based code descriptor into structured path and parameter elements.
     *
     * @param code raw code from the annotation attribute
     * @return a structured Descriptor instance
     */
    private Descriptor parseDescriptor(String code) {
        Matcher m = DESCRIPTOR.matcher(code);
        if (!m.matches()) {
            throw new FakerSourceException("cannot parse descriptor '%s'", code);
        }
        String path = m.group(1);
        String methodName = m.group(2); // null if absent
        String rawParams = Optional.ofNullable(m.group(3)).map(String::trim).orElse("");
        List<String> paramNames = rawParams.isEmpty() ? List.of() : Arrays.asList(rawParams.split("\\s*,\\s*"));

        return new Descriptor(path, methodName, paramNames);
    }

    /**
     * Retrieves all enum constants of a target enum type.
     *
     * @param enumType the enum class
     * @return a list containing all declared constants
     */
    private List<Object> allConstants(Class<?> enumType) {
        return Arrays.asList(enumType.getEnumConstants());
    }

    /**
     * Standard cartesian product builder to find all combinations across parameter positions.
     *
     * @param valuesPerPosition list of lists of available arguments per position
     * @return list of mixed object array combinations
     */
    private List<Object[]> createCartesianProduct(List<List<Object>> valuesPerPosition) {
        List<Object[]> result = new ArrayList<>();
        result.add(new Object[0]);
        for (List<Object> values : valuesPerPosition) {
            List<Object[]> next = new ArrayList<>(result.size() * values.size());
            for (Object[] existing : result) {
                for (Object value : values) {
                    Object[] combined = Arrays.copyOf(existing, existing.length + 1);
                    combined[existing.length] = value;
                    next.add(combined);
                }
            }
            result = next;
        }
        return result;
    }

    /**
     * Reads the current value from a reflection field handling static vs. instance scopes.
     *
     * @param field reflection field target
     * @param testClass class context
     * @param testInstance current test instance, might be null
     * @return field value contents
     * @throws Exception if access or initialization fails
     */
    private Object getFieldValue(Field field, Class<?> testClass, Object testInstance) throws Exception {
        field.setAccessible(true);
        if (Modifier.isStatic(field.getModifiers())) {
            return field.get(null);
        }
        if (testInstance != null) {
            return field.get(testInstance);
        }
        Class<?> declaring = field.getDeclaringClass();
        try {
            var constructor = declaring.getDeclaredConstructor();
            constructor.setAccessible(true);
            return field.get(constructor.newInstance());
        } catch (Exception ex) {
            throw new FakerSourceException(ex, "cannot instantiate %s to read field '%s'",
                declaring.getSimpleName(), field.getName());
        }
    }

    /**
     * Recursively searches for a field name upwards through the class hierarchy.
     *
     * @param clazz the starting class context
     * @param name field name
     * @return matching reflection field or null if not found
     */
    private Field findField(Class<?> clazz, String name) {
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            try {
                return c.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {}
        }
        return null;
    }

    /**
     * Identifies a target method that matches name and signature arguments.
     *
     * @param providerClass targeted provider class
     * @param methodName method name to match
     * @param args target values array
     * @return matching method metadata
     * @throws NoSuchMethodException if no compatible method signature exists
     */
    private Method resolveMethod(Class<?> providerClass, String methodName, Object[] args) throws NoSuchMethodException {
        Class<?>[] argTypes = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
        for (Method method : providerClass.getMethods()) {
            if (method.getName().equals(methodName) && matchParameters(method.getParameterTypes(), argTypes)) {
                return method;
            }
        }
        throw new FakerSourceException("no compatible method '%s' found on %s", methodName, providerClass.getName());
    }

    /**
     * Verifies array length equality and calls single type assignability checks.
     *
     * @param declared declared method argument types
     * @param actual runtime value argument types
     * @return true if all match up
     */
    private boolean matchParameters(Class<?>[] declared, Class<?>[] actual) {
        if (declared.length != actual.length) {
            return false;
        }
        for (int i = 0; i < declared.length; i++) {
            if (!isAssignable(declared[i], actual[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Type assignability logic honoring primitive types auto-boxing equivalencies.
     *
     * @param target required signature type
     * @param source provided data type
     * @return true if assignment is valid
     */
    private boolean isAssignable(Class<?> target, Class<?> source) {
        if (target.isAssignableFrom(source)) {
            return true;
        } else if (target == short.class && source == Short.class) {
            return true;
        } else if (target == int.class && source == Integer.class) {
            return true;
        } else if (target == long.class && source == Long.class) {
            return true;
        }
        return target == boolean.class && source == Boolean.class;
    }

    private record Descriptor(String path, String methodName, List<String> paramTypeNames) {}

    /**
     * Runtime exception dedicated to parsing and execution errors inside the provider.
     */
    static final class FakerSourceException extends RuntimeException {
        private static final long   serialVersionUID = 1L;
        private static final String ANNOT_NAME       = "@" + FakerSource.class.getSimpleName();

        FakerSourceException(String msgPattern, Object... args) {
            super(ANNOT_NAME + ": " + String.format(msgPattern, args));
        }

        FakerSourceException(Throwable cause, String msgPattern, Object... args) {
            super(ANNOT_NAME + ": " + String.format(msgPattern, args), cause);
        }
    }

}
