package net.datafaker;

import org.apache.commons.validator.routines.EmailValidator;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.fail;

class InternetTest extends AbstractFakerTest {

    @Test
    void testEmailAddress() {
        String emailAddress = faker.internet().emailAddress();
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
    }

    @Test
    void testEmailAddressWithLocalPartParameter() {
        String emailAddress = faker.internet().emailAddress("john");
        assertThat(emailAddress).startsWith("john@");
        assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
    }

    @Test
    void testSafeEmailAddress() {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String emailAddress = faker.internet().safeEmailAddress();
            assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
            emails.add(emailAddress);
        }
        final String safeDomain = faker.resolve("internet.safe_email");

        assertThat(emails.stream().filter(t -> t.endsWith("@" + safeDomain)).collect(Collectors.toList()))
            .isNotEmpty();
    }

    @Test
    void testSafeEmailAddressWithLocalPartParameter() {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String emailAddress = faker.internet().safeEmailAddress("john");
            assertThat(emailAddress).startsWith("john@");
            assertThat(EmailValidator.getInstance().isValid(emailAddress)).isTrue();
            emails.add(emailAddress);
        }
        final String safeDomain = faker.resolve("internet.safe_email");

        assertThat(emails.stream().filter(t -> t.endsWith("@" + safeDomain)).collect(Collectors.toList()))
            .isNotEmpty();
    }

    @Test
    void testEmailAddressDoesNotIncludeAccentsInTheLocalPart() {
        String emailAddress = faker.internet().emailAddress("áéíóú");
        assertThat(emailAddress).startsWith("aeiou@");
    }

    @Test
    void testSafeEmailAddressDoesNotIncludeAccentsInTheLocalPart() {
        String emailAddress = faker.internet().safeEmailAddress("áéíóú");
        assertThat(emailAddress).startsWith("aeiou@");
    }

    @Test
    void testUrl() {
        assertThat(faker.internet().url()).matches("www\\.(\\w|-)+\\.\\w+");
    }

    @Test
    void testAvatar() {
        assertThat(faker.internet().avatar()).matches("http.*/[^/]+/128.jpg$");
    }

    @Test
    void testImage() {
        String imageUrl = faker.internet().image();
        assertThat(imageUrl).matches("^https://lorempixel\\.com(/g)?/\\d{1,4}/\\d{1,4}/\\w+/$");
    }

    @Test
    void testDomainName() {
        assertThat(faker.internet().domainName()).matches("[a-z]+\\.\\w{2,4}");
    }

    @Test
    void testDomainWord() {
        assertThat(faker.internet().domainWord()).matches("[a-z]+");
    }

    @Test
    void testDomainSuffix() {
        assertThat(faker.internet().domainSuffix()).matches("\\w{2,4}");
    }

    @Test
    void testImageWithExplicitParams() {
        String imageUrl = faker.internet().image(800, 600, false, "bugs");
        assertThat(imageUrl).matches("^https://lorempixel\\.com/800/600/\\w+/bugs$");
    }

    @Test
    void testPassword() {
        assertThat(faker.internet().password()).matches("[a-z\\d]{8,16}");
    }

    @Test
    void testPasswordWithFixedLength() {
        String password = Faker.instance().internet().password(32, 32, true, true, true);
        assertThat(password).hasSize(32);
    }

    @Test
    void testPasswordIncludeDigit() {
        assertThat(faker.internet().password()).matches("[a-z\\d]{8,16}");
        assertThat(faker.internet().password(false)).matches("[a-z]{8,16}");
    }

    @Test
    void testPasswordMinLengthMaxLength() {
        assertThat(faker.internet().password(10, 25)).matches("[a-z\\d]{10,25}");
    }

    @Test
    void testPasswordMinLengthMaxLengthIncludeUpperCase() {
        assertThat(faker.internet().password(1, 2, false)).matches("[a-z\\d]{1,2}");
        assertThat(faker.internet().password(10, 25, true)).matches("[a-zA-Z\\d]{10,25}");
    }

    @Test
    void testPasswordMinLengthMaxLengthIncludeUpperCaseIncludeSpecial() {
        assertThat(faker.internet().password(10, 25, false, false)).matches("[a-z\\d]{10,25}");
        assertThat(faker.internet().password(10, 25, false, true)).matches("[a-z\\d!@#$%^&*]{10,25}");
        assertThat(faker.internet().password(10, 25, true, true)).matches("[a-zA-Z\\d!@#$%^&*]{10,25}");
    }

    @Test
    void shouldGenerateAPasswordWithMinAndMaxLength() {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            results.add(faker.internet().password(1, 10));
        }

        final List<String> min = results.stream().filter(x -> x.length() == 1).collect(Collectors.toList());
        final List<String> max = results.stream().filter(x -> x.length() == 10).collect(Collectors.toList());

        assertThat(min.size()).isPositive();
        assertThat(max.size()).isPositive();
    }

    @Test
    void testPasswordMinLengthMaxLengthIncludeUpperCaseIncludeSpecialIncludeDigit() {
        assertThat(faker.internet().password(10, 25, false, false, false)).matches("[a-z]{10,25}");
        assertThat(faker.internet().password(10, 25, false, true, true)).matches("[a-z\\d!@#$%^&*]{10,25}");
        assertThat(faker.internet().password(10, 25, true, true, false)).matches("[a-zA-Z!@#$%^&*]{10,25}");
        assertThat(faker.internet().password(10, 25, true, true, true)).matches("[a-zA-Z\\d!@#$%^&*]{10,25}");
    }

    private Condition<String> getCharacterCondition(char c, int expectedCnt) {
        return new Condition<>(s -> {
            int cnt = 0;
            if (s.length() < expectedCnt) {
                return false;
            }
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == c) {
                    cnt++;
                }
                if (cnt > expectedCnt) {
                    return false;
                }
            }
            return cnt == expectedCnt;
        }, "Expect " + expectedCnt + " colons");
    }

    @Test
    void testMacAddress() {
        Condition<String> colon = getCharacterCondition(':', 5);
        assertThat(faker.internet().macAddress()).is(colon);
        assertThat(faker.internet().macAddress("")).is(colon);

        assertThat(faker.internet().macAddress("fa:fa:fa"))
            .startsWith("fa:fa:fa")
            .is(colon);

        assertThat(faker.internet().macAddress("01:02"))
            .startsWith("01:02")
            .is(colon);

        // loop through 1000 times just to 'run it through the wringer'
        for (int i = 0; i < 1000; i++) {
            assertThat(faker.internet().macAddress()).matches("[0-9a-fA-F]{2}(:([0-9a-fA-F]{1,4})){5}");
        }
    }

    @Test
    void testIpV4Address() {
        Condition<String> colon = getCharacterCondition('.', 3);
        assertThat(faker.internet().ipV4Address()).is(colon);
        for (int i = 0; i < 100; i++) {
            try {
                final String[] octets = faker.internet().getIpV4Address().getHostAddress().split("\\.");
                assertThat(parseInt(octets[0])).isBetween(0, 255);
                assertThat(parseInt(octets[1])).isBetween(0, 255);
                assertThat(parseInt(octets[2])).isBetween(0, 255);
                assertThat(parseInt(octets[3])).isBetween(0, 255);
            } catch (UnknownHostException e) {
                fail("Failed with ", e);
            }
        }
    }

    @Test
    void testIpV4Cidr() {
        assertThat(faker.internet().ipV4Cidr())
            .is(getCharacterCondition('.', 3))
            .is(getCharacterCondition('/', 1));

        for (int i = 0; i < 1000; i++) {
            assertThat(parseInt(faker.internet().ipV4Cidr().split("/")[1]))
                .isBetween(1, 32);
        }
    }

    @Test
    void testPrivateIpV4Address() {
        String tenDot = "^10\\..+";
        String oneTwoSeven = "^127\\..+";
        String oneSixNine = "^169\\.254\\..+";
        String oneNineTwo = "^192\\.168\\..+";
        String oneSevenTwo = "^172\\.(16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31)\\..+";


        for (int i = 0; i < 1000; i++) {
            try {
                String addr = faker.internet().getPrivateIpV4Address().getHostAddress();
                assertThat(addr).is(anyOf(
                    new Condition<>(s -> s.matches(tenDot), "tenDot"),
                    new Condition<>(s -> s.matches(oneTwoSeven), "oneTwoSeven"),
                    new Condition<>(s -> s.matches(oneSixNine), "oneSixNine"),
                    new Condition<>(s -> s.matches(oneNineTwo), "oneNineTwo"),
                    new Condition<>(s -> s.matches(oneSevenTwo), "oneSevenTwo")
                ));
            } catch (UnknownHostException e) {
                fail("Failed with", e);
            }
        }
    }

    @Test
    void testPublicIpV4Address() {
        String tenDot = "^10\\.";
        String oneTwoSeven = "^127\\.";
        String oneSixNine = "^169\\.254";
        String oneNineTwo = "^192\\.168\\.";
        String oneSevenTwo = "^172\\.(16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31)\\.";
        for (int i = 0; i < 1000; i++) {
            try {
                String addr = faker.internet().getPublicIpV4Address().getHostAddress();
                assertThat(addr.matches(tenDot)).isFalse();
                assertThat(addr.matches(oneTwoSeven)).isFalse();
                assertThat(addr.matches(oneSixNine)).isFalse();
                assertThat(addr.matches(oneNineTwo)).isFalse();
                assertThat(addr.matches(oneSevenTwo)).isFalse();
            } catch (UnknownHostException e) {
                fail("Failed with", e);
            }
        }
    }

    @Test
    void testIpV6() {
        assertThat(faker.internet().ipV6Address()).is(getCharacterCondition(':', 7));

        for (int i = 0; i < 1000; i++) {
            try {
                assertThat(faker.internet().getIpV6Address().getHostAddress())
                    .matches("[0-9a-fA-F]{1,4}(:([0-9a-fA-F]{1,4})){1,7}");
            } catch (UnknownHostException e) {
                fail("Failed with", e);
            }
        }
    }

    @Test
    void testIpV6Cidr() {
        assertThat(faker.internet().ipV6Cidr())
            .is(getCharacterCondition(':', 7))
            .is(getCharacterCondition('/', 1));

        for (int i = 0; i < 1000; i++) {
            assertThat(parseInt(faker.internet().ipV6Cidr().split("/")[1]))
                .isBetween(1, 128);
        }
    }

    @RepeatedTest(10)
    void testSlugWithParams() {
        assertThat(faker.internet().slug(Arrays.asList("a", "b"), "-")).matches("[a-zA-Z]+-[a-zA-Z]+");
    }

    @RepeatedTest(10)
    void testSlug() {
        assertThat(faker.internet().slug()).matches("[a-zA-Z]+_[a-zA-Z]+");
    }

    @RepeatedTest(10)
    void testUuid() {
        assertThat(faker.internet().uuid()).matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
    }

    @RepeatedTest(100)
    void testFarsiIDNs() {
        // in this case, we're just making sure Farsi doesn't blow up.
        // there have been issues with Farsi not being produced.
        final Faker f = new Faker(new Locale("fa"));
        assertThat(f.internet().domainName()).isNotEmpty();
        assertThat(f.internet().emailAddress()).isNotEmpty();
        assertThat(f.internet().safeEmailAddress()).isNotEmpty();
        assertThat(f.internet().url()).isNotEmpty();
    }

    @Test
    void testUserAgent() {
        Internet.UserAgent[] agents = Internet.UserAgent.values();
        for (Internet.UserAgent agent : agents) {
            assertThat(faker.internet().userAgent(agent)).isNotEmpty();
        }

        //Test faker.internet().userAgentAny() for random user_agent retrieval.
        assertThat(faker.internet().userAgentAny()).isNotEmpty();
    }

    @Test
    void testSlugWithNull() {
        assertThat(faker.internet().slug(null, "_")).isNotNull();
    }
}
