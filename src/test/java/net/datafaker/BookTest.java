package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BookTest extends AbstractFakerTest {

    @Test
    void testTitle() {
        assertThat(faker.book().title()).matches("([\\p{L}'\\-?]+[!,]? ?){2,9}");
    }

    @Test
    void testAuthor() {
        assertThat(faker.book().author()).matches("([\\w']+\\.? ?){2,3}");
    }

    @Test
    void testPublisher() {
        assertThat(faker.book().publisher()).matches("([\\p{L}'&\\-]+[,.]? ?){1,5}");
    }

    @Test
    void testGenre() {
        assertThat(faker.book().genre()).matches("([\\w/]+ ?){2,4}");
    }
}
