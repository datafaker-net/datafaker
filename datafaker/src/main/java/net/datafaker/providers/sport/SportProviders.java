package net.datafaker.providers.sport;

import net.datafaker.providers.base.ProviderRegistration;

public interface SportProviders extends ProviderRegistration {
    default Baseball baseball() {
        return getProvider(Baseball.class, Baseball::new);
    }

    default Basketball basketball() {
        return getProvider(Basketball.class, Basketball::new);
    }

    default Chess chess() {
        return getProvider(Chess.class, Chess::new);
    }

    default Cricket cricket() {
        return getProvider(Cricket.class, Cricket::new);
    }

    default EnglandFootBall englandfootball() {
        return getProvider(EnglandFootBall.class, EnglandFootBall::new);
    }

    default Football football() {
        return getProvider(Football.class, Football::new);
    }

    default Formula1 formula1() {
        return getProvider(Formula1.class, Formula1::new);
    }

    default Volleyball volleyball() {
        return getProvider(Volleyball.class, Volleyball::new);
    }

}
