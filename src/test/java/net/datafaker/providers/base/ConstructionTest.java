package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConstructionTest extends BaseFakerTest<BaseFaker> {

    @Test
    void heavyEquipment() {
        assertThat(faker.construction().heavyEquipment()).isNotEmpty();
    }

    @Test
    void materials() {
        assertThat(faker.construction().materials()).isNotEmpty();
    }

    @Test
    void subcontractCategories() {
        assertThat(faker.construction().subcontractCategories()).isNotEmpty();
    }

    @Test
    void roles() {
        assertThat(faker.construction().roles()).isNotEmpty();
    }

    @Test
    void trades() {
        assertThat(faker.construction().trades()).isNotEmpty();
    }

    @Test
    void standardCostCodes() {
        assertThat(faker.construction().standardCostCodes()).isNotEmpty();
    }
}
