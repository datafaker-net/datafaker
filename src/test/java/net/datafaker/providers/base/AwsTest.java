package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AwsTest extends BaseFakerTest<BaseFaker> {

    @Test
    void accountId() {
        assertThat(faker.aws().accountId()).matches("^\\d{10}$");
    }

    @Test
    void acmARN() {
        assertThat(faker.aws().acmARN()).matches("^arn:aws:acm:\\w+-\\w+-\\d:\\d{10}:certificate/[\\w\\-]+$");
    }

    @Test
    void albARN() {
        assertThat(faker.aws().albARN()).matches("^arn:aws:elasticloadbalancing:\\w+-\\w+-\\d:\\d{10}:loadbalancer/app/[\\w]+/\\w+$");
    }

    @Test
    void albTargetGroupARN() {
        assertThat(faker.aws().albTargetGroupARN()).matches("^arn:aws:elasticloadbalancing:\\w+-\\w+-\\d:\\d{10}:targetgroup/[\\w]+/\\w+$");
    }

    @Test
    void route53ZoneId() {
        assertThat(faker.aws().route53ZoneId()).matches("^\\w{21}$");
    }

    @Test
    void securityGroupId() {
        assertThat(faker.aws().securityGroupId()).matches("^sg-[0-9a-f]{16}$");
    }

    @Test
    void subnetId() {
        assertThat(faker.aws().subnetId()).matches("^subnet-[0-9a-f]{16}$");
    }

    @Test
    void vpcId() {
        assertThat(faker.aws().vpcId()).matches("^vpc-[0-9a-f]{16}$");
    }

    @Test
    void region() {
        String region = faker.aws().region();
        assertThat(region).matches("^[a-z]{2}-(south|east|north|west|northeast|central|southeast)-\\d$");
    }
}
