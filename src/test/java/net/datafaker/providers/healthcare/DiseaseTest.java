package net.datafaker.providers.healthcare;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.datafaker.providers.healthcare.Disease.DERMATOLOGY_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.GYNECOLOGY_AND_OBSTETRICS_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.INTERNAL_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.NEUROLOGICAL_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.OPHTHALMOLOGY_AND_OTORHINOLARYNGOLOGY_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.PAEDIATRIC_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.SURGICAL_DISEASE_KEY;
import static org.assertj.core.api.Assertions.assertThat;

class DiseaseTest extends HealthcareFakerTest {

    private final Disease disease = faker.disease();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(disease::internalDisease, INTERNAL_DISEASE_KEY),
            TestSpec.of(disease::neurology, NEUROLOGICAL_DISEASE_KEY),
            TestSpec.of(disease::surgery, SURGICAL_DISEASE_KEY),
            TestSpec.of(disease::paediatrics, PAEDIATRIC_DISEASE_KEY),
            TestSpec.of(disease::gynecologyAndObstetrics, GYNECOLOGY_AND_OBSTETRICS_DISEASE_KEY),
            TestSpec.of(disease::ophthalmologyAndOtorhinolaryngology, OPHTHALMOLOGY_AND_OTORHINOLARYNGOLOGY_DISEASE_KEY),
            TestSpec.of(disease::dermatology, DERMATOLOGY_DISEASE_KEY));
    }

    @RepeatedTest(100)
    void testAnyDisease() {
        // given
        Set<String> allDiseases = Stream.of(INTERNAL_DISEASE_KEY, NEUROLOGICAL_DISEASE_KEY, SURGICAL_DISEASE_KEY, PAEDIATRIC_DISEASE_KEY, GYNECOLOGY_AND_OBSTETRICS_DISEASE_KEY, OPHTHALMOLOGY_AND_OTORHINOLARYNGOLOGY_DISEASE_KEY, DERMATOLOGY_DISEASE_KEY)
            .map(this::getBaseList)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

        // when
        String anyDisease = disease.anyDisease();

        // then
        assertThat(anyDisease).as("Any disease should use existing providers combined")
            .isNotBlank()
            .isIn(allDiseases);
    }


    @RepeatedTest(100)
    void testDiseaseCodes() {
        String diseaseCode = disease.icd10();
        assertThat(diseaseCode).matches("^[A-Z][0-9]{1,2}(\\.[0-9])?$");
    }
}
