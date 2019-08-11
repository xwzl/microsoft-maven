package com.cloud.common.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;

/**
 * @author xuweizhi
 * @date 2019/05/20 19:31
 */
public class JsonUtil {


    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 转换为json字符串
     */
    @Nullable
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    @SuppressWarnings({"rawtypes","unchecked"})
    public static Object fromJson(String string, Class classType) {
        try {
            return objectMapper.readValue(string, classType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    @SuppressWarnings("rawtypes")
    public static Object fromJson(String string, TypeReference typeReference) {
        try {
            return objectMapper.readValue(string, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
