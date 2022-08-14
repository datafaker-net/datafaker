package net.datafaker;

import net.datafaker.service.RandomService;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniqueTest {

    Faker faker = new Faker(new Locale("test"));

    @Test
    public void fetchFromYaml_shouldReturnValuesInRandomOrderUsingRandomService() {
        String key = "unique.values";

        RandomService randomService = mock(RandomService.class);

        when(randomService.nextInt(0, 4)).thenReturn(0);
        when(randomService.nextInt(0, 3)).thenReturn(3);
        when(randomService.nextInt(0, 2)).thenReturn(1);
        when(randomService.nextInt(0, 1)).thenReturn(0);
        when(randomService.nextInt(0, 0)).thenReturn(0);

        faker = new Faker(new Locale("test"), randomService);

        assertEquals("firstValue", faker.unique().fetchFromYaml(key));
        assertEquals("fifthValue", faker.unique().fetchFromYaml(key));
        assertEquals("thirdValue", faker.unique().fetchFromYaml(key));
        assertEquals("secondValue", faker.unique().fetchFromYaml(key));
        assertEquals("fourthValue", faker.unique().fetchFromYaml(key));
    }

    @Test
    public void fetchFromYaml_shouldThrowExceptionWhenAllPossibleValuesHaveBeenReturned() {
        String key = "unique.single-value";

        assertEquals("theOnlyValue", faker.unique().fetchFromYaml(key));
        Exception exception = assertThrows(Exception.class, () -> faker.unique().fetchFromYaml(key));

        assertEquals(
            "All possible values have been generated for key unique.single-value under locale test",
            exception.getMessage()
        );
    }

    @Test
    public void fetchFromYaml_shouldReturnValuesBasedOnKeyAndLocale() {
        String firstKey = "unique.first-same-locale-value";
        String secondKey = "unique.second-same-locale-value";

        String expectedValue = "theSameValue";

        assertEquals(expectedValue, faker.unique().fetchFromYaml(firstKey));
        assertEquals(expectedValue, faker.unique().fetchFromYaml(secondKey));
        faker.fakeValuesService().setCurrentLocale(new Locale("test_otherlocale"));
        assertEquals(expectedValue, faker.unique().fetchFromYaml(firstKey));
        assertEquals(expectedValue, faker.unique().fetchFromYaml(secondKey));
    }

    @Test
    public void fetchFromYaml_shouldThrowExceptionWhenNoValuesFoundForKey() {
        Exception exception = assertThrows(Exception.class, () -> faker.unique().fetchFromYaml("unique.nonexistent-values"));

        assertEquals("No values found for key unique.nonexistent-values", exception.getMessage());
    }

    @Test
    public void fetchFromYaml_shouldThrowExceptionWhenNonListValueFoundForKey() {
        Exception exception = assertThrows(Exception.class, () -> faker.unique().fetchFromYaml("unique"));

        assertEquals("Object found for key unique is not in list format", exception.getMessage());
    }

    @Test
    public void fetchFromYaml_shouldNotInterfereWithValuesReturnedFromOtherFakers() {
        String key = "unique.values";

        List<String> expectedValues = asList(
            "firstValue",
            "secondValue",
            "thirdValue",
            "fourthValue",
            "fifthValue"
        );

        for(int x = 0; x < expectedValues.size(); x++) {
            faker.unique().fetchFromYaml(key);
        }

        String result = faker.fakeValuesService().fetchString(key);

        assertTrue(
            expectedValues.contains(result),
            String.format("\nExpected : one of the following values: %s\nActual   : %s\n", expectedValues, result)
        );
    }

}
