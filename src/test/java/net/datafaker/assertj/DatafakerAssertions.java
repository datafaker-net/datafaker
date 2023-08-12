package net.datafaker.assertj;

import net.datafaker.assertj.base.NameAssert;
import org.assertj.core.api.Assertions;

public class DatafakerAssertions extends Assertions {

    public static NameAssert assertThat(String actual) {
        return new NameAssert(actual);
    }
}
