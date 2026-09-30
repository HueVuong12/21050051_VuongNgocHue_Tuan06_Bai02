package vn.fit.vuongngochue_bai2_tuan06.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = System.getProperty("db.url", "jdbc:mariadb://localhost:3306/storedb?useUnicode=true&characterEncoding=UTF-8");
    private static final String USER = System.getProperty("db.user", "root");
    private static final String PASSWORD = System.getProperty("db.password", "123456");

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy MariaDB JDBC Driver", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
