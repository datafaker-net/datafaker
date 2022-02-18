package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.IsStringWithContents.isStringWithContents;
import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiseaseTest extends AbstractFakerTest {
    @Test
    public void testInternalDisease() {
        assertThat(faker.disease().internalDisease(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }

    @Test
    public void testNeurology() {
        assertThat(faker.disease().neurology(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }

    @Test
    public void testSurgery() {
        assertThat(faker.disease().surgery(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }

    @Test
    public void testPaediatrics() {
        assertThat(faker.disease().paediatrics(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }

    @Test
    public void testGynecologyAndObstetrics() {
        assertThat(faker.disease().gynecologyAndObstetrics(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }

    @Test
    public void testOphthalmologyAndOtorhinolaryngology() {
        assertThat(faker.disease().ophthalmologyAndOtorhinolaryngology(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }

    @Test
    public void testDermatolory() {
        assertThat(faker.disease().dermatolory(), matchesRegularExpression("[\\p{L}'()\\., 0-9-’’]+"));
    }


    @Test
    public void testInternalDiseaseWith10000Times() {
        boolean isExist = false;
        for (int i = 0; i < 10000; i++) {
            String generateString = faker.disease().internalDisease();
            if (generateString.equals("anaphylaxis")) {
                isExist = true;
            }
        }
        assertTrue(isExist);
    }

    @RepeatedTest(1000)
    public void testNeurologyWith1000Times() {
        assertThat(faker.disease().neurology(), isStringWithContents());
    }

    @RepeatedTest(1000)
    public void testSurgeryWith1000Times() {
        assertThat(faker.disease().surgery(), isStringWithContents());
    }

    @RepeatedTest(1000)
    public void testPaediatricsWith1000Times() {
        assertThat(faker.disease().paediatrics(), isStringWithContents());
    }

    @RepeatedTest(1000)
    public void testGynecologyAndObstetricsWith1000Times() {
        assertThat(faker.disease().gynecologyAndObstetrics(), isStringWithContents());
    }

    @RepeatedTest(1000)
    public void testOphthalmologyAndOtorhinolaryngologyWith1000Times() {
        assertThat(faker.disease().ophthalmologyAndOtorhinolaryngology(), isStringWithContents());
    }

    @RepeatedTest(10000)
    public void testDermatoloryWith10000Times() {
        assertThat(faker.disease().dermatolory(), isStringWithContents());
    }
}
