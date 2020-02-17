package com.study.mybatis.session;

import com.study.mybatis.configurate.Configurate;
import com.study.mybatis.connection.DataSource;
import com.study.mybatis.executor.Executor;
import com.study.mybatis.resultset.ResultHandler;
import com.study.mybatis.statement.SimpleStatementHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建sqlsession供用户使用的接口
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SqlSessionFactory {
    private Configurate configurate;

    public SqlSession openSession() {
        Executor executor = new Executor();
        executor.setConfigurate(configurate);
        executor.setStatementHandler(new SimpleStatementHandler());
        executor.setResultHandler(new ResultHandler());
        SqlSession sqlSession = new DefaultSqlSession(configurate, executor, true,this.configurate.getEnvironment().getDataSource());
        return sqlSession;
    }
}
