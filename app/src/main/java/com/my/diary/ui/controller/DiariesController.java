package com.my.diary.ui.controller;

import android.content.Context;

import com.my.diary.app.MyDiaryApplication;
import com.my.diary.util.SharedPreferenceUtils;
import com.tencent.mmkv.MMKV;

import java.util.PrimitiveIterator;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zhangshuai
 * @date 2020/11/23 14:11
 * @description
 */
public class DiariesController {


    private Context mContext;
    private RecyclerView mRecyclerView;

    public DiariesController(Context context) {
        this.mContext = context;

    }

    /**
     * 将日记列表控件传入控制器
     *
     * @param recyclerView
     */
    public void setDiariesListView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    /**
     * 加载日记数据
     */
    public void loadDiariesData() {

    }

    /**
     * 添加新的日记
     */
    public void gotoWriteDiary() {

    }

}
