package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Case Closed, also known as Detective Conan, is a Japanese detective manga series written and illustrated by Gosho Aoyama.
 *
 * @since 1.7.0
 */
public class DetectiveConan extends AbstractProvider<ShowProviders> {

    protected DetectiveConan(ShowProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("detective_conan.characters");
    }

    public String gadgets() {
        return resolve("detective_conan.gadgets");
    }

    public String vehicles() {
        return resolve("detective_conan.vehicles");
    }

}
