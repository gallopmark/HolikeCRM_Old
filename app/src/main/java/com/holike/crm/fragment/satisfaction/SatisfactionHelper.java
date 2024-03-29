package com.holike.crm.fragment.satisfaction;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gallopmark.recycler.adapterhelper.BaseRecyclerAdapter;
import com.gallopmark.recycler.adapterhelper.CommonAdapter;
import com.holike.crm.R;
import com.holike.crm.activity.SettingsActivity;
import com.holike.crm.activity.main.PhotoViewActivity;
import com.holike.crm.adapter.AbsFormAdapter;
import com.holike.crm.base.BaseActivity;
import com.holike.crm.base.BaseFragment;
import com.holike.crm.base.FragmentHelper;
import com.holike.crm.bean.CustomerSatisfactionBean;
import com.holike.crm.helper.PickerHelper;
import com.holike.crm.helper.TextSpanHelper;
import com.holike.crm.itemdecoration.GridSpacingItemDecoration;
import com.holike.crm.rxbus.MessageEvent;
import com.holike.crm.rxbus.RxBus;
import com.holike.crm.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;

/*客户满意度帮助类*/
class SatisfactionHelper extends FragmentHelper {

    private Callback mCallback;
    private String mType;
    private String mCityCode;
    private Date mDatetime;
    private FrameLayout mContainerLayout;
    private boolean mAnimation;
    private CustomerSatisfactionBean mDataBean;
    private Disposable mDisposable;

    SatisfactionHelper(BaseFragment<?, ?> fragment, Callback callback) {
        super(fragment);
        this.mCallback = callback;
        initView();
        obtainValue();
        initData();
        registerResult();
    }

    private Runnable mRequestRun = this::doRequest;

    private void initView() {
        mContainerLayout = mFragmentView.findViewById(R.id.fl_container);
    }

    private void obtainValue() {
        Bundle bundle = mFragment.getArguments();
        if (bundle != null) {
            mType = bundle.getString("type");
            mCityCode = bundle.getString("cityCode");
            mDatetime = (Date) bundle.getSerializable("datetime");
            mAnimation = bundle.getBoolean("isAnimation", false);
        }
    }

    private void initData() {
        long delayMillis = 0;
        if (mAnimation) {
            delayMillis = 300;
        }
        mContainerLayout.postDelayed(mRequestRun, delayMillis);
    }

    void doRequest() {
        String datetime = "";
        if (mDatetime != null) {
            datetime += TimeUtil.getFirstDayOfMonth(mDatetime) + "@" + TimeUtil.getLastDayOfMonth(mDatetime);
        }
        String type = TextUtils.isEmpty(mType) ? "" : mType;
        String cityCode = TextUtils.isEmpty(mCityCode) ? "" : mCityCode;
        mCallback.onHttpRequest(type, cityCode, datetime);
    }

    /*观察者模式监听（设置页面的结果变化）*/
    private void registerResult() {
        mDisposable = RxBus.getInstance().toObservable(MessageEvent.class).subscribe(event -> {
            Bundle bundle = event.getArguments();
            if (bundle != null) {
                mDataBean.id = bundle.getString("id");
                mDataBean.param2 = bundle.getString("param2");
            }
        });
    }

    void onHttpSuccess(CustomerSatisfactionBean bean) {
        this.mDataBean = bean;
        if (mDataBean == null) {
            mContainerLayout.setVisibility(View.GONE);
            mFragment.noResult();
        } else {
            setSettingsButton();
            setData();
        }
    }

    private void setSettingsButton() {
        if (mDataBean.isShowSettings()) {
            mFragment.setRightMenu(R.string.settings, view -> SettingsActivity.startRuleSettings((BaseActivity<?, ?>) mContext, mDataBean.id, mDataBean.param2));
        } else {
            mFragment.hideRightMenu();
        }
    }

    private void setData() {
        mContainerLayout.setVisibility(View.VISIBLE);
        if (mDataBean.isShop()) {  //判断加载哪个布局
            setShopData();
        } else {
            setSelectData();
        }
    }

    /*列表式*/
    private void setShopData() {
        mContainerLayout.removeAllViews();
        View view = LayoutInflater.from(mContext).inflate(R.layout.include_customer_satisfaction_list, mContainerLayout, true);
        final TextView tvDatetime = view.findViewById(R.id.tv_datetime);
        setDatetime(tvDatetime, mDataBean.time);
        tvDatetime.setOnClickListener(v -> PickerHelper.showTimeYMPicker(mContext, date -> {
            mDatetime = date;
            doRequest();
        }));
        TextView tvEmpty = view.findViewById(R.id.tv_empty);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        if (mDataBean.getShopData().isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new ShopDataItemAdapter(mContext, mDataBean.getShopData()));
        }
    }

    private void setDatetime(TextView tvDatetime, String datetime) {
        String timeTemp = mContext.getString(R.string.tips_evaluation_time) + (TextUtils.isEmpty(datetime) ? "" : datetime);
        tvDatetime.setText(timeTemp);
    }

    /*表格式*/
    private void setSelectData() {
        mContainerLayout.removeAllViews();
        View view = LayoutInflater.from(mContext).inflate(R.layout.include_customer_satisfaction_form, mContainerLayout, true);
        final TextView tvDatetime = view.findViewById(R.id.tv_datetime);
        tvDatetime.setText(mDataBean.time);
        tvDatetime.setOnClickListener(v -> PickerHelper.showTimeYMPicker(mContext, date -> {
            mDatetime = date;
            doRequest();
        }));
        if (mDataBean.getSelectData().isEmpty()) {
            view.findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.tv_empty).setVisibility(View.GONE);
            ViewStub viewStub = view.findViewById(R.id.viewStub);
            View contentView = viewStub.inflate();
            RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);
            recyclerView.setAdapter(new FormDataAdapter(mContext, mDataBean.getSelectData()));
        }
    }

    void onHttpFailure(String failReason) {
        mContainerLayout.setVisibility(View.GONE);
        mFragment.noNetwork(failReason);
    }

    void deDetach() {
        mContainerLayout.removeCallbacks(mRequestRun);
        mDisposable.dispose();
    }

    //shopData
    class ShopDataItemAdapter extends CommonAdapter<CustomerSatisfactionBean.ShopDataBean> {

        TextSpanHelper mSpanHelper;

        ShopDataItemAdapter(Context context, List<CustomerSatisfactionBean.ShopDataBean> mDatas) {
            super(context, mDatas);
            mSpanHelper = TextSpanHelper.from(mContext);
        }

        @Override
        protected int bindView(int viewType) {
            return R.layout.item_customer_satisfaction_list;
        }

        @Override
        public void onBindHolder(RecyclerHolder holder, CustomerSatisfactionBean.ShopDataBean bean, int position) {
            holder.setText(R.id.tv_customer_phone, mSpanHelper.obtainColorBoldStyle(R.string.customer_telephone_tips, bean.phoneNumber, R.color.textColor4));
            holder.setText(R.id.tv_order_time, mSpanHelper.obtainColorBoldStyle(R.string.tips_evaluation_time, bean.time, R.color.textColor4));
            holder.setText(R.id.tv_service_satisfaction, mSpanHelper.obtainColorBoldStyle(R.string.customer_satisfaction_service, bean.service, R.color.textColor4));
            holder.setText(R.id.tv_design_satisfaction, mSpanHelper.obtainColorBoldStyle(R.string.customer_satisfaction_design, bean.design, R.color.textColor4));
            holder.setText(R.id.tv_delivery_satisfaction, mSpanHelper.obtainColorBoldStyle(R.string.customer_satisfaction_delivery, bean.delivery, R.color.textColor4));
            holder.setText(R.id.tv_install_satisfaction, mSpanHelper.obtainColorBoldStyle(R.string.customer_satisfaction_install, bean.install, R.color.textColor4));
            if (!TextUtils.isEmpty(bean.content)) {
                holder.setVisibility(R.id.ll_content_layout, View.VISIBLE);
                holder.setText(R.id.tv_message, bean.content);
            } else {
                holder.setVisibility(R.id.ll_content_layout, View.GONE);
            }
            if (bean.getUrlList().isEmpty()) {
                holder.setVisibility(R.id.ll_photo_layout, View.GONE);
            } else {
                holder.setVisibility(R.id.ll_photo_layout, View.VISIBLE);
                RecyclerView rvPhoto = holder.obtainView(R.id.rv_photo);
                rvPhoto.setNestedScrollingEnabled(false);
                if (rvPhoto.getItemDecorationCount() == 0) {
                    rvPhoto.addItemDecoration(new GridSpacingItemDecoration(5, mContext.getResources().getDimensionPixelSize(R.dimen.dp_10)));
                }
                rvPhoto.setAdapter(new UrlDataAdapter(mContext, bean.getUrlList()));
            }
        }

        class UrlDataAdapter extends CommonAdapter<CustomerSatisfactionBean.ShopDataBean.UrlBean> {
            List<String> mImages;

            UrlDataAdapter(Context context, List<CustomerSatisfactionBean.ShopDataBean.UrlBean> dataList) {
                super(context, dataList);
                mImages = new ArrayList<>();
                for (CustomerSatisfactionBean.ShopDataBean.UrlBean bean : dataList) {
                    if (!bean.isMp4()) {
                        mImages.add(bean.url);
                    }
                }
            }

            @Override
            protected int bindView(int viewType) {
                return R.layout.item_customer_satisfaction_list_child;
            }

            @Override
            public void onBindHolder(RecyclerHolder holder, CustomerSatisfactionBean.ShopDataBean.UrlBean bean, int position) {
                ImageView iv = holder.obtainView(R.id.iv_image);
                Glide.with(mContext).load(bean.getShowUrl()).apply(new RequestOptions()
                        .placeholder(R.drawable.loading_photo)
                        .error(0).centerCrop()).into(iv);
                holder.itemView.setOnClickListener(view -> {
                    PhotoViewActivity.openImage(mContext, position, mImages);
//                    if (bean.isMp4()) { //播放视频 跳转播放页面
//                    } else {
//                        PhotoViewActivity.openImage(mContext, position, mImages);
//                    }
                });
            }
        }
    }

    class FormDataAdapter extends AbsFormAdapter<CustomerSatisfactionBean.SelectDataBean> {

        FormDataAdapter(Context context, List<CustomerSatisfactionBean.SelectDataBean> mDatas) {
            super(context, mDatas);
        }

        @Override
        protected int bindView(int viewType) {
            return R.layout.item_customer_satisfaction_form;
        }

        @Override
        protected void bindViewHolder(BaseRecyclerAdapter.RecyclerHolder holder, CustomerSatisfactionBean.SelectDataBean bean, int position) {
            holder.setText(R.id.tv_division, bean.area);
            holder.setText(R.id.tv_service_satisfaction, bean.service);
            holder.setText(R.id.tv_design_satisfaction, bean.design);
            holder.setText(R.id.tv_delivery_satisfaction, bean.delivery);
            holder.setText(R.id.tv_install_satisfaction, bean.install);
            if (bean.isClickable()) {
                holder.setTextColorRes(R.id.tv_division, R.color.colorAccent);
                holder.setEnabled(R.id.tv_division, true);
            } else {
                holder.setTextColorRes(R.id.tv_division, R.color.textColor8);
                holder.setEnabled(R.id.tv_division, false);
            }
            holder.setOnClickListener(R.id.tv_division, view -> mCallback.toNextLevel(obtainBundle(bean.type, bean.cityCode)));
        }
    }

    private Bundle obtainBundle(String type, String cityCode) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("cityCode", cityCode);
        bundle.putSerializable("datetime", mDatetime);
        bundle.putBoolean("isAnimation", true);
        return bundle;
    }

    interface Callback {
        void onHttpRequest(String type, String cityCode, String datetime);

        void toNextLevel(Bundle bundle);
    }
}
