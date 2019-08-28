package com.holike.crm.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.holike.crm.bean.DistributionStoreBean;
import com.holike.crm.bean.EmployeeDetailBean;
import com.holike.crm.http.MyJsonParser;

import java.util.List;

public class LocalDataSource {

    private static final String FILE_LOCAL = "localData";
    /*店铺数据*/
    private static final String SHOP_DATA = "shopData";

    /*权限信息*/
    private static final String AUTH_INFO = "authInfo";

    public static void clear(Context context) {
        getSharedPreferences(context).edit().clear().apply();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_LOCAL, Context.MODE_PRIVATE);
    }

    public static void saveShopData(Context context, String shopData) {
        getSharedPreferences(context).edit().putString(SHOP_DATA, shopData).apply();
    }

    public static void clearShopData(Context context) {
        getSharedPreferences(context).edit().remove(SHOP_DATA).apply();
    }

    public static List<DistributionStoreBean> getShopData(Context context) {
        String json = getSharedPreferences(context).getString(SHOP_DATA, "");
        if (TextUtils.isEmpty(json)) return null;
        try {
            return new Gson().fromJson(json, new TypeToken<List<DistributionStoreBean>>() {
            }.getType());
        } catch (Exception e) {
            return null;
        }
    }

    public static void saveAuthInfo(Context context, List<EmployeeDetailBean.EmployeeAuthInfoBean> infoBeans) {
        for (EmployeeDetailBean.EmployeeAuthInfoBean bean : infoBeans) {
            if (bean.getArr2() != null && !bean.getArr2().isEmpty()) {
                for (EmployeeDetailBean.EmployeeAuthInfoBean.PArrBean arrBean : bean.getArr2()) {
                    arrBean.setIsSelect(0);
                }
            }
        }
        String authInfo = MyJsonParser.fromBeanToJson(infoBeans);
        getSharedPreferences(context).edit().putString(AUTH_INFO, authInfo).apply();
    }

    public static void clearAuthInfo(Context context) {
        getSharedPreferences(context).edit().remove(AUTH_INFO).apply();
    }

    public static List<EmployeeDetailBean.EmployeeAuthInfoBean> getAuthInfo(Context context) {
        String json = getSharedPreferences(context).getString(AUTH_INFO, "");
        if (TextUtils.isEmpty(json)) return null;
        try {
            return MyJsonParser.fromJsonToList(json, EmployeeDetailBean.EmployeeAuthInfoBean.class);
        } catch (Exception e) {
            return null;
        }
    }
}
