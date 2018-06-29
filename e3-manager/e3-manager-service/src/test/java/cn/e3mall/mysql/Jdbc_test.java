package cn.e3mall.mysql;


import org.junit.Test;

import java.sql.*;

public class Jdbc_test {

    @Test
    public void testMysqlServer() {
        final String DRIVER = "com.mysql.jdbc.Driver";
        final String URL = "jdbc:mysql://192.168.1.114:3306/e3mall?characterEncoding=utf-8";
        final String username = "root";
        final String password = "root";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, username, password);
            String sql = "select * from tb_item where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"536563");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
