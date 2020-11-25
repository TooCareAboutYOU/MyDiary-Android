package com.my.diary.ui.data;

import android.content.Context;
import com.google.gson.reflect.TypeToken;
import com.my.diary.base.DataCallBack;
import com.my.diary.base.DataSource;
import com.my.diary.model.DiaryModel;
import com.my.diary.util.GsonUtils;
import com.my.diary.util.SharedPreferenceUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * @author zhangshuai
 * @date 2020/11/23 14:45
 * @description 本地持久化数据工具
 */
class DiariesLocalDataSource implements DataSource<DiaryModel> {

    //存储日记数据SharedPreference名称
    private static final String DAIRY_DATA_NAME = "dairy_data";

    private static final String ALL_DAIRY = "all_diary";

    private static volatile DiariesLocalDataSource instance = null;
    private SharedPreferenceUtils mPreferenceUtils;

    //从屋里缓存加载加载到内存缓存中
    private Map<String, DiaryModel> LOCAL_DATA = new LinkedHashMap<>();

    private DiariesLocalDataSource() {
        mPreferenceUtils = SharedPreferenceUtils.getInstance(DAIRY_DATA_NAME);
        String dairyStr = mPreferenceUtils.get(ALL_DAIRY);
        LOCAL_DATA = json2Obj(dairyStr);
        if (LOCAL_DATA.isEmpty()) {
            LOCAL_DATA=MockDiaries.mock();
        }

    }

    private String obj2Json() {
        return GsonUtils.toJson(LOCAL_DATA);
    }

    private LinkedHashMap<String, DiaryModel> json2Obj(String diaryStr) {
        return GsonUtils.fromJson(diaryStr, new TypeToken<LinkedHashMap<String, DiaryModel>>() {
        }.getType());
    }

    private static Context mContext;

    public static synchronized DiariesLocalDataSource getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (instance == null) {
            synchronized (DiariesLocalDataSource.class) {
                if (instance == null) {
                    instance = new DiariesLocalDataSource();
                }
            }
        }
        return instance;
    }


    @Override
    public void getAll(@NonNull DataCallBack<List<DiaryModel>> callBack) {
        if (LOCAL_DATA.isEmpty()) {
            callBack.onError("获取所有数据为空！");
        } else {
            callBack.onSuccess(new ArrayList<>(LOCAL_DATA.values()));
        }
    }

    @Override
    public void get(@NonNull String id, @NonNull DataCallBack<DiaryModel> callBack) {
        DiaryModel model = LOCAL_DATA.get(id);
        if (model != null) {
            callBack.onSuccess(model);
        } else {
            callBack.onError("不存在 " + id + " 数据！");
        }
    }

    @Override
    public void update(@NonNull DiaryModel diary) {
        LOCAL_DATA.put(diary.getId(), diary);
        mPreferenceUtils.put(ALL_DAIRY, obj2Json());
    }

    @Override
    public void clear() {
        LOCAL_DATA.clear();
        mPreferenceUtils.remove(ALL_DAIRY);
    }

    @Override
    public void delete(@NonNull String id) {
        LOCAL_DATA.remove(id);
        mPreferenceUtils.put(ALL_DAIRY, obj2Json());
    }



    /**
     * 填充测试数据
     */
    public static class MockDiaries{
        public static final String DESCRIPTION="上海2例本土病例曾共同暴露于北美入境的航空集装器";
        public static Map<String,DiaryModel> mock(){
            return mock(new LinkedHashMap<String,DiaryModel>());
        }


        public static Map<String,DiaryModel> mock(Map<String,DiaryModel> data){
            for (int i = 1; i < 11; i++) {
                DiaryModel diaryModel=getDiaryModel("我的日记第"+i+"章节");
                data.put(diaryModel.getId(),diaryModel);
            }

            return data;
        }

        @NonNull
        public static DiaryModel getDiaryModel(String title){
            return new DiaryModel(title,DESCRIPTION);
        }

    }
}
