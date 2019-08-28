package com.holike.crm.base;


import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.holike.crm.R;
import com.holike.crm.customView.AppToastCompat;
import com.holike.crm.dialog.LoadingDialog;

public abstract class InheritedLazyFragment<P extends BasePresenter, V extends BaseView> extends InheritedFakeFragment {
    protected P mPresenter;
    protected Dialog mLoadingDialog;
    protected AppToastCompat mToastCompat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = attachPresenter();
    }

    protected abstract P attachPresenter();

    @SuppressWarnings("unused")
    protected void showShortToast(@StringRes int resId) {
        showShortToast(resId, -1);
    }

    protected void showShortToast(@StringRes int resId, int gravity) {
        showShortToast(getString(resId), gravity);
    }

    protected void showShortToast(CharSequence text) {
        showShortToast(text, -1);
    }

    protected void showShortToast(CharSequence text, int gravity) {
        if (TextUtils.isEmpty(text)) return;
        cancelToast();
        mToastCompat = AppToastCompat.makeText(mActivity, Toast.LENGTH_SHORT, gravity);
        mToastCompat.show(text);
    }

    @SuppressWarnings("unused")
    protected void showLongToast(@StringRes int resId) {
        showLongToast(resId, -1);
    }

    public void showLongToast(@StringRes int resId, int gravity) {
        showLongToast(getString(resId), gravity);
    }

    @SuppressWarnings("unused")
    protected void showLongToast(CharSequence text) {
        showLongToast(text, -1);
    }

    protected void showLongToast(CharSequence text, int gravity) {
        if (TextUtils.isEmpty(text)) return;
        cancelToast();
        mToastCompat = AppToastCompat.makeText(mActivity, Toast.LENGTH_LONG, gravity);
        mToastCompat.show(text);
    }

    private void cancelToast() {
        if (mToastCompat != null) mToastCompat.cancel();
    }

    protected void showLoading() {
        dismissLoading();
        if (mLoadingDialog == null) {
            mLoadingDialog = getLoadingDialog();
        }
        mLoadingDialog.show();
    }

    protected void dismissLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    protected Dialog getLoadingDialog() {
        return new LoadingDialog(mActivity);
    }

    /**
     * 没有数据
     */
    protected void noData(int imgId, int strId, boolean needReload) {
        rootContainer.findViewById(R.id.ll_empty_page).setVisibility(View.VISIBLE);
        ((ImageView) rootContainer.findViewById(R.id.iv_empty_page)).setImageResource(imgId);
        ((TextView) rootContainer.findViewById(R.id.tv_empty_page)).setText(strId);
        if (needReload) {
            rootContainer.findViewById(R.id.btn_empty_page_reload).setVisibility(View.VISIBLE);
            rootContainer.findViewById(R.id.btn_empty_page_reload).setOnClickListener(v -> {
                hasData();
                onReload();
            });
        } else {
            rootContainer.findViewById(R.id.btn_empty_page_reload).setVisibility(View.GONE);
        }
    }

    /**
     * 没有数据
     */
    protected void noData(int imgId, String str, boolean needReload) {
        View mEmptyView = rootContainer.findViewById(R.id.ll_empty_page);
        if (mEmptyView == null) return;
        mEmptyView.setVisibility(View.VISIBLE);
        ((ImageView) rootContainer.findViewById(R.id.iv_empty_page)).setImageResource(imgId);
        ((TextView) rootContainer.findViewById(R.id.tv_empty_page)).setText(str);
        if (needReload) {
            rootContainer.findViewById(R.id.btn_empty_page_reload).setVisibility(View.VISIBLE);
            rootContainer.findViewById(R.id.btn_empty_page_reload).setOnClickListener(view -> {
                mEmptyView.setVisibility(View.GONE);
                onReload();
            });
        } else {
            rootContainer.findViewById(R.id.btn_empty_page_reload).setVisibility(View.GONE);
        }
    }


    /**
     * 重新加载，隐藏没有数据提示
     */
    protected void hasData() {
        View mEmptyView = rootContainer.findViewById(R.id.ll_empty_page);
        if (mEmptyView == null) return;
        mEmptyView.setVisibility(View.GONE);
    }

    /**
     * 重新加载
     */
    protected void onReload() {

    }

    /**
     * 没有查询结果
     */
    protected void noResult() {
        noData(R.drawable.no_result, R.string.tips_noresult, false);
    }

    /**
     * 没有查询结果
     * strRes  内容
     */
    protected void noResult(String strRes) {
        noData(R.drawable.no_result, strRes, false);
    }

    /**
     * 没有网络
     */
    protected void onPageError() {
        noData(R.drawable.no_network, R.string.tips_nonetwork, true);
    }

    @Override
    public void onDestroy() {
        release();
        super.onDestroy();
    }

    private void release() {
        cancelToast();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
