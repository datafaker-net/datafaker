package net.datafaker.providers.base;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class JobTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Job job = faker.job();
        return List.of(TestSpec.of(job::field, "job.field"),
            TestSpec.of(job::seniority, "job.seniority"),
            TestSpec.of(job::position, "job.position"),
            TestSpec.of(job::keySkills, "job.key_skills", "(?:[A-Za-z-]+ ?){1,3}"));
    }

    @Nested
    final class JobInChineseTest extends ProviderListTest<BaseFaker> {

        private final BaseFaker faker = new BaseFaker(Locale.SIMPLIFIED_CHINESE);

        @Override
        protected BaseFaker getFaker() {
            return faker;
        }

        @Override
        protected Collection<TestSpec> providerListTest() {
            Job job = faker.job();
            return List.of(TestSpec.of(job::field, "job.field", "\\p{IsHan}+"),
                TestSpec.of(job::seniority, "job.seniority", "\\p{IsHan}+"),
                TestSpec.of(job::position, "job.position", "\\p{IsHan}+"),
                TestSpec.of(job::keySkills, "job.key_skills", "\\p{IsHan}+"),
                TestSpec.of(() -> faker.expression("#{job.employment_type}"), "job.employment_type", "\\p{IsHan}+"),
                TestSpec.of(() -> faker.expression("#{job.education_level}"), "job.education_level", "\\p{IsHan}+"));
        }

        @Test
        void title() {
            String title = faker.job().title();
            List<String> positions = getBaseList(faker, "job.position");
            assertThat(title).matches("\\p{IsHan}+");
            assertThat(positions).anyMatch(title::endsWith, "position suffix of " + title);
            assertThat(getBaseList(faker, "job.title"))
                .isNotEmpty()
                .allSatisfy(template -> assertThat(template).endsWith("#{Job.position}"));
        }
    }

    @Nested
    final class JobInGreekTest extends ProviderListTest<BaseFaker> {

        private final BaseFaker faker = new BaseFaker(new Locale("el", "GR"));

        @Override
        protected BaseFaker getFaker() {
            return faker;
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
