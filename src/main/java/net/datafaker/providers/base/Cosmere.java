package net.datafaker.providers.base;

/**
 * The cosmere is a fictional shared universe where several of Brandon Sanderson's books take place.
 *
 * @since 1.7.0
 */
public class Cosmere extends AbstractProvider<BaseProviders> {

    protected Cosmere(BaseProviders faker) {
        super(faker);
    }

    public String aons() {
        return resolve("cosmere.aons");
    }

    public String shardWorlds() {
        return resolve("cosmere.shard_worlds");
    }

    public String shards() {
        return resolve("cosmere.shards");
    }

    public String surges() {
        return resolve("cosmere.surges");
    }

    public String knightsRadiant() {
        return resolve("cosmere.knights_radiant");
    }

    public String metals() {
        return resolve("cosmere.metals");
    }

    public String allomancers() {
        return resolve("cosmere.allomancers");
    }

    public String feruchemists() {
        return resolve("cosmere.feruchemists");
    }

    public String heralds() {
        return resolve("cosmere.heralds");
    }

    public String sprens() {
        return resolve("cosmere.sprens");
    }

}
