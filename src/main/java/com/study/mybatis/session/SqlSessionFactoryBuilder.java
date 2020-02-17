package com.study.mybatis.session;

import com.study.mybatis.configurate.Configurate;
import com.study.mybatis.io.XmlConfigBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * sqlsessionFactory的类，进行不同的文件读取方式创建sqlsessionFactory
 *
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream is) {
        try {
            XmlConfigBuilder configBuilder = new XmlConfigBuilder(is);
            return build(configBuilder.parse());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SqlSessionFactory build(Configurate configurate) {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory(configurate);
        return sqlSessionFactory;
    }

}
