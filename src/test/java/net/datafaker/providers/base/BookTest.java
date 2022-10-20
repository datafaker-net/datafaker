package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest extends BaseFakerTest<BaseFaker> {

    @Test
    void title() {
        assertThat(faker.book().title()).matches("([\\p{L}'\\-?]+[!,]? ?){2,9}");
    }

    @Test
    void author() {
        assertThat(faker.book().author()).matches("([\\w']+\\.? ?){2,3}");
    }

    @Test
    void publisher() {
        assertThat(faker.book().publisher()).matches("([\\p{L}'&\\-]+[,.]? ?){1,5}");
    }

    @Test
    void genre() {
        assertThat(faker.book().genre()).matches("([\\w/]+ ?){2,4}");
    }
}
