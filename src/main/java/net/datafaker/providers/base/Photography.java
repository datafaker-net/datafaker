package net.datafaker.providers.base;

/**
 * Provides photography related strings.
 *
 * @since 0.8.0
 */
public class Photography extends AbstractProvider<BaseProviders> {

    protected Photography(BaseProviders faker) {
        super(faker);
    }

    /**
     * @return a photography term.
     */
    public String term() {
        return resolve("photography.term");
    }

    /**
     * @return a photography brand.
     */
    public String brand() {
        return resolve("photography.brand");
    }

    /**
     * @return a name of camera model/make.
     */
    public String camera() {
        return resolve("photography.camera");
    }

    /**
     * @return some lens description like 500mm/8.
     */
    public String lens() {
        return resolve("photography.lens");
    }

    /**
     * @return a photography genre.
     */
    public String genre() {
        return resolve("photography.genre");
    }

    /**
     * @return some string to tag an image.
     */
    public String imageTag() {
        return resolve("photography.imagetag");
    }

    /**
     * @return some aperture description like f/1.4 .
     */
    public String aperture() {
        return resolve("photography.aperture");
    }

    /**
     * @return some shutter description like 1/25 .
     */
    public String shutter() {
        return resolve("photography.shutter");
    }

    /**
     * @return some ISO value like 3200.
     */
    public String iso() {
        return resolve("photography.iso");
    }
}
