package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class CredentialsListTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(faker.credentials()::weakPassword, "credentials.weak_password"));
    }
}
