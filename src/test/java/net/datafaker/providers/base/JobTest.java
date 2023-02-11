package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class JobTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Job job = faker.job();
        return Arrays.asList(TestSpec.of(job::field, "job.field"),
            TestSpec.of(job::seniority, "job.seniority"),
            TestSpec.of(job::position, "job.position"),
            TestSpec.of(job::keySkills, "job.key_skills", "(:?[A-Za-z-]+ ?){1,3}"));
    }
}
