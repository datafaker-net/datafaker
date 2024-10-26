package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

public class DcComicsTest extends BaseFakerTest<BaseFaker> {

    @Override
protected Collection<TestSpec> providerListTest() { 
        DcComics dcComics = faker.dcComics();
        return List.of(TestSpec.of(dcComics::hero, "dc_comics.hero"),
                TestSpec.of(dcComics::heroine, "dc_comics.heroine"),
                TestSpec.of(dcComics::villain, "dc_comics.villain"),
                TestSpec.of(dcComics::name, "dc_comics.name"),
                TestSpec.of(dcComics::title, "dc_comics.title"));
    }

}
