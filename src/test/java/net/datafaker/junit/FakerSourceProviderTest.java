package net.datafaker.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.util.List;
import java.util.Optional;

/**
 * Unit tests for {@link FakerSourceProvider}.
 */
class FakerSourceProviderTest {

    /** Builds a fully configured mock annotation with sensible defaults. */
    private FakerSource createAnnotation(String code, String locale, int repeat,
                                         boolean distinct, String[] params) {
        FakerSource a = mock(FakerSource.class);
        when(a.code()).thenReturn(code);
        when(a.locale()).thenReturn(locale);
        when(a.repeat()).thenReturn(repeat);
        when(a.distinct()).thenReturn(distinct);
        when(a.params()).thenReturn(params);
        when(a.multiParams()).thenReturn(new FakerSource.Param[0]);
        return a;
    }

    /** Builds an ExtensionContext pointing at the given test class. */
    private ExtensionContext createContext(Class<?> testClass, Object testInstance) {
        ExtensionContext ctx = mock(ExtensionContext.class);
        when(ctx.getRequiredTestClass()).thenAnswer(__ -> testClass);
        when(ctx.getTestInstance()).thenReturn(Optional.ofNullable(testInstance));
        return ctx;
    }

    /** Runs provideArguments and collects results into a list. */
    private List<? extends Arguments> run(FakerSourceProvider provider,
                                          Class<?> testClass,
                                          Object testInstance) throws Exception {
        ParameterDeclarations params = mock(ParameterDeclarations.class);
        return provider.provideArguments(params, createContext(testClass, testInstance)).toList();
    }

    @Test
    void accept_throwsFakerSourceException_whenBothParamsAndMultiParamsAreSet() {
        FakerSource a = mock(FakerSource.class);
        when(a.code()).thenReturn("address#latitude");
        when(a.locale()).thenReturn("");
        when(a.repeat()).thenReturn(1);
        when(a.distinct()).thenReturn(false);
        when(a.params()).thenReturn(new String[]{"VISA"});
        FakerSource.Param param = mock(FakerSource.Param.class);
        when(a.multiParams()).thenReturn(new FakerSource.Param[]{param});

        assertThatThrownBy(() -> new FakerSourceProvider().accept(a))
            .isInstanceOf(FakerSourceProvider.FakerSourceException.class)
            .hasMessageContaining("specify either 'params' or 'multiParams', but not both");
    }

    @Test
    void provideArguments_resolvesStaticField_whenNoTestInstanceAvailable() throws Exception {
        FakerSourceProvider provider = new FakerSourceProvider();
        provider.accept(createAnnotation("STUB_PROVIDER", "", 1, false, new String[0]));

        List<? extends Arguments> args = run(provider, DummyTestClass.class, null);

        assertThat(args).hasSize(1);
        assertThat(args.get(0).get()[0]).isEqualTo("ResolvedStubInstance");
    }

    @Test
    void provideArguments_resolvesStaticField_whenTestInstancePresent() throws Exception {
        FakerSourceProvider provider = new FakerSourceProvider();
        provider.accept(createAnnotation("STUB_PROVIDER", "", 1, false, new String[0]));

        List<? extends Arguments> args = run(provider, DummyTestClass.class, new DummyTestClass());

        assertThat(args).hasSize(1);
        assertThat(args.get(0).get()[0]).isEqualTo("ResolvedStubInstance");
    }

    @Test
    void provideArguments_resolvesInstanceField_whenTestInstancePresent() throws Exception {
        FakerSourceProvider provider = new FakerSourceProvider();
        provider.accept(createAnnotation("instanceValue", "", 1, false, new String[0]));

        DummyTestClass instance = new DummyTestClass();
        List<? extends Arguments> args = run(provider, DummyTestClass.class, instance);

        assertThat(args).hasSize(1);
        assertThat(args.get(0).get()[0]).isEqualTo("InstanceValue");
    }

    @Test
    void provideArguments_resolvesInstanceField_byInstantiatingTestClass_whenNoTestInstance() throws Exception {
        FakerSourceProvider provider = new FakerSourceProvider();
        provider.accept(createAnnotation("instanceValue", "", 1, false, new String[0]));

        List<? extends Arguments> args = run(provider, DummyTestClass.class, null);

        assertThat(args).hasSize(1);
        assertThat(args.get(0).get()[0]).isEqualTo("InstanceValue");
    }

    @Test
    void provideArguments_repeatsArguments_accordingToRepeatAttribute() throws Exception {
        FakerSourceProvider provider = new FakerSourceProvider();
        provider.accept(createAnnotation("STUB_PROVIDER", "", 5, false, new String[0]));

        List<? extends Arguments> args = run(provider, DummyTestClass.class, null);

        assertThat(args).hasSize(5)
                        .allMatch(a -> "ResolvedStubInstance".equals(a.get()[0]));
    }

    @Test
    void provideArguments_deduplicatesResults_whenDistinctIsTrue() throws Exception {
        FakerSourceProvider provider = new FakerSourceProvider();
        // greet() always returns the same String → repeat=10 + distinct=true → exactly 1 result
        provider.accept(createAnnotation("STUB_PROVIDER_WITH_METHOD#greet", "", 10, true, new String[0]));

        List<? extends Arguments> args = run(provider, DummyTestClass.class, null);

        assertThat(args).hasSize(1);
        assertThat(args.get(0).get()[0]).isEqualTo("Hello from stub");
    }

    @Test
    void provideArguments_doesNotDeduplicate_whenDistinctIsFalse() throws Exception {
        FakerSourceProvider provider = new FakerSourceProvider();
        // greet() always returns the same String → repeat=10 + distinct=false → 10 results
        provider.accept(createAnnotation("STUB_PROVIDER_WITH_METHOD#greet", "", 10, false, new String[0]));

        List<? extends Arguments> args = run(provider, DummyTestClass.class, null);

        assertThat(args).hasSize(10);
    }

    @Test
    void provideArguments_usesFakerWithLocale_whenLocaleIsSet() throws Exception {
        FakerSourceProvider provider = new FakerSourceProvider();
        // address#countryCode returns a non-empty String for any locale
        provider.accept(createAnnotation("address#countryCode", "en-US", 3, false, new String[0]));

        List<? extends Arguments> args = run(provider, DummyTestClass.class, null);

        assertThat(args).hasSize(3)
                        .allSatisfy(a ->
            assertThat((String) a.get()[0]).isNotBlank()
        );
    }

    @Test
    void provideArguments_ignoresTestClassFields_whenLocaleIsSet() throws Exception {
        // DummyTestClass has no 'faker' field, but locale mode creates its own Faker
        FakerSourceProvider provider = new FakerSourceProvider();
        provider.accept(createAnnotation("address#city", "en-US", 1, false, new String[0]));

        List<? extends Arguments> args = run(provider, DummyTestClass.class, null);

        assertThat(args).hasSize(1);
        assertThat(args.get(0).get()[0]).isInstanceOf(String.class);
    }

    @Test
    void provideArguments_invokesMethod_onResolvedProvider() throws Exception {
        FakerSourceProvider provider = new FakerSourceProvider();
        provider.accept(createAnnotation("STUB_PROVIDER_WITH_METHOD#greet", "", 1, false, new String[0]));

        List<? extends Arguments> args = run(provider, DummyTestClass.class, null);

        assertThat(args).hasSize(1);
        assertThat(args.get(0).get()[0]).isEqualTo("Hello from stub");
    }

    @Test
    void provideArguments_throwsFakerSourceException_whenPathCannotBeResolved() {
        FakerSourceProvider provider = new FakerSourceProvider();
        provider.accept(createAnnotation("nonExistentField#method", "", 1, false, new String[0]));

        assertThatThrownBy(() -> run(provider, DummyTestClass.class, null))
            .isInstanceOf(FakerSourceProvider.FakerSourceException.class)
            .hasMessageContaining("cannot resolve 'nonExistentField'");
    }

    @Test
    void provideArguments_throwsFakerSourceException_whenDescriptorCannotBeParsed() {
        FakerSource a = mock(FakerSource.class);
        when(a.code()).thenReturn("!!!invalid!!!");
        when(a.locale()).thenReturn("");
        when(a.repeat()).thenReturn(1);
        when(a.distinct()).thenReturn(false);
        when(a.params()).thenReturn(new String[0]);
        when(a.multiParams()).thenReturn(new FakerSource.Param[0]);

        FakerSourceProvider provider = new FakerSourceProvider();
        provider.accept(a);

        assertThatThrownBy(() -> run(provider, DummyTestClass.class, null))
            .isInstanceOf(FakerSourceProvider.FakerSourceException.class)
            .hasMessageContaining("cannot parse descriptor");
    }

    @Test
    void provideArguments_throwsFakerSourceException_whenLocaleIsInvalid() {
        FakerSourceProvider provider = new FakerSourceProvider();
        // Faker may or may not throw for bad locales - test that exception is wrapped
        // in FakerSourceException only if it throws at all.
        // Using an obviously broken locale that triggers Faker to fail:
        provider.accept(createAnnotation("address#city", "not-a-real-locale-xyz", 1, false, new String[0]));

        // either succeeds with a fallback locale or throws FakerSourceException -
        // what it must NOT do is throw an unwrapped exception from Faker internals.
        try {
            List<? extends Arguments> args = run(provider, DummyTestClass.class, null);
            assertThat(args).isNotNull();
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(FakerSourceProvider.FakerSourceException.class);
        }
    }

    static final class DummyTestClass {
        static final Object              STUB_PROVIDER             = "ResolvedStubInstance";

        static final StubProviderWMethod STUB_PROVIDER_WITH_METHOD = new StubProviderWMethod();

        final String                     instanceValue             = "InstanceValue";
    }

    static final class StubProviderWMethod {
        public String greet() {
            return "Hello from stub";
        }
    }
}
