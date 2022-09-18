package net.datafaker;

import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;
import net.datafaker.sport.SportProviders;

import java.util.Locale;
import java.util.Random;

public class Faker extends BaseFaker implements IProviders, SportProviders {
    public Faker() {
    }

    public Faker(Locale locale) {
        super(locale);
    }

    public Faker(Random random) {
        super(random);
    }

    public Faker(Locale locale, Random random) {
        super(locale, random);
    }

    public Faker(Locale locale, RandomService randomService) {
        super(locale, randomService);
    }

    public Faker(FakeValuesService fakeValuesService, FakerContext context) {
        super(fakeValuesService, context);
    }
}
