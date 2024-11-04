package com.learn.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCQuick {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2.获取连接对象
        String url = "jdbc:mysql://localhost:3306/newbee_mall_db";
        String password = "123456";
        String username = "root";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 3.获取执行SQL语句的对象
        Statement statement = connection.createStatement();

        // 4.编写SQL语句，并执行，接收返回的结果集
        String sql = "SELECT * FROM tb_newbee_mall_user";
        ResultSet resultSet = statement.executeQuery(sql);

        // 5.处理结果：遍历resultSet结果集
        while (resultSet.next()) {
            int id = resultSet.getInt("user_id");
            String name = resultSet.getString("nick_name");
            System.out.println(id+"\t"+name);
        }

        // 5.释放资源（先开后关原则）
        resultSet.close();
        statement.close();
        connection.close();
    }
}
