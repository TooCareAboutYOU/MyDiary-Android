package com.text.module.ui;

import android.annotation.SuppressLint;
import android.content.Context;

import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import androidx.annotation.NonNull;

/**
 * @author zhangshuai
 * @date 2021/1/11 11:19
 * @description
 */
class MySvgaParse {

    @SuppressLint("StaticFieldLeak")
    private static final MySvgaParse instance = null;
    private static SVGAParser mSVGAParser;

    private MySvgaParse(){}
    @SuppressLint("StaticFieldLeak")
    public static MySvgaParse getInstance(){  return MySvgaParseHolder.instance;  }

    public static void init(@NonNull Context context) {
        mSVGAParser = SVGAParser.Companion.shareParser();
        mSVGAParser.init(context);
    }

    public void loadFromAssets(@NonNull String path, SVGAImageView imgView){
        mSVGAParser.decodeFromAssets(path, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(SVGAVideoEntity svgaVideoEntity) {
                imgView.setVideoItem(svgaVideoEntity);
                imgView.stepToFrame(0, true);
            }

            @Override
            public void onError() {

            }
        });
    }

    private static class MySvgaParseHolder{
        @SuppressLint("StaticFieldLeak")
        private static final MySvgaParse instance=new MySvgaParse();
    }


}
