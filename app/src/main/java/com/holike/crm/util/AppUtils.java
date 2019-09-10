package com.holike.crm.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.holike.crm.BuildConfig;
import com.holike.crm.base.MyApplication;

import java.io.File;

/**
 * Created by gallop on 2019/7/22.
 * Copyright holike possess 2019.
 */
public class AppUtils {

    /*打开app设置页面*/
    public static void openSettings(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    /*版本更新 apk存放的目录  放到cacheDir文件夹6.0以下无法调起安装（出现解析错误，解析程序包时出现问题）*/
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String getApkPath() {
        MyApplication application = MyApplication.getInstance();
        File fileDir = application.getExternalCacheDir();
        String path;
        if (fileDir != null) {
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            path = fileDir.getAbsolutePath() + File.separator + "apk";
        } else {
            fileDir = application.getExternalFilesDir(null);
            if (fileDir != null) {
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                path = fileDir.getAbsolutePath() + File.separator + "apk";
            } else {
                fileDir = application.getCacheDir();
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                path = application.getCacheDir() + File.separator + "apk";
            }
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    private static String generateApkName() {
        return BuildConfig.VERSION_NAME + "_higher_CRM.apk";
    }

    /*保存下载的apk的目标路径*/
    public static String getTargetApkPath() {
        return getApkPath() + File.separator + generateApkName();
    }

    public static void installApp(String apkPath) {
        if (!TextUtils.isEmpty(apkPath)) {
            installApp(new File(apkPath));
        }
    }

    public static void installApp(File file) {
        if (!isFileExists(file)) return;
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data;
            String type = "application/vnd.android.package-archive";
            MyApplication application = MyApplication.getInstance();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                data = Uri.fromFile(file);
            } else {
                String authority = application.getPackageName() + ".provider";
                data = FileProvider.getUriForFile(application, authority, file);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            application.grantUriPermission(application.getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(data, type);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /* 后面跟上包名，可以直接跳转到对应APP的未知来源权限设置界面。使用startActivityForResult
    是为了在关闭设置界面之后，获取用户的操作结果，然后根据结果做其他处理*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startUnknownAppSourceSetting(int requestCode) {
        MyApplication application = MyApplication.getInstance();
        Activity activity = application.getTopActivity();
        if (activity != null) {
            //注意这个是8.0新API
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + application.getPackageName()));
                activity.startActivityForResult(intent, requestCode);
            } catch (Exception ignored) {
            }
        }
    }
}
