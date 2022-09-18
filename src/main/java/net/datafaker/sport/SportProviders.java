package net.datafaker.sport;

import net.datafaker.base.ProviderRegistration;

public interface SportProviders extends ProviderRegistration {
    default Basketball basketball() {
        return getProvider(Basketball.class, Basketball::new);
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
