package net.datafaker.providers.base;

import java.util.Locale;

public abstract class BaseFakerLocaleTest extends ProviderListLocaleTest<BaseFaker> {
    protected final BaseFaker fakerUA = new BaseFaker(new Locale("uk", "UA"));
    protected final BaseFaker fakerUZ = new BaseFaker(new Locale("uz", "UZ"));
    protected final BaseFaker fakerIE = new BaseFaker(new Locale("en", "IE"));
}
