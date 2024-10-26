package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class CannabisTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Cannabis cannabis = faker.cannabis();
        return List.of(TestSpec.of(cannabis::strains, "cannabis.strains"),
                TestSpec.of(cannabis::cannabinoidAbbreviations, "cannabis.cannabinoid_abbreviations"),
                TestSpec.of(cannabis::cannabinoids, "cannabis.cannabinoids"),
                TestSpec.of(cannabis::terpenes, "cannabis.terpenes"),
                TestSpec.of(cannabis::medicalUses, "cannabis.medical_uses"),
                TestSpec.of(cannabis::healthBenefits, "cannabis.health_benefits"),
                TestSpec.of(cannabis::categories, "cannabis.categories"),
                TestSpec.of(cannabis::types, "cannabis.types"),
                TestSpec.of(cannabis::buzzwords, "cannabis.buzzwords"),
                TestSpec.of(cannabis::brands, "cannabis.brands"));
    }

}
