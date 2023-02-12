package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class DemographicTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Demographic demographic = faker.demographic();
        return List.of(TestSpec.of(demographic::race, "demographic.race", "(\\w+ ?)+"),
            TestSpec.of(demographic::educationalAttainment, "demographic.educational_attainment", "(?U)([\\w'-]+ ?)+"),
            TestSpec.of(demographic::demonym, "demographic.demonym", "(?U)([\\w'-]+ ?)+"),
            TestSpec.of(demographic::maritalStatus, "demographic.marital_status", "(\\w+ ?)+"),
            TestSpec.of(demographic::sex, "demographic.sex"));
    }
}
