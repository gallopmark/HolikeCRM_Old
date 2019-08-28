package com.holike.crm.helper;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;

/**
 * Created by wqj on 2017/10/19.
 * 判断应用是否前台运行帮助类
 */

public class MyLifecycleHelper implements Application.ActivityLifecycleCallbacks {
    private int created;
    private int resumed;
    private int paused;
    private int started;
    private int stopped;
    private int destroyed;

    private WeakReference<Activity> activityWeakReference;


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ++created;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        activityWeakReference = new WeakReference<Activity>(activity);
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ++destroyed;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public boolean isApplicationVisible() {
        return started > stopped;
    }

    public boolean isApplicationInForeground() {// 当所有 Activity 的状态中处于 resumed 的大于 paused 状态的，即可认为有Activity处于前台状态中
        return resumed > paused;
    }

    public boolean isExit() {//当所有 Activity 的状态中处于 destroyed 的大于 created 状态即退出应用
        return destroyed >= created;
    }

    /**
     * 获取当前打开的界面
     *
     * @return
     */
    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (activityWeakReference != null) {
            currentActivity = activityWeakReference.get();
        }
        return currentActivity;
    }
}
