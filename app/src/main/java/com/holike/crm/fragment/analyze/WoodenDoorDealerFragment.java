package com.holike.crm.fragment.analyze;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.gallopmark.recycler.adapterhelper.CommonAdapter;
import com.gallopmark.recycler.widgetwrapper.WrapperRecyclerView;
import com.holike.crm.R;
import com.holike.crm.base.BasePresenter;
import com.holike.crm.base.MyFragment;
import com.holike.crm.bean.OriginalBoardBean;
import com.holike.crm.bean.WoodenDoorBean;
import com.holike.crm.customView.LineChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by gallop on 2019/8/28.
 * Copyright holike possess 2019.
 * 木门业绩报表-经销商
 */
public class WoodenDoorDealerFragment extends MyFragment {

    @BindView(R.id.wrapperRecyclerView)
    WrapperRecyclerView mWrapperRecyclerView;

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_woodendoor_dealer;
    }

    @Override
    protected BasePresenter attachPresenter() {
        return null;
    }

    @Override
    protected void init() {
        super.init();
        setStatusBar();
        setTitle(getString(R.string.report_item18_title));
        mWrapperRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setNestedScrollingEnabled(false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            WoodenDoorBean bean = (WoodenDoorBean) bundle.getSerializable("bean");
            if (bean != null && bean.getDealerData() != null) {
                WoodenDoorBean.DealerDataBean dataBean = bean.getDealerData();
                setupHeader(dataBean);
                WoodenDoorBean.DealerListBean dealerDataBean = new WoodenDoorBean.DealerListBean("全年", dataBean.totalComplete);
                dataBean.getDealerList().add(0, dealerDataBean);
                setList(dataBean.getDealerList());
            }
        }
    }

    private void setupHeader(WoodenDoorBean.DealerDataBean bean) {
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.header_woodendoor_dealer, new LinearLayout(mContext), false);
        TextView tvComplete = headerView.findViewById(R.id.tv_complete);
        tvComplete.setText(obtainTotal(bean.totalComplete));
        TextView tvDescribe = headerView.findViewById(R.id.tv_describe);
        tvDescribe.setText(bean.dealerTime);
        LineChartView lineChartView = headerView.findViewById(R.id.chart_view);
        List<OriginalBoardBean.DealerDataBean> list = new ArrayList<>();
        for (WoodenDoorBean.DealerListBean listBean : bean.getDealerList()) {
            list.add(new OriginalBoardBean.DealerDataBean(listBean.month, listBean.achievement));
        }
        lineChartView.setDatas(list, false);
        mWrapperRecyclerView.addHeaderView(headerView);
    }

    private SpannableString obtainTotal(String totalComplete) {
        String origin = TextUtils.isEmpty(totalComplete) ? "" : totalComplete;
        int start = origin.length();
        String source = origin + " 万";
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new AbsoluteSizeSpan(mContext.getResources().getDimensionPixelSize(R.dimen.textSize_14), false), start, source.length(),
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    /**
     * 显示列表
     */
    public void setList(List<WoodenDoorBean.DealerListBean> list) {
        mWrapperRecyclerView.setAdapter(new CommonAdapter<WoodenDoorBean.DealerListBean>(mContext, list) {
            @Override
            protected int bindView(int viewType) {
                return R.layout.item_woodendoor_achievement;
            }

            @Override
            public void onBindHolder(RecyclerHolder holder, WoodenDoorBean.DealerListBean dealerDataBean, int position) {
                holder.setText(R.id.tv_date, dealerDataBean.month);
                holder.setText(R.id.tv_achievement, dealerDataBean.achievement);
                holder.setVisibility(R.id.v_divider, position != getItemCount() - 1);
            }
        });
    }
}
