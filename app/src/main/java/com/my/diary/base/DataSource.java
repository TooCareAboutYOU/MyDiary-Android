package com.my.diary.base;

import com.my.diary.model.DiaryModel;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author zhangshuai
 * @date 2020/11/23 14:38
 * @description
 */
public interface DataSource<T> {

    /**
     * 获取全部日记数据
     * @param callBack
     */
    void getAll(@NonNull DataCallBack<List<T>> callBack);

    /**
     * 获取具体日记数据
     * @param id
     * @param callBack
     */
    void get(@NonNull String id,@NonNull DataCallBack<T> callBack);

    /**
     * 更新某个日记数据
     * @param diary
     */
    void update(@NonNull T diary);

    /**
     * 清空日记数据
     */
    void clear();

    /**
     * 删除某个日记数据
     * @param id
     */
    void delete(@NonNull String id);

}
