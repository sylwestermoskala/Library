package pl.sdacademy.dbtools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection(String connString, String user, String pass){
        Connection resultConnection = null;

        try {
            resultConnection = DriverManager.getConnection(connString, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultConnection;
    }

}
