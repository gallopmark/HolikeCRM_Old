package com.holike.crm.model.fragment;

import com.holike.crm.base.BaseModel;
import com.holike.crm.bean.ReportPermissionsBean;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;

import java.util.List;

/**
 * Created by wqj on 2018/2/24.
 * 分析
 */

public class ReportModel implements BaseModel {

    public void getPermissions(final GetPermissionsListener listener) {
        MyHttpClient.postByCliId(UrlPath.URL_GET_REPORT_PERMISSIONS, null, null, new RequestCallBack<List<ReportPermissionsBean>>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(List<ReportPermissionsBean> result) {
                listener.success(result);
            }
        });
    }

    public interface GetPermissionsListener {
        void success(List<ReportPermissionsBean> list);

        void failed(String failed);
    }
}
