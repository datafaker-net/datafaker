package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AwsTest extends AbstractFakerTest {

    @Test
    void testAccountId() {
        assertThat(faker.aws().accountId()).matches("^\\d{10}$");
    }

    @Test
    void testAcmARN() {
        assertThat(faker.aws().acmARN()).matches("^arn:aws:acm:\\w+-\\w+-\\d:\\d{10}:certificate/[\\w\\-]+$");
    }

    @Test
    void testAlbARN() {
        assertThat(faker.aws().albARN()).matches("^arn:aws:elasticloadbalancing:\\w+-\\w+-\\d:\\d{10}:loadbalancer/app/[\\w]+/\\w+$");
    }

    @Test
    void testAlbTargetGroupARN() {
        assertThat(faker.aws().albTargetGroupARN()).matches("^arn:aws:elasticloadbalancing:\\w+-\\w+-\\d:\\d{10}:targetgroup/[\\w]+/\\w+$");
    }

    @Test
    void testRoute53ZoneId() {
        assertThat(faker.aws().route53ZoneId()).matches("^\\w{21}$");
    }

    @Test
    void testSecurityGroupId() {
        assertThat(faker.aws().securityGroupId()).matches("^sg-[0-9a-f]{16}$");
    }

    @Test
    void testSubnetId() {
        assertThat(faker.aws().subnetId()).matches("^subnet-[0-9a-f]{16}$");
    }

    @Test
    void testVpcId() {
        assertThat(faker.aws().vpcId()).matches("^vpc-[0-9a-f]{16}$");
    }

    @Test
    void testRegion() {
        String region = faker.aws().region();
        assertThat(region).matches("^[a-z]{2}-(south|east|north|west|northeast|central|southeast)-\\d$");
    }
}
