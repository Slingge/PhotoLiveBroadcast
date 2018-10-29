package com.photolivebroadcast.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lixin.amuseadjacent.app.util.abLog;
import com.photolivebroadcast.ui.establish.SginModel;
import com.photolivebroadcast.util.ImageLoaderUtil;
import com.photolivebroadcast.util.SharedPreferencesUtil;
import com.photolivebroadcast.util.StatickUtil;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;


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
        StatickUtil.INSTANCE.setHeaderUrl(SharedPreferencesUtil.getSharePreStr(CONTEXT, "headerUrl"));
        String userStr = SharedPreferencesUtil.getSharePreStr(CONTEXT, "model");
        if (!TextUtils.isEmpty(userStr)) {
            StatickUtil.INSTANCE.setUserModel(new Gson().fromJson(userStr, SginModel.class).getData());
        }


        abLog.INSTANCE.setE(true);
//        CrashHandler catchExcep = new CrashHandler(this);
//        Thread.setDefaultUncaughtExceptionHandler(catchExcep);

        ImageLoaderUtil.configImageLoader(CONTEXT);
        com.nostra13.universalimageloader.utils.L.disableLogging();

        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx0d0b4d8b589ef4e6", "ee455dc7b28cbf41f9370d53acd6bbb0");
//        PlatformConfig.setQQZone("1106693507", "ns8Zb9Eid2yVXFFs");



        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120000L, TimeUnit.MILLISECONDS)
                .readTimeout(120000L, TimeUnit.MILLISECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .build();
        OkHttpUtils.initClient(okHttpClient);


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
