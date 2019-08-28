package com.holike.crm.view.fragment;

import com.holike.crm.base.BaseView;
import com.holike.crm.bean.EmployeeDetailBean;

import java.util.List;

public interface EmployeeAuthInfoView extends BaseView {
    void onShowLoading();
    void onGetAuthInfo(List<EmployeeDetailBean.EmployeeAuthInfoBean> infoBeans);
    void onGetAuthInfoFail(String message);
    void onHideLoading();
}
