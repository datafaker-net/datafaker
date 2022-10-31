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

    default ElderScrolls elderScrolls() {
        return getProvider(ElderScrolls.class, ElderScrolls::new);
    }

    default Esports esports() {
        return getProvider(Esports.class, Esports::new);
    }

    default Fallout fallout() {
        return getProvider(Fallout.class, Fallout::new);
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

    default MassEffect massEffect() {
        return getProvider(MassEffect.class, MassEffect::new);
    }

    default Overwatch overwatch() {
        return getProvider(Overwatch.class, Overwatch::new);
    }

    default SoulKnight soulKnight() {
        return getProvider(SoulKnight.class, SoulKnight::new);
    }

    default StarCraft starCraft() {
        return getProvider(StarCraft.class, StarCraft::new);
    }

    default SuperMario superMario() {
        return getProvider(SuperMario.class, SuperMario::new);
    }

    default Touhou touhou() {
        return getProvider(Touhou.class, Touhou::new);
    }

    default Zelda zelda() {
        return getProvider(Zelda.class, Zelda::new);
    }
}
