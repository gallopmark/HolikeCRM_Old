package com.holike.crm.model.fragment;

import com.holike.crm.base.BaseModel;
import com.holike.crm.bean.NetBean;
import com.holike.crm.bean.NetDetailBean;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqj on 2018/6/5.
 * 拉网
 */

public class NetModel implements BaseModel {

    /**
     * 获取数据
     *
     * @param cityCode
     * @param type
     * @param listener
     */
    public void getData(String cityCode, String type, final GetDataListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("cityCode", cityCode);
        params.put("type", type);
        MyHttpClient.postByTimeout(UrlPath.URL_NET_REPORT, null, params, 60, new RequestCallBack<NetBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(NetBean result) {
                listener.success(result);
            }
        });
    }

    public interface GetDataListener {
        void success(NetBean bean);

        void failed(String failed);
    }

    /**
     * 获取拉网明细
     *
     * @param cityCode
     * @param type
     * @param listener
     */
    public void getNetDetail(String cityCode, String type, final GetNetDetailListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("cityCode", cityCode);
        params.put("type", type);
        MyHttpClient.postByTimeout(UrlPath.URL_NET_DETAIL_REPORT, null, params, 60, new RequestCallBack<NetDetailBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(NetDetailBean result) {
                listener.success(result);
            }
        });
    }

    public interface GetNetDetailListener {
        void success(NetDetailBean bean);

        void failed(String failed);
    }
}
