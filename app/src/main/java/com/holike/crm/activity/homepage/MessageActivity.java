package com.holike.crm.activity.homepage;

import androidx.fragment.app.Fragment;

import com.grallopmark.tablayout.CommonTabLayout;
import com.grallopmark.tablayout.TabEntity;
import com.grallopmark.tablayout.listener.CustomTabEntity;
import com.grallopmark.tablayout.listener.OnTabSelectListener;
import com.holike.crm.R;
import com.holike.crm.base.BasePresenter;
import com.holike.crm.base.MyFragmentActivity;
import com.holike.crm.fragment.FragmentManagerHelper;
import com.holike.crm.fragment.message.AnnounceFragment;
import com.holike.crm.fragment.message.NotifyFragment;
import com.holike.crm.presenter.fragment.HomePagePresenter;
import com.holike.crm.util.Constants;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wqj on 2018/8/2.
 * 消息
 */

public class MessageActivity extends MyFragmentActivity {
    @BindView(R.id.tab_message)
    CommonTabLayout tabMessage;

    private Fragment mFragment;
    private FragmentManagerHelper fragmentManagerHelper;
    protected int pageNo = 1;
    private String[] tabTitles;
    private ArrayList<CustomTabEntity> mTabEntities;

    @Override
    protected BasePresenter attachPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    protected void init() {
        super.init();
        MobclickAgent.onEvent(MessageActivity.this, "message");
        setTitle(getString(R.string.message_title));
        setBack();
        tabMessage = findViewById(R.id.tab_message);
        tabTitles = new String[]{getString(R.string.message_notify_message), getString(R.string.message_system_message)};
        mTabEntities = new ArrayList<>();
        List<Class<?>> fragments = new ArrayList<>();
        fragments.add(NotifyFragment.class);
        fragments.add(AnnounceFragment.class);
        fragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.fl_fragment_main, fragments);
        for (String tabTitle : tabTitles) {
            mTabEntities.add(new TabEntity(tabTitle, 0, 0));
        }
        tabMessage.setTabData(mTabEntities);
        tabMessage.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mFragment = fragmentManagerHelper.changeFragment(position, mFragment);
                if (position == 1) {
                    MobclickAgent.onEvent(MessageActivity.this, "message_announce");
                } else {
                    MobclickAgent.onEvent(MessageActivity.this, "message_notify");
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        if (getIntent() != null && getIntent().getIntExtra(Constants.PUSH_TYPE, 0) == Constants.PUSH_TYPE_NOTIFY) {
            tabMessage.setCurrentTab(0);
            mFragment = fragmentManagerHelper.getChangeFragment(0);
        } else {
            //默认进入公告
            tabMessage.setCurrentTab(1);
            mFragment = fragmentManagerHelper.getChangeFragment(1);
        }
    }

    /**
     * 显示未读消息数
     */
    public void showUnreadMsg(int notifyNum, int announceNum) {
        HomePagePresenter.setMsgNum(String.valueOf(notifyNum + announceNum));
        if (notifyNum > 0) {
            tabMessage.showMsg(0, notifyNum);
        } else {
            tabMessage.hideMsg(0);
        }
        if (announceNum > 0) {
            tabMessage.showMsg(1, announceNum);
        } else {
            tabMessage.hideMsg(1);
        }
    }
}
