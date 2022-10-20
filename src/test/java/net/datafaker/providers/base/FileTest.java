package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileTest extends BaseFakerTest<BaseFaker> {

    @RepeatedTest(10)
    void extension() {
        assertThat(faker.file().extension())
            .matches("(flac|mp3|wav|bmp|gif|jpeg|jpg|png|tiff|css|csv|html|js|json|txt|mp4|avi|mov|webm|doc|docx|xls|xlsx|ppt|pptx|odt|ods|odp|pages|numbers|key|pdf)");
    }

    @RepeatedTest(10)
    void mimeTypeFormat() {
        assertThat(faker.file().mimeType()).matches(".+/.+");
    }

    @RepeatedTest(10)
    void fileName() {
        assertThat(faker.file().fileName()).matches("([a-z\\-_]+)([\\\\/])([a-z\\-_]+)\\.([a-z0-9]+)");
    }

    @Test
    void fileNameSpecifyExtension() {
        assertThat(faker.file().fileName(null, null, "txt", null))
            .matches("([a-z\\-_]+)([\\\\/])([a-z\\-_]+)\\.txt");
    }

    @Test
    void fileNameSpecifyDir() {
        assertThat(faker.file().fileName("my_dir", null, null, null))
            .matches("my_dir([\\\\/])([a-z\\-_]+)\\.([a-z0-9]+)");
    }

    @Test
    void fileNameSpecifySeparator() {
        assertThat(faker.file().fileName(null, null, null, "\\"))
            .matches("([a-z\\-_]+)\\\\([a-z\\-_]+)\\.([a-z0-9]+)");
    }

    @Test
    void fileNameSpecifyName() {
        assertThat(faker.file().fileName(null, "da_name", null, null))
            .matches("([a-z\\-_]+)([\\\\/])da_name\\.([a-z0-9]+)");
    }
}
