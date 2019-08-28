package com.holike.crm.activity.mine;


import com.holike.crm.R;
import com.holike.crm.base.BasePresenter;
import com.holike.crm.base.MyFragmentActivity;
import com.holike.crm.bean.HomepageBean;
import com.holike.crm.fragment.homepage.FeedbackFragment;
import com.holike.crm.util.Constants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqj on 2018/7/16.
 * 售后体验反馈
 */

public class FeedbackActivity extends MyFragmentActivity {
    @Override
    protected BasePresenter attachPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void init() {
        super.init();

        HomepageBean homepageBean = (HomepageBean) getIntent().getSerializableExtra(Constants.HOME_PAGE_BEAN);
        Map<String, Serializable> params = new HashMap<>();
        params.put(Constants.HOME_PAGE_BEAN, homepageBean);
        startFragment(params, new FeedbackFragment(), false);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 10086 && data != null) {
//            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
//            if (fragmentManager.getFragments().size() > 0) {
//                FeedbackFragment fragment = (FeedbackFragment) fragmentManager.getFragments().get(0);
//                fragment.onActivityResult(images);
//            }
//        }
//    }
}
