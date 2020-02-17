package com.study.mybatis.connection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.Properties;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnPoolDataSourceFactory implements DataSourceFactory {

    private UnPoolDataSource unPoolDataSource;

    /**
     * 根据properties文件的内容对DataSource对象的属性赋值
     * @param var1
     */
    @Override
    public void setProperties(Properties var1) {
        Iterator keyIterator = var1.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = (String) keyIterator.next();
            switch (key) {
                case "drvier":
                    unPoolDataSource.setDriver(var1.getProperty(key));
                    break;
                case "url":
                    unPoolDataSource.setUrl(var1.getProperty(key));
                    break;
                case "username":
                    unPoolDataSource.setUsername(var1.getProperty(key));
                    break;
                case "password":
                    unPoolDataSource.setPassword(var1.getProperty(key));
                    break;
                case "commit":
                    unPoolDataSource.setAutocommit(Boolean.getBoolean(var1.getProperty(key)));
                    break;
            }
        }
    }

    /**
     * 返回数据源
     * @return
     */
    @Override
    public DataSource getDataSource() {
        return unPoolDataSource;
    }
}
