package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class DungeonsAndDragonsTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        DungeonsAndDragons dnd = faker.dungeonsAndDragons();
        return List.of(TestSpec.of(dnd::alignments, "dnd.alignments"),
            TestSpec.of(dnd::backgrounds, "dnd.backgrounds"),
            TestSpec.of(dnd::cities, "dnd.cities"),
            TestSpec.of(dnd::klasses, "dnd.klasses"),
            TestSpec.of(dnd::languages, "dnd.languages"),
            TestSpec.of(dnd::meleeWeapons, "dnd.melee_weapons"),
            TestSpec.of(dnd::monsters, "dnd.monsters"),
            TestSpec.of(dnd::races, "dnd.races"),
            TestSpec.of(dnd::rangedWeapons, "dnd.ranged_weapons"));
    }
}
