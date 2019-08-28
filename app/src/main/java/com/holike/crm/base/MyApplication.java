package com.holike.crm.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import android.util.DisplayMetrics;
import android.util.Log;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.holike.crm.BuildConfig;
import com.holike.crm.R;
import com.holike.crm.helper.MyLifecycleHelper;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;

import cn.jpush.android.api.JPushInterface;

import static com.umeng.commonsdk.framework.UMModuleRegister.getAppContext;

/*
 *                   _ooOoo_
 *                  o8888888o
 *                  88" . "88
 *                  (| -_- |)
 *                  O\  =  /O
 *               ____/`---'\____
 *             .'  \\|     |//  `.
 *            /  \\|||  :  |||//  \
 *           /  _||||| -:- |||||-  \
 *           |   | \\\  -  /// |   |
 *           | \_|  ''\---/''  |   |
 *           \  .-\__  `-`  ___/-. /
 *         ___`. .'  /--.--\  `. . __
 *      ."" '<  `.___\_<|>_/___.'  >'"".
 *     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *     \  \ `-.   \_ __\ /__ _/   .-` /  /
 *======`-.____`-.___\_____/___.-`____.-'======
 *                   `=---='
 *^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *         佛祖保佑       永无BUG
 */

public class MyApplication extends Application {
    public int screenWidth, screenHeight;
    private static MyApplication myApplication;
    public MyLifecycleHelper lifecycleHelper;

    public static MyApplication getInstance() {
        return myApplication;
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new WaterDropHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new BallPulseFooter(context);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        lifecycleHelper = new MyLifecycleHelper();
        getDisplay();
        registerActivityLifecycleCallbacks(lifecycleHelper);
        initJpush();
        initUm();
        BlockCanary.install(this, new AppContext()).start();
        CrashHandler.getInstance().init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
//        SophixManager.getInstance().queryAndLoadNewPatch();
        initBugly();
    }

    protected void initJpush() {
        JPushInterface.setDebugMode(BuildConfig.LOG_DEBUG);
        JPushInterface.init(this);
    }

    /**
     * 初始化友盟
     */
    private void initUm() {
        UMConfigure.setLogEnabled(BuildConfig.LOG_DEBUG);
        UMConfigure.setEncryptEnabled(true);
        UMConfigure.init(this, BuildConfig.UM_KEY, "holike", UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    /**
     * 获取屏幕分辨率
     */
    private void getDisplay() {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    /**
     * 获取当前activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        return lifecycleHelper.getCurrentActivity();
    }

    /**
     * 判断应用是否前台运行
     *
     * @return
     */
    public boolean isForeground() {
        return lifecycleHelper.isApplicationInForeground();
    }

    /**
     * 判断是否退出了应用
     *
     * @return
     */
    public boolean isExit() {
        return lifecycleHelper.isExit();
    }

    //参数设置
    public class AppContext extends BlockCanaryContext {
        private static final String TAG = "AppContext";

        @Override
        public String provideQualifier() {
            String qualifier = "";
            try {
                PackageInfo info = getAppContext().getPackageManager()
                        .getPackageInfo(getAppContext().getPackageName(), 0);
                qualifier += info.versionCode + "_" + info.versionName + "_YYB";
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "provideQualifier exception", e);
            }
            return qualifier;
        }

        @Override
        public int provideBlockThreshold() {
            return 500;
        }

        @Override
        public boolean displayNotification() {
            return BuildConfig.DEBUG;
        }

        @Override
        public boolean stopWhenDebugging() {
            return false;
        }
    }

    private void initBugly() {
        if (!BuildConfig.DEBUG) {
            CrashReport.initCrashReport(this, getString(R.string.bugly_AppID), false);
        }
    }
}
