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

    public String getExp() {
        return exp;
    }

    public Object getCurrent() {
        return current;
    }

    public ProviderRegistration getRoot() {
        return root;
    }

    public FakerContext getContext() {
        return context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegExpContext that = (RegExpContext) o;
        return Objects.equals(exp, that.exp) && Objects.equals(current, that.current) && Objects.equals(root, that.root) && Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp, current, root, context);
    }
}
