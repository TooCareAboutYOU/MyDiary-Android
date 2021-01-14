package com.text.module.ui.threads;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author zhangshuai
 * @date 2021/1/13 20:20
 * @description
 */
public class MyDefaultTaskExecutor extends MyTaskExecutor {

    private final Object mLock = new Object();

    private final ExecutorService mDiskIO = Executors.newFixedThreadPool(5, new ThreadFactory() {
        private static final String THREAD_NAME_STEM = "arch_disk_io_%d";

        private final AtomicInteger mThreadId = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName(String.format(THREAD_NAME_STEM, mThreadId.getAndIncrement()));
            return t;
        }
    });

    @Nullable
    private volatile Handler mMainHandler;

    private static Handler createAsync(@NonNull Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Handler.createAsync(looper);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                return Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, boolean.class)
                        .newInstance(looper, null, true);
            } catch (IllegalAccessException ignored) {
            } catch (InstantiationException ignored) {
            } catch (NoSuchMethodException ignored) {
            } catch (InvocationTargetException e) {
                return new Handler(looper);
            }
        }
        return new Handler(looper);
    }

    @Override
    public void executeOnDiskIO(Runnable runnable) {
        mDiskIO.execute(runnable);
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        if (mMainHandler == null) {
            synchronized (mLock) {
                if (mMainHandler == null) {
                    mMainHandler = createAsync(Looper.getMainLooper());
                }
            }
        }
        mMainHandler.post(runnable);
    }

    @Override
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
