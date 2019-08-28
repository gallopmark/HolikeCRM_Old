package com.holike.crm.model.activity;


import com.holike.crm.base.BaseModel;
import com.holike.crm.bean.UpdateBean;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;

/**
 * Created by wqj on 2017/11/27.
 * 主页Model
 */

public class MainModel implements BaseModel {

    /**
     * 检测系统版本更新
     *
     * @param checkListener
     */
    public void checkVersion(final checkListener checkListener) {
        MyHttpClient.post(UrlPath.URL_CHECK_VERSION, new RequestCallBack<UpdateBean>() {
            @Override
            public void onFailed(String result) {
                checkListener.failed(result);
            }

            @Override
            public void onSuccess(UpdateBean result) {
                checkListener.success(result);
            }
        });
    }

    public interface checkListener {
        void success(UpdateBean updateBean);

        void failed(String reult);
    }
}
