package net.datafaker;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FakerDoWithTest {
    @Test
    void overlappingCallsUseTheirOwnLocalesAndSeeds() throws Exception {
        Faker faker = new Faker(Locale.US, new Random(23));
        Sample usual = sample(new Faker(Locale.US, new Random(23)));
        Sample french = sample(new Faker(Locale.FRANCE, new Random(31)));
        Sample japanese = sample(new Faker(Locale.JAPAN, new Random(47)));
        var firstEntered = new CountDownLatch(1);
        var secondEntered = new CountDownLatch(1);
        var releaseSecond = new CountDownLatch(1);
        var executor = Executors.newFixedThreadPool(2);

        try {
            var first = executor.submit(() -> faker.doWith(() -> {
                firstEntered.countDown();
                assertThat(secondEntered.await(10, TimeUnit.SECONDS)).isTrue();
                return sample(faker);
            }, Locale.FRANCE, 31));
            var second = executor.submit(() -> {
                assertThat(firstEntered.await(10, TimeUnit.SECONDS)).isTrue();
                return faker.doWith(() -> {
                    secondEntered.countDown();
                    assertThat(releaseSecond.await(10, TimeUnit.SECONDS)).isTrue();
                    return sample(faker);
                }, Locale.JAPAN, 47);
            });

            assertThat(secondEntered.await(10, TimeUnit.SECONDS)).isTrue();
            assertThat(first.get(10, TimeUnit.SECONDS)).isEqualTo(french);
            assertThat(sample(faker)).isEqualTo(usual);
            releaseSecond.countDown();
            assertThat(second.get(10, TimeUnit.SECONDS)).isEqualTo(japanese);
            assertThat(faker.getContext().getLocale()).isEqualTo(Locale.US);
        } finally {
            releaseSecond.countDown();
            executor.shutdownNow();
        }
    }

    @Test
    void nestedScopesInheritSettingsAndRestoreThemAfterExceptions() {
        Faker faker = new Faker(Locale.US, new Random(23));
        Random expected = new Random(23);
        var context = faker.getContext();
        var random = faker.random();
        var failure = new Exception("callback failed");

        faker.doWith(() -> {
            assertThat(faker.getContext()).isSameAs(context);
            assertThat(context.getLocale()).isEqualTo(Locale.FRANCE);
            assertThat(faker.random()).isSameAs(random);
            assertThat(faker.random().nextInt()).isEqualTo(expected.nextInt());

            assertThatThrownBy(() -> faker.doWith(() -> {
                assertThat(context.getLocale()).isEqualTo(Locale.FRANCE);
                assertThat(faker.random().nextInt()).isEqualTo(new Random(31).nextInt());
                throw failure;
            }, 31)).isInstanceOf(RuntimeException.class).hasCause(failure);

            assertThat(context.getLocale()).isEqualTo(Locale.FRANCE);
            assertThat(faker.random().nextInt()).isEqualTo(expected.nextInt());
            return null;
        }, Locale.FRANCE);

        assertThat(context.getLocale()).isEqualTo(Locale.US);
        assertThat(faker.random()).isSameAs(random);
        assertThat(faker.random().nextInt()).isEqualTo(expected.nextInt());
    }

    @Test
    void combinedScopeLoadsAnUnseenLocale() {
        Faker faker = new Faker(Locale.US);
        Sample expected = sample(new Faker(Locale.JAPAN, new Random(47)));

        assertThat(faker.doWith(() -> sample(faker), Locale.JAPAN, 47)).isEqualTo(expected);
    }

    @Test
    void cachedProvidersUseTheCurrentScopeLocale() {
        Faker faker = new Faker(Locale.US);
        var commerce = faker.commerce();
        var phone = faker.phoneNumber();

        assertThat(faker.doWith(() -> commerce.price(12.5, 12.5), Locale.FRANCE, 31)).isEqualTo("12,50");
        assertThat(faker.doWith(phone::phoneNumberInternational, Locale.FRANCE, 31)).startsWith("+33");
        assertThat(commerce.price(12.5, 12.5)).isEqualTo("12.50");
        assertThat(faker.doWith(phone::phoneNumberInternational, 31)).startsWith("+1");
    }

    @Test
    void providersCreatedInAScopeFollowTheOuterLocaleAfterwards() {
        Faker faker = new Faker(Locale.US);

        faker.doWith(() -> {
            assertThat(faker.commerce().price(12.5, 12.5)).isEqualTo("12,50");
            assertThat(faker.phoneNumber().phoneNumberInternational()).startsWith("+33");
            return null;
        }, Locale.FRANCE, 31);

        assertThat(faker.commerce().price(12.5, 12.5)).isEqualTo("12.50");
        assertThat(faker.doWith(() -> faker.phoneNumber().phoneNumberInternational(), 31)).startsWith("+1");
    }

    private static Sample sample(Faker faker) {
        return new Sample(faker.getContext().getLocale(), faker.name().firstName(), faker.numerify("########"));
    }

    private record Sample(Locale locale, String name, String digits) {
    }
}
