package net.datafaker.providers.healthcare;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static net.datafaker.providers.healthcare.Disease.DiseaseType.DERMATOLOGY_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.GYNECOLOGY_AND_OBSTETRICS_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.INTERNAL_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.NEUROLOGICAL_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.OPHTHALMOLOGY_AND_OTORHINOLARYNGOLOGY_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.PAEDIATRIC_DISEASE_KEY;
import static net.datafaker.providers.healthcare.Disease.DiseaseType.SURGICAL_DISEASE_KEY;
import static org.assertj.core.api.Assertions.assertThat;

class DiseaseTest extends HealthcareFakerTest {

    private final Disease disease = faker.disease();

    private final Set<String> allDiseases = Arrays.stream(Disease.DiseaseType.values())
        .map((Disease.DiseaseType diseaseType) -> getBaseList(diseaseType.yamlKey))
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(disease::internalDisease, INTERNAL_DISEASE_KEY.yamlKey),
            TestSpec.of(disease::neurology, NEUROLOGICAL_DISEASE_KEY.yamlKey),
            TestSpec.of(disease::surgery, SURGICAL_DISEASE_KEY.yamlKey),
            TestSpec.of(disease::paediatrics, PAEDIATRIC_DISEASE_KEY.yamlKey),
            TestSpec.of(disease::gynecologyAndObstetrics, GYNECOLOGY_AND_OBSTETRICS_DISEASE_KEY.yamlKey),
            TestSpec.of(disease::ophthalmologyAndOtorhinolaryngology, OPHTHALMOLOGY_AND_OTORHINOLARYNGOLOGY_DISEASE_KEY.yamlKey),
            TestSpec.of(disease::dermatology, DERMATOLOGY_DISEASE_KEY.yamlKey));
    }

    @RepeatedTest(100)
    void testAnyDisease() {
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
