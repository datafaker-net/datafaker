package net.datafaker.providers.base;


/**
 * @since 0.8.0
 */
public class Relationship extends AbstractProvider<BaseProviders> {

    private enum Kind {
        direct("relationship.familial.direct"),
        extended("relationship.familial.extended"),
        inLaw("relationship.in_law"),
        spouse("relationship.spouse"),
        parent("relationship.parent"),
        sibling("relationship.sibling");

        private final String expression;
        Kind(String expression) {
            this.expression = expression;
        }
    }

    protected Relationship(final BaseProviders faker) {
        super(faker);
    }

    public String direct() {
        return resolve(Kind.direct.expression);
    }

    public String extended() {
        return resolve(Kind.extended.expression);
    }

    public String inLaw() {
        return resolve(Kind.inLaw.expression);
    }

    public String spouse() {
        return resolve(Kind.spouse.expression);
    }

    public String parent() {
        return resolve(Kind.parent.expression);
    }

    public String sibling() {
        return resolve(Kind.sibling.expression);
    }

    public String any() {
        Kind kind = faker.options().option(Kind.class);
        return resolve(kind.expression);
    }

}
