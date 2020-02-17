package com.study.mybatis.resultset;

import com.study.mybatis.statement.SimpleStatementHandler;
import com.study.mybatis.statement.StatementHandler;
import com.study.mybatis.utils.ReflexToolUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 结果集处理类
 */
@Data
@NoArgsConstructor
public class ResultHandler {

    /**
     * 处理结果集，判断用户返回的对象是不是基础数据类型，是则直接返回，要是返回一个类，那么
     * 进行反射工具类为对象的属性赋值
     * @param statementHandler
     * @param resultSet
     * @param <E>
     * @return
     * @throws SQLException
     */
    public <E> List<E> handleResultSets(StatementHandler statementHandler, ResultSet resultSet) throws SQLException {
        String type = ((SimpleStatementHandler) statementHandler).getMapperHandler().getResultType();
        Class clazz = null;
        try {
            clazz = Class.forName(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List list = new ArrayList();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        while (resultSet.next()) {
            try {
                if (type.indexOf("java.lang") != -1) {
                    list.add(resultSet.getObject(1));
                } else {
                    Object obj = clazz.newInstance();
                    for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                        String columnName = resultSetMetaData.getColumnName(i + 1);
                        Object value = resultSet.getObject(columnName);
                        ReflexToolUtil.setField(obj, columnName, value);
                    }
                    list.add(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
