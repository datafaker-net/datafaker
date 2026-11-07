package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class SipListTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Sip sip = faker.sip();
        return List.of(
            TestSpec.of(sip::method, "sip.methods"),
            TestSpec.of(sip::contentType, "sip.content.types"),
            TestSpec.of(sip::provisionalResponsePhrase, "sip.response.phrases.provisional"),
            TestSpec.of(sip::successResponsePhrase, "sip.response.phrases.success"),
            TestSpec.of(sip::redirectResponsePhrase, "sip.response.phrases.redirection"));
    }
}
