package com.learn.senior.dao.impl;

import com.learn.senior.dao.BankDao;
import com.learn.senior.dao.BaseDAO;

public class BankDaoImpl extends BaseDAO implements BankDao {
    @Override
    public int addMoney(Integer id, Integer money) {
        try {
            String sql = "UPDATE t_bank SET money = money + ? WHERE id = ?";
            return executeUpdate(sql,money,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int subMoney(Integer id, Integer money) {
        try {
            String sql = "UPDATE t_bank SET money = money - ? WHERE id = ?";
            return executeUpdate(sql,money,id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
