package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EducatorTest extends BaseFakerTest<BaseFaker> {

    @Test
    void university() {
        assertThat(faker.educator().university()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void course() {
        assertThat(faker.educator().course()).matches("(\\(?\\w+\\)? ?){3,6}");
    }

    @Test
    void secondarySchool() {
        assertThat(faker.educator().secondarySchool()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void campus() {
        assertThat(faker.educator().campus()).matches("(\\w+ ?){1,2}");
    }
}
