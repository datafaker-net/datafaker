package net.datafaker.providers.healthcare;

import java.util.Collection;
import java.util.List;

class CareProviderTest extends HealthcareFakerTest {

    private final CareProvider careProvider = getFaker().careProvider();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(careProvider::hospitalName, "healthcare.care_provider.hospital_name"),
            TestSpec.of(careProvider::medicalProfession, "healthcare.care_provider.medical_profession")
        );
    }
}
