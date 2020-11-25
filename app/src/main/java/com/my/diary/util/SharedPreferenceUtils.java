package com.my.diary.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.my.diary.app.MyDiaryApplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;

/**
 * @author zhangshuai
 * @date 2020/11/23 14:59
 * @description
 */
public class SharedPreferenceUtils {
    //SharedPreference的name为键
    private static final SimpleArrayMap<String,SharedPreferenceUtils> mCaches=new SimpleArrayMap<>();
    private SharedPreferences mPreferences;

    public SharedPreferenceUtils(final String spName,final int mode) {
        mPreferences= MyDiaryApplication.get().getSharedPreferences(spName, mode);
    }

    public static SharedPreferenceUtils getInstance(String spName){
        SharedPreferenceUtils spUtils=mCaches.get(spName);
        if (spUtils == null) {
            spUtils=new SharedPreferenceUtils(spName, Context.MODE_PRIVATE);
            mCaches.put(spName,spUtils);
        }
        return spUtils;
    }

    public void put(@NonNull final String key, final String value){
        this.mPreferences.edit().putString(key, value).apply();
    }

    public String get(@NonNull final String key){
        return this.mPreferences.getString(key,"");
    }

    public void remove(@NonNull final String key){
        this.mPreferences.edit().remove(key).apply();
    }


}
