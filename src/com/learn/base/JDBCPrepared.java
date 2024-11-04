package com.learn.base;

import java.sql.*;

public class JDBCPrepared {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动(可以省略)

        // 2.获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql:///newbee_mall_db", "root", "123456");

        // 3.获取执行SQL语句的对象
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM tb_newbee_mall_user WHERE user_id = ?");

        // 4.为?占位符赋值，并执行SQL语句，接受返回的结果
        prepareStatement.setInt(1,1);
        ResultSet resultSet = prepareStatement.executeQuery();

        // 5.处理结果，遍历resultSet
        while (resultSet.next()) {
            int id = resultSet.getInt("user_id");
            String name = resultSet.getString("nick_name");
            System.out.println(id+"\t"+name);
        }

        // 6.释放资源
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }
}
