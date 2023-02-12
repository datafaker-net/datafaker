
package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class KpopTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Kpop kpop = faker.kpop();
        return List.of(TestSpec.of(kpop::iGroups, "kpop.i_groups"),
                TestSpec.of(kpop::iiGroups, "kpop.ii_groups"),
                TestSpec.of(kpop::iiiGroups, "kpop.iii_groups"),
                TestSpec.of(kpop::girlGroups, "kpop.girl_groups"),
                TestSpec.of(kpop::boyBands, "kpop.boy_bands"),
                TestSpec.of(kpop::solo, "kpop.solo"));
    }
}
