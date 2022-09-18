package net.datafaker.movie;

import net.datafaker.base.BaseFaker;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;

public class MovieFaker extends BaseFaker implements MovieProviders {
    public MovieFaker() {
    }

    public MovieFaker(Locale locale) {
        super(locale);
    }

    public MovieFaker(Random random) {
        super(random);
    }

    public MovieFaker(Locale locale, Random random) {
        super(locale, random);
    }

    public MovieFaker(Locale locale, RandomService randomService) {
        super(locale, randomService);
    }

    public MovieFaker(FakeValuesService fakeValuesService, FakerContext context) {
        super(fakeValuesService, context);
    }
}
