package net.datafaker.providers.base;


/**
 * Generates data for Azure services. This is based on the Azure best practices of naming conventions:
 * <a href="https://learn.microsoft.com/en-us/azure/cloud-adoption-framework/ready/azure-best-practices/resource-naming">Naming conventions</a>
 * <a href="https://learn.microsoft.com/en-us/azure/cloud-adoption-framework/ready/azure-best-practices/resource-abbreviations">Abbreviation examples</a>
 *
 * @since 1.7.0
 */
public class Azure extends AbstractProvider<BaseProviders> {

    protected Azure(BaseProviders faker) {
        super(faker);
    }

    public String region() {
        return resolve("azure.regions");
    }

    public String subscriptionId() {
        return faker.random().hex(8, false) + '-' +
            faker.random().hex(4, false) + '-' +
            faker.random().hex(4, false) + '-' +
            faker.random().hex(4, false) + '-' +
            faker.random().hex(12, false);
    }

    public String tenantId() {
        return subscriptionId();
    }
    public String resourceGroup() {
        return "rg-" + randHex();
    }

    public String managementGroup() {
        return "mg-" + randHex();
    }

    public String applicationGateway() {
        return "agw-" + randHex();
    }

    public String bastionHost() {
        return "bas-" + randHex();
    }

    public String firewall() {
        return "afw-" + randHex();
    }

    public String loadBalancer() {
        return "lbi-" + randHex();
    }

    public String networkSecurityGroup() {
        return "nsg-" + randHex();
    }

    public String virtualNetwork() {
        return "vnet-" + randHex();
    }

    public String virtualWan() {
        return "vwan-" + randHex();
    }

    public String appServiceEnvironment() {
        return "ase-" + randHex();
    }

    public String appServicePlan() {
        return "asp-" + randHex();
    }

    public String loadTesting() {
        return "lt-" + randHex();
    }

    public String staticWebApp() {
        return "stapp-" + randHex();
    }

    public String virtualMachine() {
        return "vm-" + randHex();
    }

    public String storageAccount() {
        return "st-" + randHex();
    }

    public String containerRegistry() {
        return "cr-" + randHex();
    }

    public String containerApps() {
        return "ca-" + randHex();
    }

    public String containerAppsEnvironment() {
        return "cae-" + randHex();
    }

    public String containerInstance() {
        return "ci-" + randHex();
    }

    public String cosmosDBDatabase() {
        return "cosmos-" + randHex();
    }

    public String sqlDatabase() {
        return "sql-" + randHex();
    }

    public String mysqlDatabase() {
        return "mysql-" + randHex();
    }

    public String postgreSQLDatabase() {
        return "psql-" + randHex();
    }

    public String serviceBus() {
        return "sb-" + randHex();
    }

    public String serviceBusQueue() {
        return "sbq-" + randHex();
    }

    public String serviceBusTopic() {
        return "sbt-" + randHex();
    }

    public String keyVault() {
        return "kv-" + randHex();
    }

    public String logAnalytics() {
        return "log-" + randHex();
    }
    
    public String springApps() {
        return "sa-" + randHex();
    }

    private String randHex() {
        return faker.random().hex(16, false);
    }
}
