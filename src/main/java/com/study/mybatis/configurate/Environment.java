package com.study.mybatis.configurate;

import com.study.mybatis.connection.DataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 环境类，配置了相对应的数据源
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Environment {
    private DataSource dataSource;
}
