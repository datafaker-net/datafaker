package net.datafaker.providers.base;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;

import java.util.List;
import java.util.Collection;
import java.util.Locale;

class JobTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Job job = faker.job();
        return List.of(TestSpec.of(job::field, "job.field"),
            TestSpec.of(job::seniority, "job.seniority"),
            TestSpec.of(job::position, "job.position"),
            TestSpec.of(job::keySkills, "job.key_skills", "(?:[A-Za-z-]+ ?){1,3}"));
    }

    @Nested
    class JobInGreekTest extends BaseFakerTest<BaseFaker> {

        @BeforeAll
        void setup() {
            this.setFaker(new BaseFaker(new Locale("el", "GR")));
        }

        @AfterAll
        void reset() {
            this.setFaker(this.getFaker());
        }

        @Override
        protected Collection<TestSpec> providerListTest() {
            Job job = faker.job();
            return List.of(TestSpec.of(job::field, "job.field"),
                TestSpec.of(job::seniority, "job.seniority"),
                TestSpec.of(job::position, "job.position"),
                TestSpec.of(job::keySkills, "job.key_skills", "(?:\\p{L}+ ?){1,4}"));
        }
    }
}
