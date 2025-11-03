package net.datafaker.providers.base;

import java.util.Locale;

public abstract class BaseFakerLocaleTest extends ProviderListLocaleTest<BaseFaker> {
    protected final BaseFaker fakerUA = new BaseFaker(new Locale("uk", "UA"));
    protected final BaseFaker fakerUZ = new BaseFaker(new Locale("uz", "UZ"));
    protected final BaseFaker fakerIE = new BaseFaker(new Locale("en", "IE"));
    protected final BaseFaker fakerCH = new BaseFaker(new Locale("de", "CH"));
    protected final BaseFaker fakerBR = new BaseFaker(new Locale("pt", "BR"));
    protected final BaseFaker fakerDK = new BaseFaker(new Locale("da", "DK"));
    protected final BaseFaker fakerJP = new BaseFaker(new Locale("ja", "JP"));
    protected final BaseFaker fakerBY = new BaseFaker(new Locale("be", "BY"));
    protected final BaseFaker fakerCZ = new BaseFaker(new Locale("cs", "CZ"));
    protected final BaseFaker fakerDE = new BaseFaker(new Locale("de", "DE"));
    protected final BaseFaker fakerHU = new BaseFaker(new Locale("hu", "HU"));
    protected final BaseFaker fakerAM = new BaseFaker(new Locale("hy", "AM"));
    protected final BaseFaker fakerKR = new BaseFaker(new Locale("ko", "KR"));
    protected final BaseFaker fakerPT = new BaseFaker(new Locale("pt", "PT"));
    protected final BaseFaker fakerRU = new BaseFaker(new Locale("ru", "RU"));
    protected final BaseFaker fakerSE = new BaseFaker(new Locale("sv", "SE"));
    protected final BaseFaker fakerTH = new BaseFaker(new Locale("th", "TH"));
    protected final BaseFaker fakerTR = new BaseFaker(new Locale("tr", "TR"));
    protected final BaseFaker fakerCA = new BaseFaker(new Locale("ca", "CA"));
    protected final BaseFaker fakerFR = new BaseFaker(new Locale("fr", "FR"));

    protected final BaseFaker fakerArabic = new BaseFaker(new Locale("ar"));

}
