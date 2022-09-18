package net.datafaker.food;

import net.datafaker.base.ProviderRegistration;

public interface FoodProviders extends ProviderRegistration {
    default Beer beer() {
        return getProvider(Beer.class, Beer::new);
    }

    default Coffee coffee() {
        return getProvider(Coffee.class, Coffee::new);
    }

    default Dessert dessert() {
        return getProvider(Dessert.class, Dessert::new);
    }

    default Food food() {
        return getProvider(Food.class, Food::new);
    }

    default Tea tea() {
        return getProvider(Tea.class, Tea::new);
    }

}
