package net.datafaker.providers.food;

import net.datafaker.providers.base.ProviderRegistration;

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

    default IceCream iceCream() {
        return getProvider(IceCream.class, IceCream::new);
    }

    default Tea tea() {
        return getProvider(Tea.class, Tea::new);
    }

}
