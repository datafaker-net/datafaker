package net.datafaker.providers.base;

import net.datafaker.service.RandomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UniqueTest {

    private BaseFaker faker = new BaseFaker(new Locale("test"));

    private final List<String> defaultValues = List.of(
        "firstValue",
        "secondValue",
        "thirdValue",
        "fourthValue",
        "fifthValue"
    );

    @Test
    void fetchFromYaml_shouldReturnValuesInRandomOrderUsingRandomService() {
        String key = "unique.values";

        RandomService randomService = Mockito.spy(new RandomService(new Random()));
        doCallRealMethod().when(randomService).nextInt(anyInt(), anyInt());

        faker = new BaseFaker(new Locale("test"), randomService);

        Set<String> results = new HashSet<>();

        results.add(faker.unique().fetchFromYaml(key));
        results.add(faker.unique().fetchFromYaml(key));
        results.add(faker.unique().fetchFromYaml(key));
        results.add(faker.unique().fetchFromYaml(key));
        results.add(faker.unique().fetchFromYaml(key));

        assertThat(results)
            .hasSize(5)
            .containsAll(defaultValues);

        verify(randomService).nextInt(0, 4);
        verify(randomService).nextInt(0, 3);
        verify(randomService).nextInt(0, 2);
        verify(randomService).nextInt(0, 1);
        verify(randomService).nextInt(0, 0);
        verifyNoMoreInteractions(randomService);
    }

    @Test
    void fetchFromYaml_shouldThrowExceptionWhenAllPossibleValuesHaveBeenReturned() {
        String key = "unique.single-value";

        assertThat(faker.unique().fetchFromYaml(key)).isEqualTo("theOnlyValue");
        assertThatThrownBy(() -> faker.unique().fetchFromYaml(key))
            .hasMessage("All possible values have been generated for key unique.single-value under locale test");
    }

    @Test
    void fetchFromYaml_shouldReturnValuesBasedOnKeyAndLocale() {
        String firstKey = "unique.first-same-locale-value";
        String secondKey = "unique.second-same-locale-value";

        String expectedValue = "theSameValue";

        assertThat(faker.unique().fetchFromYaml(firstKey)).isEqualTo(expectedValue);
        assertThat(faker.unique().fetchFromYaml(secondKey)).isEqualTo(expectedValue);
        faker.getContext().setCurrentLocale(new Locale("test_otherlocale"));
        assertThat(faker.unique().fetchFromYaml(firstKey)).isEqualTo(expectedValue);
        assertThat(faker.unique().fetchFromYaml(secondKey)).isEqualTo(expectedValue);
    }

    @Test
    void fetchFromYaml_shouldThrowExceptionWhenNoValuesFoundForKey() {
        assertThatThrownBy(() -> faker.unique().fetchFromYaml("unique.nonexistent-values"))
            .hasMessage("No values found for key unique.nonexistent-values");
    }

    @Test
    void fetchFromYaml_shouldThrowExceptionWhenNonListValueFoundForKey() {
        assertThatThrownBy(() -> faker.unique().fetchFromYaml("unique"))
            .hasMessage("No values found for key unique");
    }

    @Test
    void fetchFromYaml_shouldThrowExceptionWhenListOfListsFoundForKey() {
        assertThatThrownBy(() -> faker.unique().fetchFromYaml("unique.list-of-lists"))
            .hasMessage("No values found for key unique.list-of-lists");
    }

    @Test
    void fetchFromYaml_shouldNotInterfereWithValuesReturnedFromOtherFakers() {
        String key = "unique.values";

        for (int x = 0; x < defaultValues.size(); x++) {
            faker.unique().fetchFromYaml(key);
        }

        String result = faker.unique().resolve(key);

        assertThat(defaultValues).contains(result);
    }

    @Test
    void fetchFromYaml_shouldConvertIntegersToStrings() {
        assertThat(faker.unique().fetchFromYaml("unique.valid-integer"))
            .isEqualTo("123");
    }

    @Test
    void fetchFromYaml_shouldConvertDecimalsToStrings() {
        assertThat(faker.unique().fetchFromYaml("unique.valid-decimal"))
            .isEqualTo("12.34");
    }

    @Test
    void fetchFromYaml_shouldConvertBooleansToStrings() {
        assertThat(faker.unique().fetchFromYaml("unique.valid-boolean"))
            .isEqualTo("true");
    }

}
