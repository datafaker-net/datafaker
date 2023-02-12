package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class BookTest extends BaseFakerTest<BaseFaker> {

    private final Book book = faker.book();

    @Test
    void testAuthor() {
        assertThat(book.author()).matches("([\\w']+\\.? ?){2,3}");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.book().title(), "book.title", "([\\p{L}'\\-?]+[!,]? ?){2,9}"),
            TestSpec.of(() -> faker.book().publisher(), "book.publisher", "([\\p{L}'&\\-]+[,.]? ?){1,5}"),
            TestSpec.of(() -> faker.book().genre(), "book.genre", "([\\w/]+ ?){2,4}"));
    }
}
