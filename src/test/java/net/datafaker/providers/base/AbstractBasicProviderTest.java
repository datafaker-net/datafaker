package net.datafaker.providers.base;



import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractBasicProviderTest<T extends BaseFaker> extends BaseFakerTest<BaseFaker> {

}
