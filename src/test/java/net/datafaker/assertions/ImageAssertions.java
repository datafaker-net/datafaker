package net.datafaker.assertions;

import org.assertj.core.api.Assertions;

public class ImageAssertions extends Assertions {
    public static ImageAssert assertThatImage(String imageDataUrl) {
        return assertThat(new ImageData(imageDataUrl));
    }

    public static ImageAssert assertThat(ImageData actual) {
        return new ImageAssert(actual);
    }
}

