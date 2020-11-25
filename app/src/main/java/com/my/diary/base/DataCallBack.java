package com.my.diary.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangshuai
 * @date 2020/11/23 14:39
 * @description
 */
public interface DataCallBack<T> {

    /**
     * 数据请求成功
     */
    void onSuccess(T data);

    /**
     * 数据请求失败
     * @param errorMsg
     */
    void onError(String errorMsg);

}
