package net.datafaker;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.entertainment.EntertainmentProviders;
import net.datafaker.providers.food.FoodProviders;
import net.datafaker.providers.healthcare.HealthcareProviders;
import net.datafaker.providers.sport.SportProviders;
import net.datafaker.providers.videogame.VideoGameProviders;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;

public class Faker extends BaseFaker implements BaseProviders, SportProviders, FoodProviders, EntertainmentProviders, VideoGameProviders, HealthcareProviders {
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

    public Faker(FakeValuesService fakeValuesService, FakerContext context, Predicate<Class<?>> whiteListPredicate) {
        super(fakeValuesService, context, whiteListPredicate);
    }

    /**
     * Creates a {@link Faker} backed by a shared {@link FakeValuesService}.
     * Use in multi-threaded scenarios where all threads share one service per locale
     * and each supplies its own {@link Random} to avoid redundant YAML loading.
     *
     * @param sharedService a pre-initialized service, e.g. from {@link FakeValuesService#getShared(Locale)}
     * @param locale        locale for this Faker instance
     * @param random        per-thread random source
     */
    public static Faker withSharedService(FakeValuesService sharedService, Locale locale, Random random) {
        return new Faker(sharedService, new FakerContext(locale, new RandomService(random)));
    }
}
