package com.holike.crm.activity.homepage;

import com.holike.crm.R;
import com.holike.crm.base.BasePresenter;
import com.holike.crm.base.MyFragmentActivity;
import com.holike.crm.fragment.customer.CustomerDetailFragment2;
import com.holike.crm.presenter.fragment.HomePagePresenter;
import com.holike.crm.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqj on 2018/7/30.
 * 客户管理
 */

public class CustomerDetailActivity extends MyFragmentActivity {

    @Override
    protected void init() {
        super.init();
        Map<String, String> parmas = new HashMap<>();
        parmas.put(Constants.PERSONAL_ID, getIntent().getStringExtra(Constants.PERSONAL_ID));
        parmas.put(Constants.HOUSE_ID, getIntent().getStringExtra(Constants.HOUSE_ID));
        startFragment(parmas, new CustomerDetailFragment2(), false);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setRightMenu(HomePagePresenter.getMsgNum());
        setRightMsg(HomePagePresenter.isNewMsg());
    }

    @Override
    protected void clickRightMenu() {
        startActivity(MessageActivity.class);
    }

    @Override
    protected BasePresenter attachPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_customer_detail;
    }

}
