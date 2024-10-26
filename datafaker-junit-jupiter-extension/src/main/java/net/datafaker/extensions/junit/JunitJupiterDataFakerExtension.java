package net.datafaker.extensions.junit;

import net.datafaker.Faker;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * This junit extension injects a shared instance of a default Faker object.
 * You can use it following this example:
 * <pre><code> {@literal @}ExtendWith(JunitJupiterDataFakerExtension.class)
 * class LendingUseCaseTest {
 *    {@literal @}Test
 *     void shouldAllowToCreateALending(Faker faker){
 *         var isbn = faker.code().isbn13();
 *         //insert test logic.
 *     }
 * }
 * </code></pre>
 *
 * @author Alejandro Gómez Lucena
 */
public class JunitJupiterDataFakerExtension implements ParameterResolver {
    private final Faker faker = new Faker();

    @Override
    public boolean supportsParameter(
        ParameterContext parameterContext,
        ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(Faker.class);
    }

    @Override
    public Object resolveParameter(
        ParameterContext parameterContext,
        ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        return faker;
    }
}
