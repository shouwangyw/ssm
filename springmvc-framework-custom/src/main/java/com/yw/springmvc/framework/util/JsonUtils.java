package com.yw.springmvc.framework.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author yangwei
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     */
    public static String object2Json(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return StringUtils.EMPTY;
    }

    /**
     * 将json结果集转化为对象
     */
    public static <T> T json2Pojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     */
    public static <T> List<T> json2List(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return Collections.emptyList();
    }

}