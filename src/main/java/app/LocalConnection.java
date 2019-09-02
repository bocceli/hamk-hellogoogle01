package app;

import java.sql.Connection;
import java.sql.SQLException;

public class LocalConnection {
    public static Connection getDevConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//	  Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/hellogoogle1?verifyServerCertificate=false&useSSL=false&serverTimezone=UTC", "root", /*"kukkuu");//*/ System.getProperty("password"));
//	  Connection conn=java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/hellogoogle1?useSSL=true&user=pena&password=kukkuu");
        return conn;
    }
}
