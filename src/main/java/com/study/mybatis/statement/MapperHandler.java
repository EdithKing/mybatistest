package com.study.mybatis.statement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对应mapper的配置文件的一些内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapperHandler {
    private String id;
    private Class interfaceName;
    private String parameterType;
    private String resultType;
    private String sql;
    private String sqlType;
}
