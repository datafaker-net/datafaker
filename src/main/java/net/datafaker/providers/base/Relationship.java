package net.datafaker.providers.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        Method[] methods = Arrays.stream(Relationship.class.getDeclaredMethods())
            .filter(declaredMethod -> !"any".equals(declaredMethod.getName()))
            .filter(declaredMethod -> !declaredMethod.isSynthetic())
            .toArray(Method[]::new);
        Method runMethod = faker.options().option(methods);

        try {
            return (String) runMethod.invoke(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to call " + runMethod.getName() + ": " + e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to call " + runMethod.getName() + ": " + e.getTargetException());
        }
    }

}
