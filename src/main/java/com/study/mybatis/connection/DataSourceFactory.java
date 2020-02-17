package com.study.mybatis.connection;


import java.util.Properties;

public interface DataSourceFactory {

    void setProperties(Properties var1);

    DataSource getDataSource();
}
