package net.datafaker.providers.science;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;

public class ScienceFaker extends BaseFaker implements ScienceProviders {

    public ScienceFaker() {
    }

    public ScienceFaker(Locale locale) {
        super(locale);
    }

    public ScienceFaker(Random random) {
        super(random);
    }

    public ScienceFaker(Locale locale, Random random) {
        super(locale, random);
    }

    public ScienceFaker(Locale locale, RandomService randomService) {
        super(locale, randomService);
    }

    public ScienceFaker(FakeValuesService fakeValuesService, FakerContext context) {
        super(fakeValuesService, context);
    }
}
