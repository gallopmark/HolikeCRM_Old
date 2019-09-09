package com.holike.crm.activity.main;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import androidx.fragment.app.Fragment;
import android.widget.Toast;

import com.holike.crm.R;
import com.holike.crm.activity.login.LoginActivity;
import com.holike.crm.base.MyFragmentActivity;
import com.holike.crm.bean.UpdateBean;
import com.holike.crm.customView.MainTableLayout;
import com.holike.crm.dialog.UpdateAppDialog;
import com.holike.crm.fragment.FragmentManagerHelper;
import com.holike.crm.fragment.MineFragment;
import com.holike.crm.fragment.analyze.ReportFragment;
import com.holike.crm.fragment.homepage.CustomerManageFragment;
import com.holike.crm.fragment.homepage.HomepageFragment;
import com.holike.crm.fragment.homepage.OrderFragment;
import com.holike.crm.presenter.activity.MainPresenter;
import com.holike.crm.service.VersionUpdateService;
import com.holike.crm.util.Constants;
import com.holike.crm.util.SharedPreferencesUtils;
import com.holike.crm.view.activity.MainView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 主页
 */

public class MainActivity extends MyFragmentActivity<MainPresenter, MainView> implements MainView {
    @BindView(R.id.tab_main)
    MainTableLayout tabMain;

    private long mExitTime;
    private int[] iconNor = {R.drawable.tab_home_nor, R.drawable.tab_customer_nor, R.drawable.tab_order_nor, R.drawable.tab_analysis_nor, R.drawable.tab_mine_nor};
    private int[] iconSelect = {R.drawable.tab_home_sel, R.drawable.tab_customer_sel, R.drawable.tab_order_sel, R.drawable.tab_analysis_sel, R.drawable.tab_mine_sel};
    private Fragment mFragment;
    private FragmentManagerHelper fragmentManagerHelper;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected MainPresenter attachPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void init() {
        super.init();
        MainPresenter.setAlias();
        setStatusBarColor(R.color.textColor14);
        if (getIntent().getBooleanExtra("logout", false)) {//退出登录
            startActivity(LoginActivity.class);
            finish();
            return;
        }

        String[] titles = getResources().getStringArray(R.array.names_main_tab);
        List<Class<?>> fragments = new ArrayList<>();
        fragments.add(HomepageFragment.class);
        fragments.add(CustomerManageFragment.class);
        fragments.add(OrderFragment.class);
        fragments.add(ReportFragment.class);
        fragments.add(MineFragment.class);
        fragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.fl_main_fragment, fragments);
        tabMain.initView(iconNor, iconSelect, titles, position -> {
            mFragment = fragmentManagerHelper.changeFragment(position, mFragment);
            switch (position) {
                case 0:
                    MobclickAgent.onEvent(getActivity(), "homepage");
                    break;
                case 1:
                    MobclickAgent.onEvent(getActivity(), "customer");
                    break;
                case 2:
                    MobclickAgent.onEvent(getActivity(), "order");
                    break;
                case 3:
                    MobclickAgent.onEvent(getActivity(), "analyze");
                    break;
                case 4:
                    MobclickAgent.onEvent(getActivity(), "my_mine");
                    break;
            }
        });
        checkVersion();
    }

    private void checkVersion() {
        mPresenter.checkVersion();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, getString(R.string.tips_exit), Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            Process.killProcess(Process.myPid());
//            SophixManager.getInstance().killProcessSafely();
        }
    }

    /**
     * 切换页面
     */
    @Override
    public void changeFragment(int position) {
        tabMain.selectTable(position);
    }

    /**
     * 发现新版本
     */
    @Override
    public void hasNewVersion(UpdateBean updateBean) {
        long now = System.currentTimeMillis();
        long old = SharedPreferencesUtils.getLong(Constants.CHECK_VERSION_TIME, 0);
        if ((now - old) > Constants.UPDATE_DIALOG_TIME) {
            SharedPreferencesUtils.saveLong(Constants.CHECK_VERSION_TIME, now);
            showUpdateAppDialog(MainActivity.this, updateBean);
        }
    }

    @Override
    public void onFailure() {
        handler.postDelayed(retryRun, 5000);
    }

    private Runnable retryRun = this::checkVersion;

    /**
     * 版本更新弹窗
     */
    public void showUpdateAppDialog(final Context context, final UpdateBean updateBean) {
        new UpdateAppDialog(context, updateBean).setUpdateButtonClickListener(dialog -> {
            dialog.dismiss();
            VersionUpdateService.start(context,updateBean.getUpdatepath());
//            if (updateBean.getType() == UpdateBean.TYPE_DOWNLOAD) {
//                showLongToast("正在下载...");
////                Toast.makeText(MyApplication.getInstance(), "正在下载...", Toast.LENGTH_SHORT).show();
//                DownloadFileBean downloadFileBean = new DownloadFileBean(updateBean.getUpdatepath(), "CRM.apk");
//                Intent intent = new Intent(context, UpdateService.class);
//                intent.putExtra(UpdateService.DOWNLOADFILEBEAN, downloadFileBean);
//                context.startService(intent);
//            } else if (updateBean.getType() == UpdateBean.TYPE_INSTALL) {
//                UpdateService.install(AppUtils.getApkPath() + "/" + "CRM.apk");
//            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(retryRun);
        super.onDestroy();
    }
}
