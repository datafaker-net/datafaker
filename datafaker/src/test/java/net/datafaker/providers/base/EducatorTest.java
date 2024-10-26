package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EducatorTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testUniversity() {
        assertThat(faker.educator().university()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void testCourse() {
        assertThat(faker.educator().course()).matches("(\\(?\\w+\\)? ?){3,6}");
    }

    @RepeatedTest(10)
    void testSubjectWithNumber() {
        assertThat(faker.educator().subjectWithNumber()).matches("[a-zA-Z() ]+ [1-5][0-9]{2}");
    }

    @Test
    void testSecondarySchool() {
        assertThat(faker.educator().secondarySchool()).matches("(\\w+ ?){2,3}");
    }

    @Test
    void testCampus() {
        assertThat(faker.educator().campus()).matches("(\\w+ ?){1,2}");
    }
}
