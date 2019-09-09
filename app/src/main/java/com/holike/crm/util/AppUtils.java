package com.holike.crm.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

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
        File fileDir = MyApplication.getInstance().getExternalCacheDir();
        String path;
        if (fileDir != null) {
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            path = fileDir.getAbsolutePath() + File.separator + "apk";
        } else {
            fileDir = MyApplication.getInstance().getExternalFilesDir(null);
            if (fileDir != null) {
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                path = fileDir.getAbsolutePath() + File.separator + "apk";
            } else {
                fileDir = MyApplication.getInstance().getCacheDir();
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                path = MyApplication.getInstance().getCacheDir() + File.separator + "apk";
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
        installApp(new File(apkPath));
    }

    public static void installApp(File file) {
        if (!isFileExists(file)) return;
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data;
            String type = "application/vnd.android.package-archive";
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                data = Uri.fromFile(file);
            } else {
                String authority = MyApplication.getInstance().getPackageName() + ".provider";
                data = FileProvider.getUriForFile(MyApplication.getInstance(), authority, file);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            MyApplication.getInstance().grantUriPermission(MyApplication.getInstance().getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(data, type);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getInstance().startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }
}
