package com.my.diary.model;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author zhangshuai
 * @date 2020/11/23 14:31
 * @description
 */
public class DiaryModel {

    private String id; //日记的唯一标识
    private String title; //日记标题
    private String description;//日记内容

    public DiaryModel(@Nullable String title,@Nullable String description) {
        this(title,description, UUID.randomUUID().toString()); //通过UUID生成唯一标识
    }

    public DiaryModel(@Nullable String title, @Nullable String description, @NonNull String id) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
