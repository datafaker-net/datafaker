package net.datafaker.providers.base;

import java.util.List;
import java.util.Map;

public class Database extends AbstractProvider<BaseProviders> {

    private static final String DATABASES = "database.databases";

    protected Database(BaseProviders faker) {
        super(faker);
    }

    public String vendor() {
        return randomDatabase().get("name").toString();
    }

    public String type() {
        return randomDatabase().get("type").toString();
    }

    public String dialect() {
        return randomDatabase().get("dialect").toString();
    }

    public int port(String vendor) {
        return Integer.parseInt(database(vendor).get("port").toString());
    }

    public String jdbcDriver(String vendor) {
        return database(vendor).get("jdbc_driver").toString();
    }

    public String jdbcUrl(String vendor, String host, String databaseName) {
        String template = database(vendor)
            .get("jdbc_url")
            .toString();

        return template
            .replace("{host}", host)
            .replace("{port}", String.valueOf(port(vendor)))
            .replace("{database}", databaseName);
    }

    public String sqlType() {
        return resolve("database.sql_types");
    }

    public String constraint() {
        return resolve("database.constraints");
    }

    public String indexType() {
        return resolve("database.index_types");
    }

    public String isolationLevel() {
        return resolve("database.isolation_levels");
    }

    public String sqlState() {
        return resolve("database.sql_states");
    }

    public String error() {
        return resolve("database.errors");
    }

    public boolean supportsJson(String vendor) {
        return supports(vendor, "json");
    }

    public boolean supportsTransactions(String vendor) {
        return supports(vendor, "transactions");
    }

    public boolean supportsWindowFunctions(String vendor) {
        return supports(vendor, "window_functions");
    }

    private boolean supports(String vendor, String capability) {
        return database(vendor)
            .get("capabilities")
            .toString()
            .contains(capability);
    }

    private Map<String, Object> randomDatabase() {
        List<Map<String, Object>> databases = databases();

        return databases.get(
            faker.random().nextInt(databases.size())
        );
    }

    private Map<String, Object> database(String vendor) {
        return databases()
            .stream()
            .filter(database ->
                database.get("name")
                    .toString()
                    .equalsIgnoreCase(vendor))
            .findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Unknown database vendor: " + vendor
                ));
    }

    private List<Map<String, Object>> databases() {
        return faker.fakeValuesService()
            .fetchObject(DATABASES, faker.getContext());
    }
}
