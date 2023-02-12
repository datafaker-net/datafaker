package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class TronTest extends EntertainmentFakerTest {

    private final Tron tron = getFaker().tron();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(() -> tron.alternateCharacterSpelling(Tron.AlternateCharacterSpelling.ALAN_BRADLEY), "tron.alternate_character_spellings.alan_bradley"),
            TestSpec.of(() -> tron.alternateCharacterSpelling(Tron.AlternateCharacterSpelling.CLU), "tron.alternate_character_spellings.clu"),
            TestSpec.of(() -> tron.alternateCharacterSpelling(Tron.AlternateCharacterSpelling.DR_LORA_BAINES), "tron.alternate_character_spellings.dr_lora_baines"),
            TestSpec.of(() -> tron.alternateCharacterSpelling(Tron.AlternateCharacterSpelling.DR_WALTER_GIBBS), "tron.alternate_character_spellings.dr_walter_gibbs"),
            TestSpec.of(() -> tron.alternateCharacterSpelling(Tron.AlternateCharacterSpelling.ED_DILLINGER), "tron.alternate_character_spellings.ed_dillinger"),
            TestSpec.of(() -> tron.alternateCharacterSpelling(Tron.AlternateCharacterSpelling.KEVIN_FLYNN), "tron.alternate_character_spellings.kevin_flynn"),
            TestSpec.of(() -> tron.alternateCharacterSpelling(Tron.AlternateCharacterSpelling.MCP), "tron.alternate_character_spellings.mcp"),
            TestSpec.of(() -> tron.alternateCharacterSpelling(Tron.AlternateCharacterSpelling.ROY_KLEINBERG), "tron.alternate_character_spellings.roy_kleinberg"),
            TestSpec.of(() -> tron.character(Tron.Character.OTHER), "tron.characters.other"),
            TestSpec.of(() -> tron.character(Tron.Character.PROGRAM), "tron.characters.programs"),
            TestSpec.of(() -> tron.character(Tron.Character.USER), "tron.characters.users"),
            TestSpec.of(tron::game, "tron.games"),
            TestSpec.of(tron::location, "tron.locations"),
            TestSpec.of(() -> tron.quote(Tron.Quote.ALAN_BRADLEY), "tron.quotes.alan_bradley"),
            TestSpec.of(() -> tron.quote(Tron.Quote.BIT), "tron.quotes.bit"),
            TestSpec.of(() -> tron.quote(Tron.Quote.CLU), "tron.quotes.clu"),
            TestSpec.of(() -> tron.quote(Tron.Quote.CROM), "tron.quotes.crom"),
            TestSpec.of(() -> tron.quote(Tron.Quote.DR_LORA_BAINES), "tron.quotes.dr_lora_baines"),
            TestSpec.of(() -> tron.quote(Tron.Quote.DR_WALTER_GIBBS), "tron.quotes.dr_walter_gibbs"),
            TestSpec.of(() -> tron.quote(Tron.Quote.DUMONT), "tron.quotes.dumont"),
            TestSpec.of(() -> tron.quote(Tron.Quote.ED_DILLINGER), "tron.quotes.ed_dillinger"),
            TestSpec.of(() -> tron.quote(Tron.Quote.KEVIN_FLYNN), "tron.quotes.kevin_flynn"),
            TestSpec.of(() -> tron.quote(Tron.Quote.MCP), "tron.quotes.mcp"),
            TestSpec.of(() -> tron.quote(Tron.Quote.PROGRAM), "tron.quotes.program"),
            TestSpec.of(() -> tron.quote(Tron.Quote.RAM), "tron.quotes.ram"),
            TestSpec.of(() -> tron.quote(Tron.Quote.SARK), "tron.quotes.sark"),
            TestSpec.of(() -> tron.quote(Tron.Quote.TRON), "tron.quotes.tron"),
            TestSpec.of(() -> tron.quote(Tron.Quote.YORI), "tron.quotes.yori"),
            TestSpec.of(tron::tagline, "tron.taglines"),
            TestSpec.of(tron::vehicle, "tron.vehicles")
        );
    }
}
