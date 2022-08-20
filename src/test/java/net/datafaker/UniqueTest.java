package net.datafaker;

import net.datafaker.service.RandomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniqueTest {

    private Faker faker = new Faker(new Locale("test"));

    private final List<String> defaultValues = asList(
        "firstValue",
        "secondValue",
        "thirdValue",
        "fourthValue",
        "fifthValue"
    );

    @Test
    public void fetchFromYaml_shouldReturnValuesInRandomOrderUsingRandomService() {
        String key = "unique.values";

        RandomService randomService = Mockito.spy(new RandomService(new Random()));
        doCallRealMethod().when(randomService).nextInt(anyInt(), anyInt());

        faker = new Faker(new Locale("test"), randomService);

        Set<String> results = new HashSet<>();

        results.add(faker.unique().fetchFromYaml(key));
        results.add(faker.unique().fetchFromYaml(key));
        results.add(faker.unique().fetchFromYaml(key));
        results.add(faker.unique().fetchFromYaml(key));
        results.add(faker.unique().fetchFromYaml(key));

        assertEquals(5, results.size());
        assertTrue(
            results.containsAll(defaultValues),
            String.format("\nExpected : Result set containing the following values: %s\nActual   : %s\n", defaultValues, results)
        );

        verify(randomService).nextInt(0, 4);
        verify(randomService).nextInt(0, 3);
        verify(randomService).nextInt(0, 2);
        verify(randomService).nextInt(0, 1);
        verify(randomService).nextInt(0, 0);
        verifyNoMoreInteractions(randomService);
    }

    @Test
    public void fetchFromYaml_shouldThrowExceptionWhenAllPossibleValuesHaveBeenReturned() {
        String key = "unique.single-value";

        assertEquals("theOnlyValue", faker.unique().fetchFromYaml(key));
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> faker.unique().fetchFromYaml(key));

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

        assertEquals("No values found for key unique", exception.getMessage());
    }

    @Test
    public void fetchFromYaml_shouldThrowExceptionWhenListOfListsFoundForKey() {
        Exception exception = assertThrows(Exception.class, () -> faker.unique().fetchFromYaml("unique.list-of-lists"));

        assertEquals("No values found for key unique.list-of-lists", exception.getMessage());
    }

    @Test
    public void fetchFromYaml_shouldNotInterfereWithValuesReturnedFromOtherFakers() {
        String key = "unique.values";

        for (int x = 0; x < defaultValues.size(); x++) {
            faker.unique().fetchFromYaml(key);
        }

        String result = faker.fakeValuesService().fetchString(key);

        assertTrue(
            defaultValues.contains(result),
            String.format("\nExpected : one of the following values: %s\nActual   : %s\n", defaultValues, result)
        );
    }

    @Test
    public void fetchFromYaml_shouldConvertIntegersToStrings() {
        String result = faker.unique().fetchFromYaml("unique.valid-integer");

        assertEquals("123", result);
    }

    @Test
    public void fetchFromYaml_shouldConvertDecimalsToStrings() {
        String result = faker.unique().fetchFromYaml("unique.valid-decimal");

        assertEquals("12.34", result);
    }

    @Test
    public void fetchFromYaml_shouldConvertBooleansToStrings() {
        String result = faker.unique().fetchFromYaml("unique.valid-boolean");

        assertEquals("true", result);
    }

}
