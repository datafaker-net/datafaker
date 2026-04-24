package net.datafaker.providers.base;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

/**
 *Tormenta is a famous Brazilian RPG created in 1999 by Marcelo Cassaro,
 * Rogério Saladino, and JM Trevisan.
 */

public class Tormenta extends AbstractProvider<BaseProviders> {

    public Tormenta(BaseProviders faker) {
        super(faker);
    }
    public String bestiary(){
        return resolve("tormenta.bestiary");
    }
    public String names(){
        return resolve("tormenta.names");
    }
    public String cities(){
        return resolve("tormenta.cities");
    }

}
