package net.datafaker.providers.sport;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;

public class SportFaker extends BaseFaker implements SportProviders {
    public SportFaker() {
        super();
    }

    public SportFaker(Locale locale) {
        super(locale);
    }

    public SportFaker(Random random) {
        super(random);
    }

    public SportFaker(Locale locale, Random random) {
        super(locale, random);
    }

    public SportFaker(Locale locale, RandomService randomService) {
        super(locale, randomService);
    }

    public SportFaker(FakeValuesService fakeValuesService, FakerContext context) {
        super(fakeValuesService, context);
    }

    public SportFaker(FakeValuesService fakeValuesService, FakerContext context, Predicate<Class<?>> whiteListPredicate) {
        super(fakeValuesService, context, whiteListPredicate);
    }
}
