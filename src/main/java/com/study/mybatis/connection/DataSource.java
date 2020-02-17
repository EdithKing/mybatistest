package com.study.mybatis.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据源
 */
public interface DataSource {

    /**
     * 获取数据源
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    Connection getConnection() throws ClassNotFoundException, SQLException;

}
