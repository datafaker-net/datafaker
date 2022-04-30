package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileTest extends AbstractFakerTest {

    @RepeatedTest(10)
    void testExtension() {
        assertThat(faker.file().extension())
            .matches("(flac|mp3|wav|bmp|gif|jpeg|jpg|png|tiff|css|csv|html|js|json|txt|mp4|avi|mov|webm|doc|docx|xls|xlsx|ppt|pptx|odt|ods|odp|pages|numbers|key|pdf)");
    }

    @RepeatedTest(10)
    void testMimeTypeFormat() {
        assertThat(faker.file().mimeType()).matches(".+/.+");
    }

    @RepeatedTest(10)
    void testFileName() {
        assertThat(faker.file().fileName()).matches("([a-z\\-_]+)([\\\\/])([a-z\\-_]+)\\.([a-z0-9]+)");
    }

    @Test
    void testFileNameSpecifyExtension() {
        assertThat(faker.file().fileName(null, null, "txt", null))
            .matches("([a-z\\-_]+)([\\\\/])([a-z\\-_]+)\\.txt");
    }

    @Test
    void testFileNameSpecifyDir() {
        assertThat(faker.file().fileName("my_dir", null, null, null))
            .matches("my_dir([\\\\/])([a-z\\-_]+)\\.([a-z0-9]+)");
    }

    @Test
    void testFileNameSpecifySeparator() {
        assertThat(faker.file().fileName(null, null, null, "\\"))
            .matches("([a-z\\-_]+)\\\\([a-z\\-_]+)\\.([a-z0-9]+)");
    }

    @Test
    void testFileNameSpecifyName() {
        assertThat(faker.file().fileName(null, "da_name", null, null))
            .matches("([a-z\\-_]+)([\\\\/])da_name\\.([a-z0-9]+)");
    }
}
