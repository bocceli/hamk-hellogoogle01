package app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class CloudConnection {
    public static DataSource getPool() {
        // The configuration object specifies behaviors for the connection pool.
        HikariConfig config = new HikariConfig();

        // Configure which instance and what database user to connect with.
        config.setJdbcUrl(String.format("jdbc:mysql:///%s", "mobileapp01")); //e.g. hellogoogle1
        config.setUsername("root"); // e.g. "root", "postgres"
        config.setPassword("a"); // e.g. "my-password"

        // For Java users, the Cloud SQL JDBC Socket Factory can provide authenticated connections.
        // See https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory for details.
        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
        config.addDataSourceProperty("cloudSqlInstance", "mobile-app-01-251306:europe-west1:mobileapp01mysql");


        config.addDataSourceProperty("useSSL", "false");

        // ... Specify additional connection properties here.
        // ...


        // Initialize the connection pool using the configuration object.
        DataSource pool = new HikariDataSource(config);
        return pool;
    }
}
