package com.example.capstonedesign_geo.data.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLHelper {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/geo_db";
    private static final String USER = "geo";
    private static final String PSWD = "0000";

    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PSWD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
