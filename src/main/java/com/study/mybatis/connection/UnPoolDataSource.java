package com.study.mybatis.connection;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 非连接池的数据源获取
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnPoolDataSource implements DataSource {
    private String driver;
    private String url;
    private String username;
    private String password;
    private boolean autocommit;

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(autocommit);
        return connection;
    }
}
