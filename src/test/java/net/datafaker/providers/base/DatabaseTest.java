package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseTest extends BaseFakerTest {

    private final Database database = faker.database();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(database::sqlType, "database.sql_types"),
            TestSpec.of(database::constraint, "database.constraints"),
            TestSpec.of(database::indexType, "database.index_types"),
            TestSpec.of(database::isolationLevel, "database.isolation_levels"),
            TestSpec.of(database::sqlState, "database.sql_states"),
            TestSpec.of(database::error, "database.errors")
        );
    }

    @Test
    void postgresDatabaseShouldReturnCorrectJdbcInformation() {
        assertThat(database.jdbcDriver("PostgreSQL"))
            .isEqualTo("org.postgresql.Driver");

        assertThat(database.port("PostgreSQL"))
            .isEqualTo(5432);

        assertThat(
            database.jdbcUrl(
                "PostgreSQL",
                "localhost",
                "orders"
            )
        ).isEqualTo(
            "jdbc:postgresql://localhost:5432/orders"
        );
    }

    @Test
    void mysqlDatabaseShouldReturnCorrectJdbcInformation() {
        assertThat(database.jdbcDriver("MySQL"))
            .isEqualTo("com.mysql.cj.jdbc.Driver");

        assertThat(database.port("MySQL"))
            .isEqualTo(3306);

        assertThat(
            database.jdbcUrl(
                "MySQL",
                "localhost",
                "orders"
            )
        ).isEqualTo(
            "jdbc:mysql://localhost:3306/orders"
        );
    }

    @Test
    void postgresShouldSupportJson() {
        assertThat(database.supportsJson("PostgreSQL"))
            .isTrue();
    }

    @Test
    void sqliteShouldReturnSqliteJdbcUrl() {
        assertThat(
            database.jdbcUrl(
                "SQLite",
                "localhost",
                "orders"
            )
        ).isEqualTo(
            "jdbc:sqlite:orders.db"
        );
    }
}
