package net.datafaker;

public class SportFaker extends Faker {
    public Basketball basketball() {
        return getProvider(Basketball.class, () -> new Basketball(this));
    }

    public EnglandFootBall englandfootball() {
        return getProvider(EnglandFootBall.class, () -> new EnglandFootBall(this));
    }

    public Football football() {
        return getProvider(Football.class, () -> new Football(this));
    }

    public Formula1 formula1() {
        return getProvider(Formula1.class, () -> new Formula1(this));
    }

    public Volleyball volleyball() {
        return getProvider(Volleyball.class, () -> new Volleyball(this));
    }

}
