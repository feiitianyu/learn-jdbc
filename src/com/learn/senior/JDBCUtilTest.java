package com.learn.senior;

import com.learn.senior.dao.EmployeeDao;
import com.learn.senior.dao.impl.BankDaoImpl;
import com.learn.senior.dao.impl.EmployeeDaoImpl;
import com.learn.senior.pojo.Employee;
import com.learn.senior.util.JDBCUtil;
import com.learn.senior.util.JDBCUtilV2;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBCUtilTest {
    @Test
    public void testGetConnection() {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
        JDBCUtil.release(connection);
    }

    @Test
    public void testJDBCV2() {
//        Connection connection1 = JDBCUtil.getConnection();
//        Connection connection2 = JDBCUtil.getConnection();
//        Connection connection3 = JDBCUtil.getConnection();
//
//        System.out.println(connection1);
//        System.out.println(connection2);
//        System.out.println(connection3);

        Connection connection1 = JDBCUtilV2.getConnection();
        Connection connection2 = JDBCUtilV2.getConnection();
        Connection connection3 = JDBCUtilV2.getConnection();

        System.out.println(connection1);
        System.out.println(connection2);
        System.out.println(connection3);
    }

    @Test
    public void testEmployeeDao() {
        // 1.创建DAO实现类对象
        EmployeeDao employeeDao = new EmployeeDaoImpl();

//        // 2.调用查询所有方法
//        List<Employee> employeeList = employeeDao.selectAll();
//
//        // 3.处理结果
//        for (Employee employee : employeeList) {
//            System.out.println("employee" + employee);
//        }

//        // 调用根据id查询单个员工方法
//        Employee employee = employeeDao.selectByEmpId(1);
//        System.out.println("employee" + employee);

//        // 调用添加员工的方法
//        Employee employee = new Employee(null, "tom", 123.34, 12);
//        int insert = employeeDao.insert(employee);
//        System.out.println("insert" + insert);

//        Employee employee = new Employee(123, "tom", 1235.34, 12);
//        int i = employeeDao.update(employee);
//        System.out.println("i = " + i);

        int delete = employeeDao.delete(123);
        System.out.println("delete = " + delete);
    }

    @Test
    public void testTransaction() {
        BankDaoImpl bankDao = new BankDaoImpl();
        Connection connection = null;
        try {
            // 1.获取连接，将连接的事务提交改为手动提交
            connection = JDBCUtilV2.getConnection();
            connection.setAutoCommit(false); // 开启事务，当前连接的自动提交关闭，改为手动提交

            // 2.操作减钱
            bankDao.addMoney(1,100);

            int i = 10 / 0;

            // 3.操作加钱
            bankDao.subMoney(2,100);

            // 4.前置的多次dao操作，没有异常，提交事务
            connection.commit();

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            JDBCUtilV2.release();
        }
    }
}
