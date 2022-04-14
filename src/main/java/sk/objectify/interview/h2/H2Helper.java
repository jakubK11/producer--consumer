package sk.objectify.interview.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Helper {
    private H2Helper() {
    }

    public static Connection init() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:h2:mem:˜/test;INIT=DROP TABLE SUSERS IF EXISTS\\; CREATE TABLE SUSERS (USER_ID long, USER_GUID varchar(255), USER_NAME varchar(255))");
    }
}
