package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;

public class EntertainmentFaker extends BaseFaker implements EntertainmentProviders {
    public EntertainmentFaker() {
        super();
    }

    public EntertainmentFaker(Locale locale) {
        super(locale);
    }

    public EntertainmentFaker(Random random) {
        super(random);
    }

    public EntertainmentFaker(Locale locale, Random random) {
        super(locale, random);
    }

    public EntertainmentFaker(Locale locale, RandomService randomService) {
        super(locale, randomService);
    }

    public EntertainmentFaker(FakeValuesService fakeValuesService, FakerContext context) {
        super(fakeValuesService, context);
    }
}
