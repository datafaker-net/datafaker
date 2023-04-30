package net.datafaker.service;

import net.datafaker.providers.base.ProviderRegistration;

import java.util.Objects;

public class RegExpContext {
    private final String exp;
    private final Object current;
    private final ProviderRegistration root;
    private final FakerContext context;

    private RegExpContext(String exp, Object current, ProviderRegistration root, FakerContext context) {
        this.exp = exp;
        this.current = current;
        this.root = root;
        this.context = context;
    }

    public static RegExpContext of(String exp, Object current, ProviderRegistration root, FakerContext context) {
        return new RegExpContext(exp, current, root, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegExpContext that = (RegExpContext) o;

        if (!Objects.equals(exp, that.exp)) return false;
        if (!Objects.equals(current, that.current)) return false;
        if (!Objects.equals(root, that.root)) return false;
        return Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        int result = exp != null ? exp.hashCode() : 0;
        result = 31 * result + (context != null ? context.hashCode() : 0);
        return result;
    }
}
