package sk.objectify.interview.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Helper {
    private H2Helper() {
    }

    public static Connection init() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:˜/test;INIT=runscript from 'classpath:db/init.sql'");
    }
}
