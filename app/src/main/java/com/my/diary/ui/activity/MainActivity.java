package com.my.diary.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.my.diary.R;
import com.my.diary.ui.fragment.DiariesFragment;
import com.my.diary.util.ActivityUtils;

/**
 * @author zhangshuai
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivities";

    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 网络数据地址
     */
    public native String stringFromJNI();

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initToolBar();
        initFragment();
    }

    private void initToolBar() {
        mToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
    }


    private void initFragment() {
        DiariesFragment diariesFragment = getDiariesFragment();
        if (diariesFragment == null) {
            diariesFragment = new DiariesFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), diariesFragment, R.id.content);
        }
    }

    private DiariesFragment getDiariesFragment() {
        return (DiariesFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }

}