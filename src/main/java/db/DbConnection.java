package db;

import app.CloudConnection;
import app.LocalConnection;
import com.google.appengine.api.utils.SystemProperty;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConnection {
    public static Connection getConnection() {
        DataSource pool = null;
        java.sql.Connection conn = null;
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) { // when running locally, environment value is always Development, on Cloud is olways production
            pool = CloudConnection.getPool();
            try {
                conn = pool.getConnection();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                conn = LocalConnection.getDevConnection();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return conn;
    }
}
