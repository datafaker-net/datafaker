package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

class DiseaseTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Disease disease = faker.disease();
        return Arrays.asList(TestSpec.of(disease::internalDisease, "disease.internal_disease", "[\\p{L}'()., 0-9-’]+"),
                TestSpec.of(disease::neurology, "disease.neurology", "[\\p{L}'()., 0-9-’]+"),
                TestSpec.of(disease::surgery, "disease.surgery", "[\\p{L}'()., 0-9-’]+"),
                TestSpec.of(disease::paediatrics, "disease.paediatrics", "[\\p{L}'()., 0-9-’]+"),
                TestSpec.of(disease::gynecologyAndObstetrics, "disease.gynecology_and_obstetrics", "[\\p{L}'()., 0-9-’]+"),
                TestSpec.of(disease::ophthalmologyAndOtorhinolaryngology, "disease.ophthalmology_and_otorhinolaryngology", "[\\p{L}'()., 0-9-’]+"),
                TestSpec.of(disease::dermatology, "disease.dermatology", "[\\p{L}'()., 0-9-’]+"));
    }

    void testInternalDiseaseWith10000Times() {
        boolean isExist = false;
        for (int i = 0; i < 10000; i++) {
            String generateString = faker.disease().internalDisease();
            if (generateString.equals("anaphylaxis")) {
                isExist = true;
            }
        }
        assertThat(isExist).isTrue();
    }

    @RepeatedTest(1000)
    void testNeurologyWith1000Times() {
        assertThat(faker.disease().neurology()).isNotEmpty();
    }

    @RepeatedTest(1000)
    void testSurgeryWith1000Times() {
        assertThat(faker.disease().surgery()).isNotEmpty();
    }

    @RepeatedTest(1000)
    void testPaediatricsWith1000Times() {
        assertThat(faker.disease().paediatrics()).isNotEmpty();
    }

    @RepeatedTest(1000)
    void testGynecologyAndObstetricsWith1000Times() {
        assertThat(faker.disease().gynecologyAndObstetrics()).isNotEmpty();
    }

    @RepeatedTest(1000)
    void testOphthalmologyAndOtorhinolaryngologyWith1000Times() {
        assertThat(faker.disease().ophthalmologyAndOtorhinolaryngology()).isNotEmpty();
    }

    @RepeatedTest(10000)
    void testDermatoloryWith10000Times() {
        assertThat(faker.disease().dermatology()).isNotEmpty();
    }
}
