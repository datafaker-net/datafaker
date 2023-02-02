
package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class KpopTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.kpop().iGroups(), "kpop.i_groups"),
                TestSpec.of(() -> faker.kpop().iiGroups(), "kpop.ii_groups"),
                TestSpec.of(() -> faker.kpop().iiiGroups(), "kpop.iii_groups"),
                TestSpec.of(() -> faker.kpop().girlGroups(), "kpop.girl_groups"),
                TestSpec.of(() -> faker.kpop().boyBands(), "kpop.boy_bands"),
                TestSpec.of(() -> faker.kpop().solo(), "kpop.solo"));
    }
}
