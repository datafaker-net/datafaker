package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Control is an action-adventure game developed by Remedy Entertainment and published by 505 Games.
 *
 * @since 1.7.0
 */
public class Control extends AbstractProvider<VideoGameProviders> {

    protected Control(VideoGameProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("control.character");
    }

    public String location() {
        return resolve("control.location");
    }

    public String objectOfPower() {
        return resolve("control.object_of_power");
    }

    public String alteredItem() {
        return resolve("control.altered_item");
    }

    public String alteredWorldEvent() {
        return resolve("control.altered_world_event");
    }

    public String hiss() {
        return resolve("control.hiss");
    }

    public String theBoard() {
        return resolve("control.the_board");
    }

    public String quote() {
        return resolve("control.quote");
    }

}
