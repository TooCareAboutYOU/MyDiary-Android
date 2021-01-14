package com.my.diary.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.my.diary.R;
import com.my.diary.ui.controller.DiariesController;
import com.my.diary.util.FlashUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;


public class DiariesFragment extends Fragment {

    private View mRootView;
    private DiariesController mController;
    private AppCompatButton acBtnLight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mController=new DiariesController(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.fragment_diaries, container, false);
        this.acBtnLight=mRootView.findViewById(R.id.acBtnLight);
        ((AppCompatTextView)mRootView.findViewById(R.id.acTxt)).setText(DiariesFragment.class.getName()+"\n"+DiariesFragment.class.getSimpleName());

        this.mController.setDiariesListView(mRootView.findViewById(R.id.recyclerView_diaries));
        init();
        return mRootView;
    }

    private void init(){
        this.acBtnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlashUtils.getInstance(getActivity()).converse();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mController.loadDiariesData();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_write,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:{
                this.mController.gotoWriteDiary();
                return true;
            }
            default:break;
        }
        return false;
    }
}