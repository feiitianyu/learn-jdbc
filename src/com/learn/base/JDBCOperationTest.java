package com.learn.base;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCOperationTest {
    @Test
    public void testQuerySingleRowAndCol() throws Exception {
        // 1.注册驱动（可以省略）

        // 2.获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql:///newbee_mall_db", "root", "123456");

        // 3.预编译SQL语句得到PreparedStatement对象
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM tb_newbee_mall_user");

        // 4.执行SQL语句，获取结果
        ResultSet resultSet = prepareStatement.executeQuery();

        // 5.处理结果（如果自己明确一定只有一个结果，那么resultSet最少要做一次next的判断，才能拿到我们要的列的结果）
        if (resultSet.next()) {
            int count = resultSet.getInt("count");
            System.out.println(count);
        }

        // 6.释放资源
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testQuerySingleRow() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///newbee_mall_db", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM tb_newbee_mall_user WHERE user_id = ?");
        prepareStatement.setInt(1,1);
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("user_id");
            String name = resultSet.getString("nick_name");
            System.out.println(id+"\t"+name);
        }
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testQueryMoreRow() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///newbee_mall_db", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM tb_newbee_mall_user WHERE user_id > ?");
        prepareStatement.setInt(1,5);
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("user_id");
            String name = resultSet.getString("nick_name");
            System.out.println(id+"\t"+name);
        }
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testInsert() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///newbee_mall_db", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO tb_newbee_mall_user(nick_name) VALUES (?)");
        prepareStatement.setString(1,"test1");
        int i = prepareStatement.executeUpdate();
        if(i > 0) {
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testUpdate() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///newbee_mall_db", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("UPDATE tb_newbee_mall_user SET nick_name = ? WHERE user_id = ?");
        prepareStatement.setString(1,"test2");
        prepareStatement.setInt(2,9);
        int i = prepareStatement.executeUpdate();
        if(i > 0) {
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testDelete() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///newbee_mall_db", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM tb_newbee_mall_user WHERE user_id = ?");
        prepareStatement.setInt(1,9);
        int i = prepareStatement.executeUpdate();
        if(i > 0) {
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
        prepareStatement.close();
        connection.close();
    }

}
