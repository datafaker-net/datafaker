package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ConstructionTest extends BaseFakerTest<BaseFaker> {

    @Test
    public void heavyEquipment() {
        assertThat(faker.construction().heavyEquipment()).isNotEmpty();
    }

    @Test
    public void materials() {
        assertThat(faker.construction().materials()).isNotEmpty();
    }

    @Test
    public void subcontractCategories() {
        assertThat(faker.construction().subcontractCategories()).isNotEmpty();
    }

    @Test
    public void roles() {
        assertThat(faker.construction().roles()).isNotEmpty();
    }

    @Test
    public void trades() {
        assertThat(faker.construction().trades()).isNotEmpty();
    }

    @Test
    public void standardCostCodes() {
        assertThat(faker.construction().standardCostCodes()).isNotEmpty();
    }
}
