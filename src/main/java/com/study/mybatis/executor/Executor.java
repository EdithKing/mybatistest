package com.study.mybatis.executor;

import com.study.mybatis.configurate.Configurate;
import com.study.mybatis.resultset.ResultHandler;
import com.study.mybatis.statement.MapperHandler;
import com.study.mybatis.statement.SimpleStatementHandler;
import com.study.mybatis.statement.StatementHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * sql执行器，获取sql，去调用statement去执行sql，将返回结果进行处理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Executor {
    private Configurate configurate;
    private StatementHandler statementHandler;
    private ResultHandler resultHandler;

    /**
     * 调用statmentHandler的接口执行sql，并且将结果加入resultHnadler中处理结果集
     * @param connection
     * @param mapperHandler
     * @param args
     * @param <E>
     * @return
     */
    public <E> List<E> query(Connection connection,MapperHandler mapperHandler, Object... args) {
        Statement statement = null;
        try {
            statement = this.prepare(connection,mapperHandler, args);
            ((SimpleStatementHandler) this.statementHandler).setMapperHandler(mapperHandler);
            return statementHandler.query(connection,statement, resultHandler);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * sql可变参数的填充
     * @param connection
     * @param mapperHandler
     * @param args
     * @return
     * @throws SQLException
     */
    private Statement prepare(Connection connection,MapperHandler mapperHandler, Object... args) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(mapperHandler.getSql());
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
        }
        return statement;
    }

    public Integer update(Connection connection,MapperHandler mapperHandler, Object[] args) {
        Statement statement = null;
        try {
            statement = this.prepare(connection,mapperHandler, args);
            ((SimpleStatementHandler) this.statementHandler).setMapperHandler(mapperHandler);
            return statementHandler.update(connection,statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
