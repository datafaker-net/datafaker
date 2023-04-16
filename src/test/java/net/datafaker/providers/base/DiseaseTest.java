package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class DiseaseTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Disease disease = faker.disease();
        return List.of(TestSpec.of(disease::internalDisease, "disease.internal_disease"),
                TestSpec.of(disease::neurology, "disease.neurology"),
                TestSpec.of(disease::surgery, "disease.surgery"),
                TestSpec.of(disease::paediatrics, "disease.paediatrics"),
                TestSpec.of(disease::gynecologyAndObstetrics, "disease.gynecology_and_obstetrics"),
                TestSpec.of(disease::ophthalmologyAndOtorhinolaryngology, "disease.ophthalmology_and_otorhinolaryngology"),
                TestSpec.of(disease::dermatology, "disease.dermatology"));
    }
}
