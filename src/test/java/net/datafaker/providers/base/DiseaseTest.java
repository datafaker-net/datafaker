package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

class DiseaseTest extends AbstractBasicProviderTest<BaseFaker> {

    @Test
    void testInternalDisease() {
        assertThat(faker.disease().internalDisease()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testNeurology() {
        assertThat(faker.disease().neurology()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testSurgery() {
        assertThat(faker.disease().surgery()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testPaediatrics() {
        assertThat(faker.disease().paediatrics()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testGynecologyAndObstetrics() {
        assertThat(faker.disease().gynecologyAndObstetrics()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testOphthalmologyAndOtorhinolaryngology() {
        assertThat(faker.disease().ophthalmologyAndOtorhinolaryngology()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testDermatology() {
        assertThat(faker.disease().dermatology()).matches("[\\p{L}'()., 0-9-’]+");
    }


    @Test
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

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.disease().neurology(), "disease.neurology"),
                TestSpec.of(() -> faker.disease().surgery(), "disease.surgery"),
                TestSpec.of(() -> faker.disease().paediatrics(), "disease.paediatrics"),
                TestSpec.of(() -> faker.disease().gynecologyAndObstetrics(), "disease.gynecology_and_obstetrics"),
                TestSpec.of(() -> faker.disease().ophthalmologyAndOtorhinolaryngology(), "disease.ophthalmology_and_otorhinolaryngology"),
                TestSpec.of(() -> faker.disease().dermatology(), "disease.dermatology"));
    }
}
