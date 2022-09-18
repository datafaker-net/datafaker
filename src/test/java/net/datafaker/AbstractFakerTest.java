package net.datafaker;

public class AbstractFakerTest extends AbstractBaseFakerTest<Faker> {
    protected Faker getFaker() {
        return new Faker();
    }
}
