package com.holike.crm.activity.message;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.holike.crm.R;
import com.holike.crm.base.MyFragmentActivity;
import com.holike.crm.bean.MessageDetailsBean;
import com.holike.crm.presenter.activity.MessageDetailsPresenter;
import com.holike.crm.util.Constants;
import com.holike.crm.view.activity.MessageDetailsView;

import java.util.List;

import butterknife.BindView;

/**
 * 消息详情
 */
public class MessageDetailsActivity extends MyFragmentActivity<MessageDetailsPresenter, MessageDetailsView> implements MessageDetailsView {
    @BindView(R.id.mContainer)
    View mContainer;
    @BindView(R.id.tv_message_details_message_title)
    TextView tvMessageTitle;
    @BindView(R.id.tv_message_details_data)
    TextView tvData;
    @BindView(R.id.tv_message_details_sender)
    TextView tvSender;
    @BindView(R.id.ll_message_details_imgs)
    LinearLayout llImgs;

    @Override
    protected MessageDetailsPresenter attachPresenter() {
        return new MessageDetailsPresenter();
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_message_details;
    }

    @Override
    protected void init() {
        setBack();
        setTitle(getString(R.string.message_detail_title));
        initData();
    }

    private void initData() {
        showLoading();
        mPresenter.getMessageDetails(getIntent().getStringExtra(Constants.MESSAGE_ID));
    }

    /**
     * 获取消息详情成功
     */
    @Override
    public void getMessageDetailsSuccess(MessageDetailsBean messageDetailsBean) {
        dismissLoading();
        mContainer.setVisibility(View.VISIBLE);
        tvMessageTitle.setText(messageDetailsBean.getTitle());
        tvData.setText(messageDetailsBean.getCreateDate());
        String publisher = getString(R.string.message_publisher);
        if (!TextUtils.isEmpty(messageDetailsBean.getName()))
            publisher += messageDetailsBean.getName();
        tvSender.setText(publisher);
        showImgs(llImgs, messageDetailsBean.getPictureList());
    }

    /**
     * 显示图片
     */
    public void showImgs(final LinearLayout llImgs, List<String> imgs) {
        llImgs.removeAllViews();
        int size = imgs.size();
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(llImgs.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            iv.setLayoutParams(params);
            Glide.with(this).load(Uri.parse(imgs.get(i))).into(iv);
            llImgs.addView(iv);
        }
    }

    /**
     * 获取消息详情失败
     */
    @Override
    public void getMessageDetailsFailed(String failed) {
        dismissLoading();
        mContainer.setVisibility(View.GONE);
        dealWithFailed(failed, true);
    }
}
