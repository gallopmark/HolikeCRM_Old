package com.holike.crm.model.fragment;

import com.holike.crm.base.BaseModel;
import com.holike.crm.bean.NewRetailBean;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqj on 2018/5/31.
 * 新零售
 */

public class NewRetailModel implements BaseModel {

    /**
     * 获取数据
     *
     * @param cityCode
     * @param time
     * @param type
     * @param listener
     */
    public void getData(String cityCode, String time, String type, final GetDataListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("cityCode", cityCode);
        params.put("time", time);
        params.put("type", type);
        MyHttpClient.postByTimeout(UrlPath.URL_GET_NEW_RETAIL_REPORT, null, params, 60, new RequestCallBack<NewRetailBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(NewRetailBean result) {
                listener.success(result);
            }
        });
    }

    public interface GetDataListener {
        void success(NewRetailBean bean);

        void failed(String failed);
    }
}
