package com.study.mybatis.configurate;

import com.study.mybatis.statement.MapperHandler;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * config文件配置类
 */
@Data
public class Configurate {

    private Environment environment;
    private Set<String> resources = new HashSet<>();
    private Map<String, MapperHandler> mapperHandlerMap = new HashMap<>();
    private Map<Class, MapperHandler> classMapperHandlerMap = new HashMap<>();
    private Map<String, Class> classNameMap = new HashMap<>();

    public boolean checkResource(String resource) {
        return resources.contains(resource);
    }

    public void addMapperHandler(MapperHandler mapperHandler) {
        mapperHandlerMap.put(mapperHandler.getId(), mapperHandler);
        classNameMap.put((mapperHandler.getInterfaceName().getSimpleName()), mapperHandler.getInterfaceName());
        classMapperHandlerMap.put(mapperHandler.getInterfaceName(), mapperHandler);
    }


}
