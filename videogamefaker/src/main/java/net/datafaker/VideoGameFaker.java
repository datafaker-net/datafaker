package net.datafaker;

import net.datafaker.service.FakeValuesService;
import net.datafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;

public class VideoGameFaker extends Faker {
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

    public VideoGameFaker(FakeValuesService fakeValuesService, RandomService random) {
        super(fakeValuesService, random);
    }

    public Battlefield1 battlefield1() {
        return getProvider(Battlefield1.class, () -> new Battlefield1(this));
    }

    public ClashOfClans clashOfClans() {
        return getProvider(ClashOfClans.class, () -> new ClashOfClans(this));
    }

    public ElderScrolls elderScrolls() {
        return getProvider(ElderScrolls.class, () -> new ElderScrolls(this));
    }

    public Esports esports() {
        return getProvider(Esports.class, () -> new Esports(this));
    }

    public Fallout fallout() {
        return getProvider(Fallout.class, () -> new Fallout(this));
    }

    public Hearthstone hearthstone() {
        return getProvider(Hearthstone.class, () -> new Hearthstone(this));
    }

    public LeagueOfLegends leagueOfLegends() {
        return getProvider(LeagueOfLegends.class, () -> new LeagueOfLegends(this));
    }

    public Minecraft minecraft() {
        return getProvider(Minecraft.class, () -> new Minecraft(this));
    }

    public SoulKnight soulKnight() {
        return getProvider(SoulKnight.class, () -> new SoulKnight(this));
    }

    public StarCraft starCraft() {
        return getProvider(StarCraft.class, () -> new StarCraft(this));
    }

    public SuperMario superMario() {
        return getProvider(SuperMario.class, () -> new SuperMario(this));
    }

    public Touhou touhou() {
        return getProvider(Touhou.class, () -> new Touhou(this));
    }

    public Zelda zelda() {
        return getProvider(Zelda.class, () -> new Zelda(this));
    }

}
