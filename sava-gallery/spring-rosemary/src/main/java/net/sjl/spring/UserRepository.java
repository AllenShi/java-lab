package net.sjl.spring;


import net.sjl.fragrans.util.SecurityUtil;

import java.sql.*;

public class UserRepository {

    public Object addUser(String name, char isActive) {

        String sql = "SELECT * FROM USERS WHERE name = '" + name + "' AND isActive = '" + isActive + "'";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            return rs;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
