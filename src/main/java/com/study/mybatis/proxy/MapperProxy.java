package com.study.mybatis.proxy;

import com.study.mybatis.session.DefaultSqlSession;
import com.study.mybatis.session.SqlSession;
import com.study.mybatis.statement.MapperHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * 实现Mapper接口的代理对象
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapperProxy implements InvocationHandler {
    private Class clazz;
    private SqlSession sqlSession;

    /**
     * 动态代理对Mapper接口的对象的方法执行进行处理
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        if (args != null && args.length == 1) {
            if (args[0] instanceof Object[]) {
                args = (Object[]) args[0];
            }
        }
        String statementName = clazz.getTypeName() + "." + method.getName();
        MapperHandler mapperHandler = ((DefaultSqlSession) this.sqlSession).getConfigurate().getMapperHandlerMap().get(statementName);
        if (mapperHandler.getSqlType().equalsIgnoreCase("select")) {
            return sqlSession.query(mapperHandler, args);
        } else {
            return sqlSession.update(mapperHandler, args);
        }
    }
}
