package com.holike.crm.model.fragment;

import com.holike.crm.base.BaseModel;
import com.holike.crm.bean.ProductCompleteBean;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqj on 2018/5/9.
 * 成品目标各月完成率
 */

public class ProductCompleteModel implements BaseModel {

    /**
     * 获取各月完成率数据
     *
     * @param cityCode
     * @param type
     * @param listener
     */
    public void getData(String cityCode, String type, final GetDataListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("cityCode", cityCode);
        params.put("type", type);
        MyHttpClient.postByTimeout(UrlPath.URL_GET_PRODUCT_COMPLETE, null, params, 60, new RequestCallBack<ProductCompleteBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(ProductCompleteBean result) {
                listener.success(result);
            }

        });
    }

    public interface GetDataListener {
        void success(ProductCompleteBean bean);

        void failed(String failed);
    }
}
