package com.holike.crm.activity.analyze;


import com.holike.crm.R;
import com.holike.crm.base.BasePresenter;
import com.holike.crm.base.MyFragmentActivity;
import com.holike.crm.fragment.satisfaction.CustomerSatisfactionFragment;

/**
 * Created by pony on 2019/10/2.
 * Copyright holike possess 2019.
 * 客户满意度报表
 */
public class CustomerSatisfactionActivity extends MyFragmentActivity {

    @Override
    protected BasePresenter attachPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_customer_satisfaction;
    }

    @Override
    protected void init() {
        startFragment(new CustomerSatisfactionFragment());
    }
}
