package com.study.mybatis.statement;

import com.study.mybatis.resultset.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * java.sql.statement简易的处理类
 */
public interface StatementHandler {

    /**
     * 更新操作
     * @param connection
     * @param statement
     * @return
     * @throws SQLException
     */
    Integer update(Connection connection,Statement statement) throws SQLException;

    /**
     * 查询操作
     * @param connection
     * @param statement
     * @param resultHandler
     * @param <E>
     * @return
     * @throws SQLException
     */
    <E> List<E> query(Connection connection,Statement statement, ResultHandler resultHandler) throws SQLException;


}
