package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final public class Util {
    static final public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                String.format("jdbc:mysql://%s:%d/%s",
                        DatabaseConfig.HOSTNAME,
                        DatabaseConfig.PORT,
                        DatabaseConfig.DATABASE_NAME),
                DatabaseConfig.USERNAME,
                DatabaseConfig.PASSWORD);
    }
}
