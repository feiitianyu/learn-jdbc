package com.learn.base;

import java.sql.*;

public class JDBCInjection {
    public static void main(String[] args) throws Exception {
        // 1.注册驱动(可以省略)

        // 2.获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql:///newbee_mall_db", "root", "123456");

        // 3.获取执行SQL语句的对象
        Statement statement = connection.createStatement();

        // 4.编写SQL语句，并执行，接受返回的结果
        String nick_name = "tom' or '1' = '1";
        String sql = "SELECT * FROM tb_newbee_mall_user WHERE nick_name = '" + nick_name + "'";
        ResultSet resultSet = statement.executeQuery(sql);

        // 5.处理结果，遍历resultSet
        while (resultSet.next()) {
            int id = resultSet.getInt("user_id");
            String name = resultSet.getString("nick_name");
            System.out.println(id+"\t"+name);
        }

        // 6.释放资源
        resultSet.close();
        statement.close();
        connection.close();
    }
}
