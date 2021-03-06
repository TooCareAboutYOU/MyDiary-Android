package com.my.diary.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

/**
 * @author zhangshuai
 * @date 2020/11/25 13:26
 * @description
 */
public class FlashUtils {
    @SuppressLint("StaticFieldLeak")
    private static volatile FlashUtils instance=null;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private final Context context;
    private CameraManager manager;
    private Camera mCamera = null;
    private boolean status = false;//记录手电筒状态
    private FlashUtils(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        }
        this.context = context;
    }

    @SuppressLint("StaticFieldLeak")
    public static synchronized FlashUtils getInstance(Context context){
        mContext= context.getApplicationContext();
        if(instance==null){
            synchronized(FlashUtils.class){
                if(instance==null){
                    instance=new FlashUtils(context);
                }
            }
        }
        return instance;
    }

    @SuppressLint("StaticFieldLeak")
    public static FlashUtils peckInstance(){
        return instance;
    }

    //打开手电筒
    public void open() {
        if(status){//如果已经是打开状态，不需要打开
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                manager.setTorchMode("0", true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            PackageManager packageManager = context.getPackageManager();
            FeatureInfo[] features = packageManager.getSystemAvailableFeatures();
            for (FeatureInfo featureInfo : features) {
                if (PackageManager.FEATURE_CAMERA_FLASH.equals(featureInfo.name)) { // 判断设备是否支持闪光灯
                    if (null == mCamera) {
                        mCamera = Camera.open();
                    }
                    Camera.Parameters parameters = mCamera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    mCamera.setParameters(parameters);
                    mCamera.startPreview();
                }
            }
        }
        status = true;//记录手电筒状态为打开
    }

    //关闭手电筒
    public void close() {
        if(!status){//如果已经是关闭状态，不需要打开
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                manager.setTorchMode("0", false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }
        status = false;//记录手电筒状态为关闭
    }

    //改变手电筒状态
    public void converse(){
        if(status){
            close();
        }else{
            open();
        }
    }
}
