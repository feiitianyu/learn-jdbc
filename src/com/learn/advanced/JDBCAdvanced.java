package com.learn.advanced;

import com.learn.advanced.pojo.Employee;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAdvanced {
    @Test
    public void testORM() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///newbee_mall_db", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT emp_id,emp_name,emp_salary,emp_age FROM t_emp WHERE emp_id = ?");
        prepareStatement.setInt(1,1);
        ResultSet resultSet = prepareStatement.executeQuery();
        Employee employee = null;
        if(resultSet.next()) {
            employee = new Employee();
            int id = resultSet.getInt("emp_id");
            String name = resultSet.getString("emp_name");
            double salary = resultSet.getDouble("emp_salary");
            int age = resultSet.getInt("emp_age");
            employee.setEmpId(id);
            employee.setEmpName(name);
            employee.setEmpSalary(salary);
            employee.setEmpAge(age);
        }
        System.out.println(employee);
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testORMList() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql:///newbee_mall_db", "root", "123456");
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT emp_id,emp_name,emp_salary,emp_age FROM t_emp");
        ResultSet resultSet = prepareStatement.executeQuery();
        Employee employee = null;
        List<Employee> employeeList = new ArrayList<>();
        if(resultSet.next()) {
            employee = new Employee();
            int id = resultSet.getInt("emp_id");
            String name = resultSet.getString("emp_name");
            double salary = resultSet.getDouble("emp_salary");
            int age = resultSet.getInt("emp_age");
            employee.setEmpId(id);
            employee.setEmpName(name);
            employee.setEmpSalary(salary);
            employee.setEmpAge(age);
            employeeList.add(employee);
        }
        for (Employee emp : employeeList) {
            System.out.println(emp);
        }
        resultSet.close();
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testReturnPK() throws Exception {
        // 获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///newbee_mall_db", "root", "123456");

        // 预编译SQL语句，告知prepareStatement，返回新增数据的主键列的值
        String sql = "INSERT INTO t_emp(emp_id,emp_name,emp_salary,emp_age) VALUES(?,?,?,?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // 创建对象，将对象的属性值，填充在?占位符上（ORM）
        Employee employee = new Employee(null, "jack", 123.45, 29);
        prepareStatement.setString(1,employee.getEmpName());
        prepareStatement.setDouble(2,employee.getEmpSalary());
        prepareStatement.setInt(3,employee.getEmpAge());

        // 执行SQL，并获取返回的结果
        int i = prepareStatement.executeUpdate();

        ResultSet generatedKeys = null;
        // 处理结果
        if(i > 0) {
            System.out.println("success");

            // 获取当前新增数据的主键列，回显到Java中employee对象的empId属性上
            // 返回的主键列，是一个单行单列的结果存储在ResultSet里
            generatedKeys = prepareStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                int empId = generatedKeys.getInt(1);
                employee.setEmpId(empId);
            }
            System.out.println(employee);
        } else {
            System.out.println("fail");
        }

        // 释放资源
        if(generatedKeys != null) {
            generatedKeys.close();
        }
        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testMoreInsert() throws Exception {
        // 1.注册驱动
        // Class.forName("com.mysql.cj.jdbc.Driver");

        // 2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc", "root", "123456");

        // 3.编写SQL语句
        String sql = "INSERT INTO t_emp(emp_name,emp_salary,emp_age) VALUES(?,?,?)";

        // 4.创建预编译的PreparedStatement，传入SQL语句
        PreparedStatement prepareStatement = connection.prepareStatement(sql);

        //获取当前行代码执行的时间。毫秒值
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            // 5.为占位符赋值
            prepareStatement.setString(1,"jack" + i);
            prepareStatement.setDouble(2,100.0 + 1);
            prepareStatement.setInt(3,20 + i);

            prepareStatement.executeUpdate();
        }
        long end = System.currentTimeMillis();
        System.out.println("消耗时间：" + (end - start));

        prepareStatement.close();
        connection.close();
    }

    @Test
    public void testBatch() throws Exception {
        // 1.注册驱动
        // Class.forName("com.mysql.cj.jdbc.Driver");

        // 2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///learn-jdbc?rewriteBatchedStatements=true", "root", "123456");

        // 3.编写SQL语句
        /**
         * 注意：1：必须在连接数据库的URL后面追加?rewriteBatchedStatements=true，允许批量操作
         *      2：新增SQL必须用values，且语句最后不要追加;结束
         *      3：调用addBatch()方法，将SQL语句进行批量添加操作
         *      4：统一执行批量操作，调用executeBatch()方法
         */
        String sql = "INSERT INTO t_emp(emp_name,emp_salary,emp_age) VALUES(?,?,?)";

        // 4.创建预编译的PreparedStatement，传入SQL语句
        PreparedStatement prepareStatement = connection.prepareStatement(sql);

        //获取当前行代码执行的时间。毫秒值
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            // 5.为占位符赋值
            prepareStatement.setString(1,"jack" + i);
            prepareStatement.setDouble(2,100.0 + 1);
            prepareStatement.setInt(3,20 + i);

            prepareStatement.addBatch();
        }

        // 执行批量操作
        prepareStatement.executeBatch();

        long end = System.currentTimeMillis();
        System.out.println("消耗时间：" + (end - start));

        prepareStatement.close();
        connection.close();
    }
}
