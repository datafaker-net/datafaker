package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiseaseTest extends BaseFakerTest<BaseFaker> {
    @Test
    void internalDisease() {
        assertThat(faker.disease().internalDisease()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void neurology() {
        assertThat(faker.disease().neurology()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void surgery() {
        assertThat(faker.disease().surgery()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void paediatrics() {
        assertThat(faker.disease().paediatrics()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void gynecologyAndObstetrics() {
        assertThat(faker.disease().gynecologyAndObstetrics()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void ophthalmologyAndOtorhinolaryngology() {
        assertThat(faker.disease().ophthalmologyAndOtorhinolaryngology()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void dermatolory() {
        assertThat(faker.disease().dermatolory()).matches("[\\p{L}'()., 0-9-’]+");
    }


    @Test
    void internalDiseaseWith10000Times() {
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
    void neurologyWith1000Times() {
        assertThat(faker.disease().neurology()).isNotEmpty();
    }

    @RepeatedTest(1000)
    void surgeryWith1000Times() {
        assertThat(faker.disease().surgery()).isNotEmpty();
    }

    @RepeatedTest(1000)
    void paediatricsWith1000Times() {
        assertThat(faker.disease().paediatrics()).isNotEmpty();
    }

    @RepeatedTest(1000)
    void gynecologyAndObstetricsWith1000Times() {
        assertThat(faker.disease().gynecologyAndObstetrics()).isNotEmpty();
    }

    @RepeatedTest(1000)
    void ophthalmologyAndOtorhinolaryngologyWith1000Times() {
        assertThat(faker.disease().ophthalmologyAndOtorhinolaryngology()).isNotEmpty();
    }

    @RepeatedTest(10000)
    void dermatoloryWith10000Times() {
        assertThat(faker.disease().dermatolory()).isNotEmpty();
    }
}
