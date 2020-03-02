package com.evaluateStudent.data;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


public class ConnectToSQL {


    private final String classs = "com.mysql.jdbc.Driver";
    private final String URL = "//localhost:3306/dghs";
    private final String USER = "root";
    private final String PASSWORD = "";
    Connection connect = null;

    public Connection CreateConnectToSQL() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(classs);
            connect = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception se) {
            Log.e("ERROR", Objects.requireNonNull(se.getMessage()));
        }
        return connect;
    }

    public boolean getAuthenticationAccess(String name, String password) {
        ResultSet rs = null;
        String query = "SELECT * FROM `user` WHERE `userName` = '" + name + "' and `password` = '" + password + "'";

        try {
            Statement stmt = connect.createStatement();
            rs = stmt.executeQuery(query);

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
