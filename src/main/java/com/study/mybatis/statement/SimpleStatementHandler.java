package com.study.mybatis.statement;

import com.study.mybatis.resultset.ResultHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.List;

/**
 * 简单的java.sql.statement执行结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleStatementHandler implements StatementHandler {
    private MapperHandler mapperHandler;

    @Override
    public Integer update(Connection connection,Statement statement) throws SQLException {
        Integer result = ((PreparedStatement) statement).executeUpdate();
        return result;
    }

    @Override
    public <E> List<E> query(Connection connection,Statement statement, ResultHandler resultHandler) throws SQLException {
        ResultSet resultSet = ((PreparedStatement) statement).executeQuery();
        return resultHandler.handleResultSets(this, resultSet);
    }

}
