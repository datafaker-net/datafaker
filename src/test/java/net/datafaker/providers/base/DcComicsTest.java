package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

public class DcComicsTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.dcComics().hero(), "dc_comics.hero"),
                TestSpec.of(() -> faker.dcComics().heroine(), "dc_comics.heroine"),
                TestSpec.of(() -> faker.dcComics().villain(), "dc_comics.villain"),
                TestSpec.of(() -> faker.dcComics().name(), "dc_comics.name"),
                TestSpec.of(() -> faker.dcComics().title(), "dc_comics.title"));
    }

}
