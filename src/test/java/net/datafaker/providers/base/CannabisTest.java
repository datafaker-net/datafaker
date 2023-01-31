package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class CannabisTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.cannabis().strains(), "cannabis.strains"),
                TestSpec.of(() -> faker.cannabis().cannabinoidAbbreviations(), "cannabis.cannabinoid_abbreviations"),
                TestSpec.of(() -> faker.cannabis().cannabinoids(), "cannabis.cannabinoids"),
                TestSpec.of(() -> faker.cannabis().terpenes(), "cannabis.terpenes"),
                TestSpec.of(() -> faker.cannabis().medicalUses(), "cannabis.medical_uses"),
                TestSpec.of(() -> faker.cannabis().healthBenefits(), "cannabis.health_benefits"),
                TestSpec.of(() -> faker.cannabis().categories(), "cannabis.categories"),
                TestSpec.of(() -> faker.cannabis().types(), "cannabis.types"),
                TestSpec.of(() -> faker.cannabis().buzzwords(), "cannabis.buzzwords"),
                TestSpec.of(() -> faker.cannabis().brands(), "cannabis.brands"));
    }

}
