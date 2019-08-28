package com.holike.crm.model.fragment;

import com.holike.crm.base.BaseModel;
import com.holike.crm.bean.LogisticsInfoBean;
import com.holike.crm.bean.ReceiptDeliveryManifestBean;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;
import com.holike.crm.util.Constants;
import com.holike.crm.util.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogisticsInfoModel implements BaseModel {

    public void getData(String orderId, final LogisticsInfoModel.LogisticsInfoListener listener) {

        Map<String, String> header = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        header.put(Constants.CLIENT, SharedPreferencesUtils.getString(Constants.CLIENT, ""));
        MyHttpClient.postByCliId(UrlPath.URL_GET_LOGISTICS_INFO, header, params, new RequestCallBack<LogisticsInfoBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(LogisticsInfoBean results) {
                listener.success(results);
            }
        });
    }

    public interface LogisticsInfoListener {
        void success(LogisticsInfoBean messageBean);

        void failed(String failed);
    }
}
