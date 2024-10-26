package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AzureTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testRegion() {
        String region = faker.azure().region();
        assertThat(region).matches("(eastus|eastus2|southcentralus|westus2|westus3|australiaeast|southeastasia|northeurope|swedencentral|uksouth|westeurope|centralus|southafricanorth|centralindia|eastasia|japaneast|koreacentral|canadacentral|francecentral|germanywestcentral|norwayeast|switzerlandnorth|uaenorth|brazilsouth|eastus2euap|qatarcentral|centralusstage|eastusstage|eastus2stage|northcentralusstage|southcentralusstage|westusstage|westus2stage|asia|asiapacific|australia|brazil|canada|europe|france|germany|global|india|japan|korea|norway|singapore|southafrica|switzerland|uae|uk|unitedstates|unitedstateseuap|eastasiastage|southeastasiastage|eastusstg|southcentralusstg|northcentralus|westus|jioindiawest|centraluseuap|westcentralus|southafricawest|australiacentral|australiacentral2|australiasoutheast|japanwest|jioindiacentral|koreasouth|southindia|westindia|canadaeast|francesouth|germanynorth|norwaywest|switzerlandwest|ukwest|uaecentral|brazilsoutheast)");
    }

    @Test
    void testAccountId() {
        assertThat(faker.azure().subscriptionId()).matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}");
    }

    @Test
    void testTenantId() {
        assertThat(faker.azure().tenantId()).matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}");
    }

    @Test
    void testResourceGroup() {
        assertThat(faker.azure().resourceGroup()).matches("^rg-[0-9a-f]{16}$");
    }

    @Test
    void testManagementGroup() {
        assertThat(faker.azure().managementGroup()).matches("^mg-[0-9a-f]{16}$");
    }

    @Test
    void testApplicationGateway() {
        assertThat(faker.azure().applicationGateway()).matches("^agw-[0-9a-f]{16}$");
    }

    @Test
    void testBastionHost() {
        assertThat(faker.azure().bastionHost()).matches("^bas-[0-9a-f]{16}$");
    }

    @Test
    void testFirewall() {
        assertThat(faker.azure().firewall()).matches("^afw-[0-9a-f]{16}$");
    }

    @Test
    void testLoadBalancer() {
        assertThat(faker.azure().loadBalancer()).matches("^lbi-[0-9a-f]{16}$");
    }

    @Test
    void testNetworkSecurityGroup() {
        assertThat(faker.azure().networkSecurityGroup()).matches("^nsg-[0-9a-f]{16}$");
    }

    @Test
    void testVirtualNetwork() {
        assertThat(faker.azure().virtualNetwork()).matches("^vnet-[0-9a-f]{16}$");
    }

    @Test
    void testVirtualWan() {
        assertThat(faker.azure().virtualWan()).matches("^vwan-[0-9a-f]{16}$");
    }

    @Test
    void testAppServiceEnvironment() {
        assertThat(faker.azure().appServiceEnvironment()).matches("^ase-[0-9a-f]{16}$");
    }

    @Test
    void testAppServicePlan() {
        assertThat(faker.azure().appServicePlan()).matches("^asp-[0-9a-f]{16}$");
    }

    @Test
    void testLoadTesting() {
        assertThat(faker.azure().loadTesting()).matches("^lt-[0-9a-f]{16}$");
    }

    @Test
    void testStaticWebApp() {
        assertThat(faker.azure().staticWebApp()).matches("^stapp-[0-9a-f]{16}$");
    }

    @Test
    void testVirtualMachine() {
        assertThat(faker.azure().virtualMachine()).matches("^vm-[0-9a-f]{16}$");
    }

    @Test
    void testStorageAccount() {
        assertThat(faker.azure().storageAccount()).matches("^st-[0-9a-f]{16}$");
    }

    @Test
    void testContainerRegistry() {
        assertThat(faker.azure().containerRegistry()).matches("^cr-[0-9a-f]{16}$");
    }

    @Test
    void testContainerApps() {
        assertThat(faker.azure().containerApps()).matches("^ca-[0-9a-f]{16}$");
    }

    @Test
    void testContainerAppsEnvironment() {
        assertThat(faker.azure().containerAppsEnvironment()).matches("^cae-[0-9a-f]{16}$");
    }

    @Test
    void testContainerInstance() {
        assertThat(faker.azure().containerInstance()).matches("^ci-[0-9a-f]{16}$");
    }

    @Test
    void testCosmosDBDatabase() {
        assertThat(faker.azure().cosmosDBDatabase()).matches("^cosmos-[0-9a-f]{16}$");
    }

    @Test
    void testSqlDatabase() {
        assertThat(faker.azure().sqlDatabase()).matches("^sql-[0-9a-f]{16}$");
    }

    @Test
    void testMysqlDatabase() {
        assertThat(faker.azure().mysqlDatabase()).matches("^mysql-[0-9a-f]{16}$");
    }

    @Test
    void testPostgreSQLDatabase() {
        assertThat(faker.azure().postgreSQLDatabase()).matches("^psql-[0-9a-f]{16}$");
    }

    @Test
    void testServiceBus() {
        assertThat(faker.azure().serviceBus()).matches("^sb-[0-9a-f]{16}$");
    }

    @Test
    void testServiceBusQueue() {
        assertThat(faker.azure().serviceBusQueue()).matches("^sbq-[0-9a-f]{16}$");
    }

    @Test
    void testServiceBusTopic() {
        assertThat(faker.azure().serviceBusTopic()).matches("^sbt-[0-9a-f]{16}$");
    }

    @Test
    void testKeyVault() {
        assertThat(faker.azure().keyVault()).matches("^kv-[0-9a-f]{16}$");
    }

    @Test
    void testLogAnalytics() {
        assertThat(faker.azure().logAnalytics()).matches("^log-[0-9a-f]{16}$");
    }
    
    @Test
    void testSpringApps() {
        assertThat(faker.azure().springApps()).matches("^sa-[0-9a-f]{16}$");
    }
}
