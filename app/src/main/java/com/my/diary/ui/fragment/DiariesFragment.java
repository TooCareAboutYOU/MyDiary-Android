package com.my.diary.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.my.diary.R;
import com.my.diary.ui.controller.DiariesController;


public class DiariesFragment extends Fragment {

    private View mRootView;
    private DiariesController mController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mController=new DiariesController(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.fragment_diaries, container, false);
        this.mController.setDiariesListView(mRootView.findViewById(R.id.recyclerView_diaries));
        return mRootView;
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