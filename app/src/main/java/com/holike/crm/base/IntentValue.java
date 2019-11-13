package com.holike.crm.base;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.holike.crm.bean.DistributionStoreBean;
import com.holike.crm.bean.HomepageBean;
import com.holike.crm.bean.UpdateBean;
import com.holike.crm.http.MyJsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pony on 2019/7/11.
 * Copyright holike possess 2019.
 * 替换activity、fragment等大数据传值
 */
public class IntentValue {
    private volatile static IntentValue mInstance;
    private Map<String, Object> mValueMap;

    private static final String UPDATE_BEAN = "updateBean";
    private static final String HOME_BEAN = "homeBean";

    private static final String EMPLOYEE_EDIT_RESULT = "employee_edit_result";

    private IntentValue() {
        mValueMap = new HashMap<>();
    }

    public static IntentValue getInstance() {
        if (mInstance == null) {
            synchronized (IntentValue.class) {
                if (mInstance == null) {
                    mInstance = new IntentValue();
                }
            }
        }
        return mInstance;
    }

    /*首页数据保存到缓存中，提供其他页面调用*/
    public void setHomeBean(HomepageBean bean) {
        mValueMap.put(HOME_BEAN, bean);
    }

    @Nullable
    public HomepageBean getHomeBean() {
        Object object = mValueMap.get(HOME_BEAN);
        if (object == null) return null;
        if (object instanceof HomepageBean) {
            return (HomepageBean) object;
        }
        return null;
    }

    public void setUpdateBean(UpdateBean bean) {
        mValueMap.put(UPDATE_BEAN, bean);
    }

    @Nullable
    public UpdateBean getUpdateBean() {
        Object object = mValueMap.get(UPDATE_BEAN);
        if (object == null) return null;
        if (object instanceof UpdateBean) {
            return (UpdateBean) object;
        }
        return null;
    }

    /*门店数据*/
    @NonNull
    public List<DistributionStoreBean> getShopData() {
        HomepageBean bean = getHomeBean();
        if (bean == null || bean.getTypeList() == null || bean.getTypeList().getShopData().isEmpty()) {
            return new ArrayList<>();
        }
        return bean.getTypeList().getShopData();
    }

    public void removeEmployeeEditResult() {
        remove(EMPLOYEE_EDIT_RESULT);
    }

    public void put(String key, String value) {
        mValueMap.put(key, value);
    }

    public void put(String key, Object object) {
        mValueMap.put(key, object);
    }

    public void putAsJson(String key, Object object) {
        mValueMap.put(key, MyJsonParser.fromBeanToJson(object));
    }

    @Nullable
    public Object get(String key) {
        return mValueMap.get(key);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T getAs(String key, T t) {
        Object o = mValueMap.get(key);
        if (o == null) return null;
        return (T) o;
    }

    @Nullable
    public <T> T get(String key, Class<T> clazz) {
        if (get(key) instanceof String) {
            String json = (String) get(key);
            return MyJsonParser.fromJsonToBean(json, clazz);
        }
        return null;
    }

    @Nullable
    public <T> List<T> getAsList(String key, Class<T> clazz) {
        if (get(key) instanceof String) {
            String json = (String) get(key);
            return MyJsonParser.fromJsonToList(json, clazz);
        }
        return null;
    }

    public void remove(String key) {
        mValueMap.remove(key);
    }

    public Object removeBy(String key){
        return mValueMap.remove(key);
    }
}
