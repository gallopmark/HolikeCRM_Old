package com.holike.crm.model.fragment;

import com.holike.crm.base.BaseModel;
import com.holike.crm.bean.ActiveMarketBean;
import com.holike.crm.bean.CustomerSatisfactionBean;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pony on 2019/9/20.
 * Copyright holike possess 2019.
 */
public class CustomerSatisfactionModel implements BaseModel {

    public void onQueryRequest(String type, String cityCode, String datetime, RequestCallBack<CustomerSatisfactionBean> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("cityCode", cityCode);
        params.put("time", datetime);
        MyHttpClient.postByTimeout(UrlPath.URL_EVALUATION_REPORT, null, params, 60, callBack);
    }
}
