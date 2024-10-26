package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class BloodTypeTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        BloodType bloodType = faker.bloodtype();
        return List.of(TestSpec.of(bloodType::aboTypes, "blood_type.abo_types", "[A-Za-z]+"),
            TestSpec.of(bloodType::rhTypes, "blood_type.rh_types", "[A-Za-z+-]+"),
            TestSpec.of(bloodType::pTypes, "blood_type.p_types", "[A-Za-z\\d]+"),
            TestSpec.of(bloodType::bloodGroup, "blood_type.blood_group", "(A|B|AB|O)[+-]"));
    }
}
