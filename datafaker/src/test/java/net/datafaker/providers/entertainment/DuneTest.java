package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;


class DuneTest extends EntertainmentFakerTest {

    private final Dune dune = getFaker().dune();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(dune::character, "dune.characters"),
            TestSpec.of(dune::title, "dune.titles"),
            TestSpec.of(dune::planet, "dune.planets"),
            TestSpec.of(() -> dune.quote(Dune.Quote.ALIA), "dune.quotes.alia"),
            TestSpec.of(() -> dune.quote(Dune.Quote.DUNCAN), "dune.quotes.duncan"),
            TestSpec.of(() -> dune.quote(Dune.Quote.EMPEROR), "dune.quotes.emperor"),
            TestSpec.of(() -> dune.quote(Dune.Quote.GURNEY), "dune.quotes.gurney"),
            TestSpec.of(() -> dune.quote(Dune.Quote.IRULAN), "dune.quotes.irulan"),
            TestSpec.of(() -> dune.quote(Dune.Quote.LETO), "dune.quotes.leto"),
            TestSpec.of(() -> dune.quote(Dune.Quote.JESSICA), "dune.quotes.jessica"),
            TestSpec.of(() -> dune.quote(Dune.Quote.BARON_HARKONNEN), "dune.quotes.baron_harkonnen"),
            TestSpec.of(() -> dune.quote(Dune.Quote.GUILD_NAVIGATOR), "dune.quotes.guild_navigator"),
            TestSpec.of(() -> dune.quote(Dune.Quote.LIET_KYNES), "dune.quotes.liet_kynes"),
            TestSpec.of(() -> dune.quote(Dune.Quote.MAPES), "dune.quotes.mapes"),
            TestSpec.of(() -> dune.quote(Dune.Quote.MOHIAM), "dune.quotes.mohiam"),
            TestSpec.of(() -> dune.quote(Dune.Quote.PARDOT_KYNES), "dune.quotes.pardot_kynes"),
            TestSpec.of(() -> dune.quote(Dune.Quote.PAUL), "dune.quotes.paul"),
            TestSpec.of(() -> dune.quote(Dune.Quote.PITER), "dune.quotes.piter"),
            TestSpec.of(() -> dune.quote(Dune.Quote.STILGAR), "dune.quotes.stilgar"),
            TestSpec.of(() -> dune.quote(Dune.Quote.THUFIR), "dune.quotes.thufir"),
            TestSpec.of(() -> dune.quote(Dune.Quote.YUEH), "dune.quotes.yueh"),
            TestSpec.of(() -> dune.saying(Dune.Saying.BENE_GESSERIT), "dune.sayings.bene_gesserit"),
            TestSpec.of(() -> dune.saying(Dune.Saying.MENTAT), "dune.sayings.mentat"),
            TestSpec.of(() -> dune.saying(Dune.Saying.FREMEN), "dune.sayings.fremen"),
            TestSpec.of(() -> dune.saying(Dune.Saying.MUADDIB), "dune.sayings.muaddib"),
            TestSpec.of(() -> dune.saying(Dune.Saying.ORANGE_CATHOLIC_BIBLE), "dune.sayings.orange_catholic_bible")
        );
    }
}
