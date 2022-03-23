package net.datafaker;

import java.util.UUID;

public class Aws {

    private final Faker faker;

    protected Aws(Faker faker) {
        this.faker = faker;
    }

    public String region() {
        return faker.fakeValuesService().resolve("aws.regions", this, faker);
    }

    public String accountId() {
        return faker.numerify("0#########");
    }

    public String acmARN() {
        return new StringBuilder("arn:aws:acm:")
            .append(region())
            .append(":")
            .append(accountId())
            .append(":certificate/")
            .append(UUID.randomUUID())
            .toString();
    }

    public String albARN() {
        return new StringBuilder("arn:aws:elasticloadbalancing:")
            .append(region())
            .append(":")
            .append(accountId())
            .append(":loadbalancer/app/")
            .append(appName())
            .append("/")
            .append(randHex())
            .toString();
    }

    public String albTargetGroupARN() {
        return new StringBuilder("arn:aws:elasticloadbalancing:")
            .append(region())
            .append(":")
            .append(accountId())
            .append(":targetgroup/")
            .append(appName())
            .append("/")
            .append(randHex())
            .toString();
    }

    public String route53ZoneId() {
        return faker.regexify("[A-Z]{21}");
    }

    public String securityGroupId() {
        return "sg-" + randHex();
    }

    public String subnetId() {
        return "subnet-" + randHex();
    }

    public String vpcId() {
        return "vpc-" + randHex();
    }

    private String appName() {
        return faker.app().name().toLowerCase().replaceAll("\\W+", "");
    }

    private String randHex() {
        return faker.regexify("[a-f0-9]{16}");
    }
}
