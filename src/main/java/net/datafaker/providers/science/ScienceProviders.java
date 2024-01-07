package net.datafaker.providers.science;

import net.datafaker.providers.base.ProviderRegistration;

public interface ScienceProviders extends ProviderRegistration {

    default Planet planet() {
        return getProvider(Planet.class, Planet::new);
    }
}
