package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

class AwsTest extends AbstractFakerTest {

    @Test
    void testAccountId() {
        assertThat(faker.aws().accountId(), matchesRegularExpression("^\\d{10}$"));
    }

    @Test
    void testAcmARN() {
        assertThat(faker.aws().acmARN(), matchesRegularExpression("^arn:aws:acm:\\w+\\-\\w+\\-\\d:\\d{10}:certificate/[\\w\\-]+$"));
    }

    @Test
    void testAlbARN() {
        assertThat(faker.aws().albARN(), matchesRegularExpression("^arn:aws:elasticloadbalancing:\\w+\\-\\w+\\-\\d:\\d{10}:loadbalancer/app/[\\w]+/\\w+$"));
    }

    @Test
    void testAlbTargetGroupARN() {
        assertThat(faker.aws().albTargetGroupARN(), matchesRegularExpression("^arn:aws:elasticloadbalancing:\\w+\\-\\w+\\-\\d:\\d{10}:targetgroup/[\\w]+/\\w+$"));
    }

    @Test
    void testRoute53ZoneId() {
        assertThat(faker.aws().route53ZoneId(), matchesRegularExpression("^\\w{21}$"));
    }

    @Test
    void testSecurityGroupId() {
        assertThat(faker.aws().securityGroupId(), matchesRegularExpression("^sg\\-[0-9a-f]{16}$"));
    }

    @Test
    void testSubnetId() {
        assertThat(faker.aws().subnetId(), matchesRegularExpression("^subnet\\-[0-9a-f]{16}$"));
    }

    @Test
    void testVpcId() {
        assertThat(faker.aws().vpcId(), matchesRegularExpression("^vpc\\-[0-9a-f]{16}$"));
    }

    @Test
    void testRegion() {
        String region = faker.aws().region();
        assertThat(region, matchesRegularExpression("^[a-z]{2}\\-(south|east|north|west|northeast|central|southeast)\\-\\d$"));
    }
}
