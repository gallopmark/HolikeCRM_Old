package com.holike.crm.activity.homepage;

import com.holike.crm.R;
import com.holike.crm.base.BasePresenter;
import com.holike.crm.base.MyFragmentActivity;
import com.holike.crm.fragment.homepage.OrderDetailsFragment;
import com.holike.crm.util.Constants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单详情
 */
public class OrderDetailsActivity extends MyFragmentActivity {

    @Override
    protected BasePresenter attachPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_fragment_depend;
    }

    @Override
    protected void init() {
        super.init();
        Map<String, Serializable> map = new HashMap<>(0);
        map.put(Constants.ORDER_ID, getIntent().getStringExtra(Constants.ORDER_ID));
        map.put(Constants.MESSAGE_ID, getIntent().getStringExtra(Constants.MESSAGE_ID));
        startFragment(map, new OrderDetailsFragment(),false);
    }

}

