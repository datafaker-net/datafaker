package net.datafaker.extensions.junit;

import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;

import java.lang.reflect.Parameter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("JunitJupiterDataFakerExtension should")
class JunitJupiterDataFakerExtensionTest {
    private final JunitJupiterDataFakerExtension extension = new JunitJupiterDataFakerExtension();

    @Test
    @DisplayName("not recognized a parameter that is not supported")
    void shouldNotRecognizeAParameterThatIsNotSupported() {
        var givenParameterContext = mock(ParameterContext.class);
        var mockedParameter = mock(Parameter.class);
        when(givenParameterContext.getParameter()).thenReturn(mockedParameter);
        when(mockedParameter.getType()).thenAnswer(args -> Integer.class);

        assertFalse(extension.supportsParameter(givenParameterContext,mock()));
    }

    @Test
    @DisplayName("recognize a method that contains Faker instance")
    void shouldRecognizeAMethodThatContainsFakerInterface() {
        var givenParameterContext = mock(ParameterContext.class);
        var mockedParameter = mock(Parameter.class);
        when(givenParameterContext.getParameter()).thenReturn(mockedParameter);
        when(mockedParameter.getType()).thenAnswer(args -> Faker.class);

        assertTrue(extension.supportsParameter(givenParameterContext,mock()));
    }

    @Test
    @DisplayName("return a faker object for the parameters")
    void shouldReturnAFakeObjectForTheParameters() {
        var givenParameterContext = mock(ParameterContext.class);
        var mockedParameter = mock(Parameter.class);
        when(givenParameterContext.getParameter()).thenReturn(mockedParameter);
        when(mockedParameter.getType()).thenAnswer(args -> Faker.class);

        var result = extension.resolveParameter(givenParameterContext,mock());
        assertNotNull(result);
        assertInstanceOf(Faker.class, result);
    }

    @Test
    @DisplayName("return the same instance whenever a faker object is required")
    void shouldReturnTheSameInstanceWheneverAFakeObjectIsRequired() {
        var givenParameterContext = mock(ParameterContext.class);
        var mockedParameter = mock(Parameter.class);
        when(givenParameterContext.getParameter()).thenReturn(mockedParameter);
        when(mockedParameter.getType()).thenAnswer(args -> Faker.class);

        var firstResult = extension.resolveParameter(givenParameterContext,mock());
        var secondResult = extension.resolveParameter(givenParameterContext,mock());

        assertSame(firstResult, secondResult);
    }
}
