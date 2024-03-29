package com.holike.crm.fragment.analyze;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gallopmark.recycler.adapterhelper.CommonAdapter;
import com.holike.crm.R;
import com.holike.crm.activity.homepage.MessageActivity;
import com.holike.crm.base.BaseActivity;
import com.holike.crm.base.BaseFragment;
import com.holike.crm.bean.ReportPermissionsBean;
import com.holike.crm.helper.ReportGridItemClickHelper;
import com.holike.crm.presenter.fragment.HomePagePresenter;
import com.holike.crm.presenter.fragment.ReportPresenter;
import com.holike.crm.view.fragment.ReportView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wqj on 2018/2/24.
 * 分析
 */

public class ReportFragment extends BaseFragment<ReportPresenter, ReportView> implements ReportView {

    @BindView(R.id.rv_report)
    RecyclerView rv;
    @BindView(R.id.tv_homepage_msg)
    TextView tvMsg;
    @BindView(R.id.iv_home_red_point_msg)
    ImageView ivRedPoint;

//    private Class[] classes = new Class[]{WeekReportActivity.class, OrderReportActivity.class,
//            TranslateReportActivity.class, OrderRankingActivity.class,
//            ProductTradingActivity.class, BuildStoreActivity.class,
//            InstallEvaluateActivity.class, PerformanceActivity.class,
//            CupboardActivity.class, OriginalBoardActivity.class,
//            DealerRankActivity.class, TerminalCheckActivity.class,
//            NewRetailActivity.class, NetActivity.class,
//            MonthPkActivity.class, ActiveMarketActivity.class,
//            ActiveMarketRankActivity.class, FastLiveActivity.class,
//            OnlineAttractReportActivity.class, WoodenDoorActivity.class};

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_report;
    }

    @Override
    protected ReportPresenter attachPresenter() {
        return new ReportPresenter();
    }

    @Override
    protected void init() {
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(new GridLayoutManager(mContext, 3));
        mPresenter.getPermissions();
        showLoading();
    }

    @Override
    public void onResume() {
        super.onResume();
//        tvMsg.setText(HomePagePresenter.getMsgNum());

        ivRedPoint.setVisibility(HomePagePresenter.isNewMsg() ? View.VISIBLE : View.GONE);
    }

    public void noPromissions() {
        dismissLoading();
        noData(R.drawable.no_power, R.string.tips_nopermissions, false);
    }

    @Override
    public void getPermissionsSuccess(final List<ReportPermissionsBean> list) {
        dismissLoading();
        rv.setAdapter(new CommonAdapter<ReportPermissionsBean>(mContext, list) {
            @Override
            protected int bindView(int viewType) {
                return R.layout.item_rv_fragment_report;
            }

            @Override
            public void onBindHolder(RecyclerHolder holder, ReportPermissionsBean bean, int position) {
                ImageView iv = holder.obtainView(R.id.iv_item_rv_fragment_report);
                TextView tv = holder.obtainView(R.id.tv_item_rv_fragment_report);
                Glide.with(mContext).load(bean.getImageUrl())
                        .apply(new RequestOptions().placeholder(R.drawable.analysis_default)
                                .error(R.drawable.analysis_default))
                        .into(iv);
//                Glide.with(mContext).load(reportPermissionsBean.getImageUrl()).placeholder(R.drawable.analysis_default).error(R.drawable.analysis_default).into(iv);
                tv.setText(bean.getTitle());
                holder.itemView.setOnClickListener(v -> {
                    ReportGridItemClickHelper.dealWith((BaseActivity<?, ?>) mContext, bean.getType());
//                    if (type <= classes.length) {
//                        startActivity(classes[type - 1]);
//                        switch (type) {
//                            case 1:
//                                MobclickAgent.onEvent(mContext, "analyze_order_trend");
//                                break;
//                            case 2:
//                                MobclickAgent.onEvent(mContext, "analyze_order_report");
//                                break;
//                            case 3:
//                                MobclickAgent.onEvent(mContext, "analyze_order_translate_report");
//                                break;
//                            case 4:
//                                MobclickAgent.onEvent(mContext, "analyze_sign_order_ranking");
//                                break;
//                            case 5:
//                                MobclickAgent.onEvent(mContext, "analyze_product_trading");
//                                break;
//                            case 6:
//                                MobclickAgent.onEvent(mContext, "analyze_build_shop");
//                                break;
//                            case 7:
//                                break;
//                            case 8:
//                                MobclickAgent.onEvent(mContext, "analyze_performance");
//                                break;
//                            case 9:
//                                MobclickAgent.onEvent(mContext, "analyze_cupboard");
//                                break;
//                            case 10:
//                                MobclickAgent.onEvent(mContext, "analyze_original_board");
//                                break;
//                            case 11:
//                                MobclickAgent.onEvent(mContext, "analyze_dealers_ranking");
//                                break;
//                            case 12:
//                                MobclickAgent.onEvent(mContext, "analyze_terminal_check");
//                                break;
//                            case 13:
//                                MobclickAgent.onEvent(mContext, "analyze_new_retail");
//                                break;
//                            case 14:
//                                MobclickAgent.onEvent(mContext, "analyze_net");
//                                break;
//                            case 15:
//                                MobclickAgent.onEvent(mContext, "analyze_month_pk");
//                                break;
//                            case 16:
//                                MobclickAgent.onEvent(mContext, "analyze_active_marketing");
//                                break;
//                            case 17:
//                                MobclickAgent.onEvent(mContext, "analyze_active_marketing_ranking");
//                                break;
//                            case 18:
//                                MobclickAgent.onEvent(mContext, "analyze_fast_live");
//                                break;
//                            case 19:
//                                MobclickAgent.onEvent(mContext, "analyze_online_attract_report");
//                                break;
//                        }
//                    }
                });
            }
        });
    }

    @Override
    public void getPermissionsFailed(String failed) {
        dismissLoading();
        dealWithFailed(failed, true);
    }

    @Override
    protected void reload() {
        super.reload();
        mPresenter.getPermissions();
    }

    @OnClick(R.id.tv_homepage_msg)
    public void onViewClicked() {
        startActivity(MessageActivity.class);
    }
}
