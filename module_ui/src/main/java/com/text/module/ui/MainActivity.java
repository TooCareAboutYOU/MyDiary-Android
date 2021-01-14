package com.text.module.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.text.module.ui.threads.MyArchTaskExecutor;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * UI 标签的联动使用
 */
public class MainActivity extends AppCompatActivity {


    private static final ArrayList<String> samples = new ArrayList();

    static {
        if (samples.size() == 0) {
            samples.add("750x80.svga");
            samples.add("alarm.svga");
            samples.add("angel.svga");
            samples.add("Castle.svga");
            samples.add("EmptyState.svga");
            samples.add("Goddess.svga");
            samples.add("gradientBorder.svga");
            samples.add("heartbeat.svga");
            samples.add("matteBitmap.svga");
            samples.add("matteBitmap_1.x.svga");
            samples.add("matteRect.svga");
            samples.add("MerryChristmas.svga");
            samples.add("posche.svga");
            samples.add("Rocket.svga");
            samples.add("rose.svga");
            samples.add("rose_2.0.0.svga");
        }
    }

    private SVGAImageView svgImage;
    private List<String> list;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main2);
        svgImage = findViewById(R.id.svgaImageView);
//        svgImage = new SVGAImageView(this);
        svgImage.setBackgroundColor(Color.BLACK);
//        svgImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                svgImage.stepToFrame(currentIndex++, false);
//            }
//        });

        init();

        svgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyArchTaskExecutor.getInstance().executeOnDiskIO(new MyRunnable("executeOnDiskIO"));
                MyArchTaskExecutor.getInstance().postToMainThread(new MyRunnable("postToMainThread"));
                MyArchTaskExecutor.getIOThreadExecutor().execute(new MyRunnable("getIOThreadExecutor "));
                MyArchTaskExecutor.getMainThreadExecutor().execute(new MyRunnable("getMainThreadExecutor "));
            }
        });

    }

    private void init() {
        MySvgaParse.init(this);
        MySvgaParse.getInstance().loadFromAssets(samples.get(1), svgImage);
        initRecyclerView();

    }

    private String randomSample() {
        return samples.get((int) Math.floor(Math.random() * samples.size()));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));
        list = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            list.add("pos: " + i);
        }
        mAdapter = new ItemAdapter(this, list, svgImage);
        recyclerView.setAdapter(mAdapter);
    }

    public static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        private final Context mContext;
        private final List<String> mList;
        private final SVGAImageView mSVGAImageView;
        private SVGAParser localSvgAParser;

        public ItemAdapter(Context context, List<String> list, SVGAImageView svgaImageView) {
            this.mContext = context;
            this.mList = list;
            this.mSVGAImageView = svgaImageView;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.layout_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.acTvTxt.setText(mList.get(position));
            holder.itemView.setOnClickListener(v -> MySvgaParse.getInstance().loadFromAssets(samples.get(position), mSVGAImageView));
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

    private class MyRunnable implements Runnable {

        String mName;

        public MyRunnable(String name) {
            mName = name;
        }

        @Override
        public void run() {
            Log.i("test", "当前线程来自：" + mName + ", 线程名" + Thread.currentThread());
        }
    }
}