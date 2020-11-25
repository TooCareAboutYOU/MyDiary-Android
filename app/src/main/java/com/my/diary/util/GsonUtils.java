package com.my.diary.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.sql.BatchUpdateException;

/**
 * @author zhangshuai
 * @date 2020/11/23 15:19
 * @description
 */
public class GsonUtils {

    public static final Gson GSON=createGson();

    /**
     * 创建Gson实例
     * @return
     */
    private static Gson createGson() {
        final GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.serializeNulls();
        return gsonBuilder.create();
    }

    public static String toJson(final Object obj){
        return GSON.toJson(obj);
    }

    /**
     * /将json格式的字符串转换为对象T
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(final String json, final Type type){
        return GSON.fromJson(json,type);
    }


}
