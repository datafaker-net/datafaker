package net.datafaker.videogame;

import net.datafaker.base.ProviderRegistration;

public interface VideoGameProviders extends ProviderRegistration {
    default Battlefield1 battlefield1() {
        return getProvider(Battlefield1.class, Battlefield1::new);
    }

    default ClashOfClans clashOfClans() {
        return getProvider(ClashOfClans.class, ClashOfClans::new);
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

    default LeagueOfLegends leagueOfLegends() {
        return getProvider(LeagueOfLegends.class, LeagueOfLegends::new);
    }

    default SoulKnight soulKnight() {
        return getProvider(SoulKnight.class, SoulKnight::new);
    }

    default StarCraft starCraft() {
        return getProvider(StarCraft.class, StarCraft::new);
    }

    default StarWars starWars() {
        return getProvider(StarWars.class, StarWars::new);
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
