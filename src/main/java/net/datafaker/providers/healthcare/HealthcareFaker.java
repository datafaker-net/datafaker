package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.service.FakeValuesService;
import net.datafaker.service.FakerContext;
import net.datafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;

/**
 * @since 2.3.0
 */
public class HealthcareFaker extends BaseFaker implements HealthcareProviders {
    public HealthcareFaker() {
        super();
    }

    public HealthcareFaker(Locale locale) {
        super(locale);
    }

    public HealthcareFaker(Random random) {
        super(random);
    }

    public HealthcareFaker(Locale locale, RandomService randomService) {
        super(locale, randomService);
    }

    public HealthcareFaker(FakeValuesService fakeValuesService, FakerContext context) {
        super(fakeValuesService, context);
    }

    public HealthcareFaker(FakeValuesService fakeValuesService, FakerContext context, Predicate<Class<?>> whiteListPredicate) {
        super(fakeValuesService, context, whiteListPredicate);
    }
}
