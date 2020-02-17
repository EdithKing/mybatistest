package com.study.mybatis.session;

import com.study.mybatis.statement.MapperHandler;

import java.util.List;

/**
 * 每个会话可进行的操作，查询，更新，事务提交，回滚，获取mapper对象等
 * @param <E>
 */
public interface SqlSession<E> {
    /**
     * 查询一条
     * @param mapperHandler
     * @param args
     * @return
     */
    E queryOne(MapperHandler mapperHandler, Object... args);
    /**
     * 查询多条
     * @param mapperHandler
     * @param args
     * @return
     */
    List<E> query(MapperHandler mapperHandler, Object... args);

    /**
     * 更新操作
     * @param mapperHandler
     * @param args
     * @return
     */
    Integer update(MapperHandler mapperHandler, Object... args);

    /**
     * 根据类名获取mapper
     * @param mapperName
     * @return
     */
    E getMapper(String mapperName);

    /**
     * 通过class类获取mapper的代理对象
     * @param clazz
     * @return
     */
    E getMapper(Class clazz);

    /**
     * 事务提交
     */
    void commit();

    /**
     * 事务回滚
     */
    void rollback();
}
