package ner.datafaker;

import net.datafaker.AbstractFakerTest;
import net.datafaker.VideoGameFaker;

public class VideoGameFakerTest extends AbstractFakerTest<VideoGameFaker> {
    protected VideoGameFakerTest() {
        super(new VideoGameFaker());
    }
}
