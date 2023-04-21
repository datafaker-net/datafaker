package net.datafaker.providers.videogame;

import net.datafaker.providers.base.ProviderRegistration;

public interface VideoGameProviders extends ProviderRegistration {
    default Battlefield1 battlefield1() {
        return getProvider(Battlefield1.class, Battlefield1::new);
    }

    default ClashOfClans clashOfClans() {
        return getProvider(ClashOfClans.class, ClashOfClans::new);
    }

    default Control control() {
        return getProvider(Control.class, Control::new);
    }

    default DarkSouls darkSouls() {
        return getProvider(DarkSouls.class, DarkSouls::new);
    }

    default EldenRing eldenRing() {
        return getProvider(EldenRing.class, EldenRing::new);
    }

    default ElderScrolls elderScrolls() {
        return getProvider(ElderScrolls.class, ElderScrolls::new);
    }

    default Esports esports() {
        return getProvider(Esports.class, Esports::new);
    }

    default Fallout fallout() {
        return getProvider(Fallout.class, Fallout::new);
    }

    default FinalFantasyXIV finalFantasyXIV() {
        return getProvider(FinalFantasyXIV.class, FinalFantasyXIV::new);
    }

    default HalfLife halfLife() {
        return getProvider(HalfLife.class, HalfLife::new);
    }

    default Hearthstone hearthstone() {
        return getProvider(Hearthstone.class, Hearthstone::new);
    }

    default HeroesOfTheStorm heroesOfTheStorm() {
        return getProvider(HeroesOfTheStorm.class, HeroesOfTheStorm::new);
    }

    default LeagueOfLegends leagueOfLegends() {
        return getProvider(LeagueOfLegends.class, LeagueOfLegends::new);
    }

    default MarvelSnap marvelSnap() {
        return getProvider(MarvelSnap.class, MarvelSnap::new);
    }

    default MassEffect massEffect() {
        return getProvider(MassEffect.class, MassEffect::new);
    }

    default Minecraft minecraft() {
        return getProvider(Minecraft.class, Minecraft::new);
    }

    default Myst myst() {
        return getProvider(Myst.class, Myst::new);
    }

    default Overwatch overwatch() {
        return getProvider(Overwatch.class, Overwatch::new);
    }

    default RedDeadRedemption2 redDeadRedemption2() {
        return getProvider(RedDeadRedemption2.class, RedDeadRedemption2::new);
    }

    default SonicTheHedgehog sonicTheHedgehog() {
        return getProvider(SonicTheHedgehog.class, SonicTheHedgehog::new);
    }

    default SoulKnight soulKnight() {
        return getProvider(SoulKnight.class, SoulKnight::new);
    }

    default StarCraft starCraft() {
        return getProvider(StarCraft.class, StarCraft::new);
    }

    default StreetFighter streetFighter() {
        return getProvider(StreetFighter.class, StreetFighter::new);
    }

    default SuperMario superMario() {
        return getProvider(SuperMario.class, SuperMario::new);
    }

    default SuperSmashBros superSmashBros() {
        return getProvider(SuperSmashBros.class, SuperSmashBros::new);
    }

    default Touhou touhou() {
        return getProvider(Touhou.class, Touhou::new);
    }

    default VideoGame videoGame() {
        return getProvider(VideoGame.class, VideoGame::new);
    }

    default WarhammerFantasy warhammerFantasy() {
        return getProvider(WarhammerFantasy.class, WarhammerFantasy::new);
    }

    default WorldOfWarcraft worldOfWarcraft() {
        return getProvider(WorldOfWarcraft.class, WorldOfWarcraft::new);
    }

    default Zelda zelda() {
        return getProvider(Zelda.class, Zelda::new);
    }
}
