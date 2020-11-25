package com.text.module.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * UI 标签的联动使用
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main2);
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            list.add("position " + i);
        }
        recyclerView.setAdapter(new ItemAdapter(this, list));
    }

    public static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        private Context mContext;
        private List<String> mList;

        public ItemAdapter(Context context, List<String> list) {
            mContext = context;
            mList = list;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.layout_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.acTvTxt.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList != null && mList.size() > 0 ? mList.size() : 0;
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {

            public AppCompatTextView acTvTxt;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                acTvTxt = itemView.findViewById(R.id.acTv_txt);
            }
        }

    }


}