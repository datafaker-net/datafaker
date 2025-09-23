package net.datafaker.providers.videogame;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;

public class VideoGameFaker extends BaseFaker implements VideoGameProviders {
    public VideoGameFaker() {
        super();
    }

    public VideoGameFaker(Locale locale) {
        super(locale);
    }

    public VideoGameFaker(Random random) {
        super(random);
    }

    public VideoGameFaker(Locale locale, Random random) {
        super(locale, random);
    }

    public VideoGameFaker(Locale locale, RandomService randomService) {
        super(locale, randomService);
    }

    public VideoGameFaker(FakeValuesService fakeValuesService, FakerContext context) {
        super(fakeValuesService, context);
    }

    public VideoGameFaker(FakeValuesService fakeValuesService, FakerContext context, Predicate<Class<?>> whiteListPredicate) {
        super(fakeValuesService, context, whiteListPredicate);
    }
}
