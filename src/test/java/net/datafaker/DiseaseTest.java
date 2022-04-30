package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiseaseTest extends AbstractFakerTest {
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
    void testDermatolory() {
        assertThat(faker.disease().dermatolory()).matches("[\\p{L}'()., 0-9-’]+");
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
        assertThat(faker.disease().dermatolory()).isNotEmpty();
    }
}
