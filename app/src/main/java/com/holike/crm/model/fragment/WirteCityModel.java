package com.holike.crm.model.fragment;

import com.holike.crm.base.BaseModel;
import com.holike.crm.bean.WriteCityBean;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqj on 2018/6/26.
 * 主动营销-填写城市
 */

public class WirteCityModel implements BaseModel {

    /**
     * 获取数据
     *
     * @param listener
     */
    public void getData(final GetDataListener listener) {
        MyHttpClient.postByCliId(UrlPath.URL_ACTIVE_MARKET_REPORT_WRITE_CITY, null, null, new RequestCallBack<WriteCityBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(WriteCityBean result) {
                listener.success(result);
            }

        });
    }

    public interface GetDataListener {
        void success(WriteCityBean bean);

        void failed(String failed);
    }

    /**
     * 保存城市
     *
     * @param dealerId
     * @param start
     * @param end
     * @param listener
     */
    public void saveCity(String dealerId, String start, String end, final SaveCityListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("dealerId", dealerId);
        params.put("start", start);
        params.put("end", end);
        MyHttpClient.postByCliId(UrlPath.URL_ACTIVE_MARKET_REPORT_SAVE_CITY, null, params, new RequestCallBack<String>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(String result) {
                listener.success(result);
            }

        });
    }

    public interface SaveCityListener {
        void success(String success);

        void failed(String failed);
    }

    /**
     * 删除城市
     *
     * @param dealerId
     * @param listener
     */
    public void delCity(String dealerId, final DelCityListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("dealerId", dealerId);
        MyHttpClient.postByCliId(UrlPath.URL_ACTIVE_MARKET_REPORT_DELETE_CITY, null, params, new RequestCallBack<String>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(String result) {
                listener.success(result);
            }

        });
    }

    public interface DelCityListener {
        void success(String success);

        void failed(String failed);
    }
}
