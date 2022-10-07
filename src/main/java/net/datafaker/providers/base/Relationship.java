package net.datafaker.providers.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * @since 0.8.0
 */
public class Relationship extends AbstractProvider<BaseProviders> {

    protected Relationship(final BaseProviders faker) {
        super(faker);
    }

    public String direct() {
        return resolve("relationship.familial.direct");
    }

    public String extended() {
        return resolve("relationship.familial.extended");
    }

    public String inLaw() {
        return resolve("relationship.in_law");
    }

    public String spouse() {
        return resolve("relationship.spouse");
    }

    public String parent() {
        return resolve("relationship.parent");
    }

    public String sibling() {
        return resolve("relationship.sibling");
    }

    public String any() {
        // Get name of current method
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        final String currentMethodName = stackTrace[1].getMethodName();

        try {
            Method[] methods = Arrays.stream(Relationship.class.getDeclaredMethods())
                .filter(declaredMethod -> !declaredMethod.getName().equals(currentMethodName) && Modifier.isPublic(declaredMethod.getModifiers()))
                .toArray(Method[]::new);
            int indx = faker.random().nextInt(methods.length);
            Method runMethod = methods[indx];
            Relationship relationship = new Relationship(faker);
            return (String) runMethod.invoke(relationship);
        } catch (SecurityException e) {
            throw new SecurityException("SecurityException: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("IllegalArgumentException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccessException: " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new RuntimeException("InvocationTargetException: " + e.getMessage());
        }
    }

}
