package net.datafaker.providers.food;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;

public class FoodFaker extends BaseFaker implements FoodProviders {
    public FoodFaker() {
        super();
    }

    public FoodFaker(Locale locale) {
        super(locale);
    }

    public FoodFaker(Random random) {
        super(random);
    }

    public FoodFaker(Locale locale, Random random) {
        super(locale, random);
    }

    public FoodFaker(Locale locale, RandomService randomService) {
        super(locale, randomService);
    }

    public FoodFaker(FakeValuesService fakeValuesService, FakerContext context) {
        super(fakeValuesService, context);
    }

    public FoodFaker(FakeValuesService fakeValuesService, FakerContext context, Predicate<Class<?>> whiteListPredicate) {
        super(fakeValuesService, context, whiteListPredicate);
    }
}
