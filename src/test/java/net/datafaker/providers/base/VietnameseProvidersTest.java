package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class VietnameseProvidersTest {
    private final Faker vi = new Faker(new Locale("vi"));
    private final Faker en = new Faker(Locale.ENGLISH);

    @ParameterizedTest
    @ValueSource(strings = {
        "color.name",
        "job.field", "job.seniority", "job.position", "job.key_skills", "job.employment_type", "job.education_level",
        "food.allergens", "food.dish", "food.ingredients", "food.fruits", "food.vegetables",
        "food.spices", "food.measurements",
        "book.title", "book.author", "book.publisher", "book.genre",
        "university.degree", "university.prefix", "university.suffix", "university.place",
        "commerce.department", "commerce.brand",
        "gender.types", "gender.binary_types",
        "weather.description",
        "compass.cardinal.word", "compass.ordinal.word", "compass.half-wind.word"
    })
    void vietnameseValuesDoNotFallBackToEnglish(String key) {
        List<String> vietnamese = list(vi, key);
        assertThat(vietnamese).isNotEmpty().doesNotContainAnyElementsOf(list(en, key));
    }

    @RepeatedTest(10)
    void providersReturnVietnameseValues() {
        assertThat(list(vi, "color.name")).contains(vi.color().name());
        assertThat(list(vi, "food.dish")).contains(vi.food().dish());
        assertThat(list(vi, "book.title")).contains(vi.book().title());
        assertThat(list(vi, "weather.description")).contains(vi.weather().description());
        assertThat(list(vi, "gender.binary_types")).contains(vi.gender().binaryTypes());
        assertThat(vi.university().name()).matches("(Trường Đại học|Học viện|Trường Cao đẳng) .+");
        String title = vi.job().title();
        assertThat(list(vi, "job.position")).anyMatch(title::startsWith);
        assertThat(vi.compass().word())
            .isIn(concat(list(vi, "compass.cardinal.word"), list(vi, "compass.ordinal.word"),
                list(vi, "compass.half-wind.word")));
    }

    @SuppressWarnings("unchecked")
    private static List<String> list(Faker faker, String key) {
        Object values = faker.fakeValuesService().fetchObject(key, faker.getContext());
        return values instanceof List ? (List<String>) values : List.of((String) values);
    }

    @SafeVarargs
    private static List<String> concat(List<String>... lists) {
        return Arrays.stream(lists).flatMap(List::stream).toList();
    }
}
