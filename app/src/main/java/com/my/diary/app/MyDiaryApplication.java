package com.my.diary.app;

import android.app.Application;

import com.tencent.mmkv.MMKV;

/**
 * @author zhangshuai
 * @date 2020/11/20 14:38
 * @description
 */
public class MyDiaryApplication extends Application {

    private static Application mApplication;
    public static MMKV sMMKV;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        initMMKV();
    }

    private void initMMKV(){
        MMKV.initialize(this);
        sMMKV=MMKV.defaultMMKV();
    }

    public static Application get(){
        return mApplication;
    }


    public static MMKV getMMKV(){
        return sMMKV;
    }
}
