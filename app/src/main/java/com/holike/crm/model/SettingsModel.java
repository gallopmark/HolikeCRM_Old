package com.holike.crm.model;

import com.holike.crm.base.BaseModel;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

public class SettingsModel implements BaseModel {

    public void setRule(String id, String param2, RequestCallBack<String> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id == null ? "" : id);
        params.put("param2", param2 == null ? "" : param2);
        MyHttpClient.postByCliId(UrlPath.URL_SETTINGS_RULE, null, params, callBack);
    }
}
