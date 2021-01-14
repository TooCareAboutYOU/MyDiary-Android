package com.text.module.ui.threads;

import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.ArchTaskExecutor;

/**
 * @author zhangshuai
 * @date 2021/1/13 20:23
 * @description
 */
public class MyArchTaskExecutor extends MyTaskExecutor {
    private static volatile MyArchTaskExecutor sInstance;
    @NonNull
    private static final Executor sMainThreadExecutor = new Executor() {
        @Override
        public void execute(Runnable command) {
            getInstance().postToMainThread(command);
        }
    };
    @NonNull
    private static final Executor sIOThreadExecutor = new Executor() {
        @Override
        public void execute(Runnable command) {
            getInstance().executeOnDiskIO(command);
        }
    };
    @NonNull
    private final MyTaskExecutor mDefaultTaskExecutor;
    @NonNull
    private MyTaskExecutor mDelegate;

    private MyArchTaskExecutor() {
        mDefaultTaskExecutor = new MyDefaultTaskExecutor();
        mDelegate = mDefaultTaskExecutor;
    }

    /**
     * Returns an instance of the task executor.
     *
     * @return The singleton ArchTaskExecutor.
     */
    @NonNull
    public static MyArchTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (ArchTaskExecutor.class) {
            if (sInstance == null) {
                sInstance = new MyArchTaskExecutor();
            }
        }
        return sInstance;
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    @NonNull
    public static Executor getIOThreadExecutor() {
        return sIOThreadExecutor;
    }

    /**
     * Sets a delegate to handle task execution requests.
     * <p>
     * If you have a common executor, you can set it as the delegate and App Toolkit components will
     * use your executors. You may also want to use this for your tests.
     * <p>
     * Calling this method with {@code null} sets it to the default TaskExecutor.
     *
     * @param taskExecutor The task executor to handle task requests.
     */
    public void setDelegate(@Nullable MyTaskExecutor taskExecutor) {
        mDelegate = taskExecutor == null ? mDefaultTaskExecutor : taskExecutor;
    }

    @Override
    public void executeOnDiskIO(Runnable runnable) {
        mDelegate.executeOnDiskIO(runnable);
    }

    @Override
    public void postToMainThread(Runnable runnable) {
        mDelegate.postToMainThread(runnable);
    }

    @Override
    public boolean isMainThread() {
        return mDelegate.isMainThread();
    }
}
