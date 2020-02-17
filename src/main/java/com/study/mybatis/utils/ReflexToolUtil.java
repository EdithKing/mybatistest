package com.study.mybatis.utils;

import java.lang.reflect.Field;

/**
 * 反射工具类
 */

public class ReflexToolUtil {
    /**
     * 为对象的属性赋值
     * @param object
     * @param fieldName
     * @param value
     * @throws Exception
     */
    public static void setField(Object object, String fieldName, Object value) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field temp : fields) {
            if (temp.getName().equalsIgnoreCase(fieldName)) {
                temp.setAccessible(true);
                temp.set(object, value);
            }
        }
    }


}
