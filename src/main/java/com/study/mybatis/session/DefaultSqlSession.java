package com.study.mybatis.session;

import com.study.mybatis.configurate.Configurate;
import com.study.mybatis.connection.DataSource;
import com.study.mybatis.executor.Executor;
import com.study.mybatis.proxy.MapperProxy;
import com.study.mybatis.statement.MapperHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;

/**
 * 一个sqlsession会话固定一个connection，方便事务提交，回滚
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultSqlSession implements SqlSession {
    private Configurate configurate;
    private Executor executor;
    private Boolean autoCommit;
    private DataSource dataSource;
    private Connection connection;

    public DefaultSqlSession(Configurate configurate, Executor executor, boolean autoCommit,DataSource dataSource) {
        this.configurate = configurate;
        this.executor = executor;
        this.autoCommit = autoCommit;
        this.dataSource = dataSource;
        try {
            connection = dataSource.getConnection();
        }catch (Exception e){

        }
    }

    @Override
    public List query(MapperHandler mapperHandler, Object... args) {
        return this.getExecutor().query(connection,mapperHandler, args);
    }

    @Override
    public Object getMapper(String mapperName) {
        if (mapperName != null && mapperName.length() > 0) {
            return getMapper(this.getConfigurate().getClassNameMap().get(mapperName));
        }
        return null;
    }

    @Override
    public Object getMapper(Class clazz) {
        if (clazz != null) {
            if (this.configurate.getClassMapperHandlerMap().containsKey(clazz)) {
                return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MapperProxy(clazz, this));
            }
        }
        return null;
    }

    @Override
    public Object queryOne(MapperHandler mapperHandler, Object... args) {
        List<Object> list = this.query(mapperHandler, args);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer update(MapperHandler mapperHandler, Object... args) {
        if(!autoCommit){

        }
        try {
            return this.getExecutor().update(connection,mapperHandler, args);
        }catch (Exception e){
            rollback();
        }finally {
            commit();
        }
        return null;
    }

    @Override
    public void commit() {
        try {
            if (autoCommit) {
                connection.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
