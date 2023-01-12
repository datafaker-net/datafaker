package net.datafaker.providers.show;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;

public class ShowFaker extends BaseFaker implements ShowProviders {
    public ShowFaker() {
    }

    public ShowFaker(Locale locale) {
        super(locale);
    }

    public ShowFaker(Random random) {
        super(random);
    }

    public ShowFaker(Locale locale, Random random) {
        super(locale, random);
    }

    public ShowFaker(Locale locale, RandomService randomService) {
        super(locale, randomService);
    }

    public ShowFaker(FakeValuesService fakeValuesService, FakerContext context) {
        super(fakeValuesService, context);
    }
}
