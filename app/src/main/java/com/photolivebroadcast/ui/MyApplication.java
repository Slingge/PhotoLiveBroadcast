package com.photolivebroadcast.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.view.View;

import com.lixin.amuseadjacent.app.util.abLog;
import com.photolivebroadcast.util.ImageLoaderUtil;
import com.photolivebroadcast.util.SharedPreferencesUtil;
import com.photolivebroadcast.util.StatickUtil;


/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class MyApplication extends MultiDexApplication {
    public static Context CONTEXT;

    private static MyApplication myApplication;


//String json = "{\"cmd\":\"getMsg\"" + "}";
// String json = "{\"cmd\":\"upPrize\",\"prizeId\":\"" + prizeId  + "\",\"userNme\":\"" + MyApplication.getUserName() + "\"}";


    public static String CameraPath = Environment.getExternalStorageDirectory().getPath() + "/带路圈/";

    public static MyApplication getInstance() {
        // if语句下是不会走的，Application本身已单例
        if (myApplication == null) {
            synchronized (MyApplication.class) {
                if (myApplication == null) {
                    myApplication = new MyApplication();
                }
            }
        }
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = getApplicationContext();
        myApplication = this;

        StatickUtil.INSTANCE.setUid(SharedPreferencesUtil.getSharePreStr(CONTEXT, "uid"));
        StatickUtil.INSTANCE.setPass(SharedPreferencesUtil.getSharePreStr(CONTEXT, "phone"));

        abLog.INSTANCE.setE(true);

        ImageLoaderUtil.configImageLoader(CONTEXT);
        com.nostra13.universalimageloader.utils.L.disableLogging();
    }

    /**
     * 分割 Dex 支持
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 通过类名启动Activity
     *
     * @param targetClass
     */
    public static void openActivity(Context context, Class<?> targetClass) {
        openActivity(context, targetClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param targetClass
     * @param extras
     */
    public static void openActivity(Context context, Class<?> targetClass,
                                    Bundle extras) {
        Intent intent = new Intent(context, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void openActivityForResult(Activity activity,
                                             Class<?> targetClass, Bundle extras, int requestCode) {
        Intent intent = new Intent(activity, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * Fragment中无效
     */
    public static void openActivityForResult(Activity activity,
                                             Class<?> targetClass, int requestCode) {
        openActivityForResult(activity, targetClass, null, requestCode);
    }

    public static Context getContext() {
        return CONTEXT;
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


}
