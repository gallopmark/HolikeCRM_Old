package com.holike.crm.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.holike.crm.R;
import com.holike.crm.customView.AppToastCompat;
import com.holike.crm.helper.SystemBarTintHelper;
import com.holike.crm.util.KeyBoardUtil;
import com.holike.crm.util.LogCat;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wqj on 2017/9/20.
 */

public abstract class BaseFragment<P extends BasePresenter, V extends BaseView> extends Fragment implements View.OnTouchListener {
    protected final int REQUEST_CODE = new Random().nextInt(65536);
    protected View mContentView;
    protected P mPresenter;
    protected Context mContext;
    protected AppToastCompat mToastCompat;
    private int mRequestPermissionCode;
    private OnRequestPermissionsCallback mRequestPermissionsCallback;

    protected abstract int setContentViewId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            mPresenter = ((Class<P>) GenericsUtils.getSuperClassGenricType(getClass())).newInstance();
            mPresenter = attachPresenter();
            if (this instanceof BaseView) {
                if (mPresenter != null)
                    mPresenter.attach((V) this);
            }
        } catch (Exception e) {
            LogCat.e(e);
        }
    }

    protected abstract P attachPresenter();

    protected Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(setContentViewId(), container, false);
            mContentView.setClickable(true);
            unbinder = ButterKnife.bind(this, mContentView);
            init();
            mContentView.setOnTouchListener(this);

        }
        return mContentView;
    }


    protected abstract void init();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.deAttach();
        }
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (KeyBoardUtil.isKeyboardShown(mContentView))
            KeyBoardUtil.hideKeyboard(mContentView);
    }

    public Fragment getFragment() {
        return this;
    }

    /**
     * 启动activity
     */
    protected void startActivity(Class c) {
        if (getActivity() == null) return;
        startActivityForResult(c, REQUEST_CODE);
    }

    /**
     * 启动activity
     */
    protected void startActivity(Class c, Bundle bundle) {
        if (getActivity() == null) return;
        startActivityForResult(c, bundle, REQUEST_CODE);
    }

    protected void startActivityForResult(Class c, int requestCode) {
        if (getActivity() == null) return;
        startActivityForResult(c, null, requestCode);
    }

    protected void startActivityForResult(Class c, Bundle bundle, int requestCode) {
        if (getActivity() == null) return;
        Intent intent = new Intent(getActivity(), c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 设置导航栏颜色
     */
    protected void setStatusBar() {
        setStatusBar(0);
    }

    protected void setStatusBar(int resid) {
        View statusView = mContentView.findViewById(R.id.statusView);
        if (statusView != null) {
            ViewGroup.LayoutParams params = statusView.getLayoutParams();
            params.height = SystemBarTintHelper.getStatusBarHeight();
            if (resid != 0) {
                statusView.setBackgroundResource(resid);
            }
        }
    }

    protected void setTextColor(TextView view, int colorId) {
        view.setTextColor(getResources().getColor(colorId));
    }

    private boolean isContainEmptyView() {
        return mContentView.findViewById(R.id.ll_empty_page) != null;
    }

    /**
     * 没有数据
     */
    protected void noData(int imgId, int strId, boolean needReload) {
        if (!isContainEmptyView()) return;
        mContentView.findViewById(R.id.ll_empty_page).setVisibility(View.VISIBLE);
        ((ImageView) mContentView.findViewById(R.id.iv_empty_page)).setImageResource(imgId);
        ((TextView) mContentView.findViewById(R.id.tv_empty_page)).setText(strId);
        if (needReload) {
            mContentView.findViewById(R.id.btn_empty_page_reload).setVisibility(View.VISIBLE);
            mContentView.findViewById(R.id.btn_empty_page_reload).setOnClickListener(v -> {
                hasData();
                reload();
            });
        } else {
            mContentView.findViewById(R.id.btn_empty_page_reload).setVisibility(View.GONE);
        }
    }

    /**
     * 没有数据
     */
    protected void noData(int imgId, String str, boolean needReload) {
        if (!isContainEmptyView()) return;
        mContentView.findViewById(R.id.ll_empty_page).setVisibility(View.VISIBLE);
        ((ImageView) mContentView.findViewById(R.id.iv_empty_page)).setImageResource(imgId);
        ((TextView) mContentView.findViewById(R.id.tv_empty_page)).setText(str);
        if (needReload) {
            mContentView.findViewById(R.id.btn_empty_page_reload).setVisibility(View.VISIBLE);
            mContentView.findViewById(R.id.btn_empty_page_reload).setOnClickListener(v -> reload());
        } else {
            mContentView.findViewById(R.id.btn_empty_page_reload).setVisibility(View.GONE);
        }
    }


    /**
     * 重新加载，隐藏没有数据提示
     */
    protected void hasData() {
        if (!isContainEmptyView()) return;
        mContentView.findViewById(R.id.ll_empty_page).setVisibility(View.GONE);
    }

    /**
     * 重新加载
     */
    protected void reload() {

    }

    /**
     * 显示正在加载
     */
    protected void showLoading() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showLoading();
        }
    }

    /**
     * 隐藏正在加载
     */
    protected void dismissLoading() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).dismissLoading();
        }
    }

    /**
     * 隐藏键盘
     */
    protected void hideSoftInput(View view) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).hideSoftInput(view);
        }
    }

    /**
     * 显示键盘
     */
    protected void showSoftInput(View view) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showSoftInput(view);
        }
    }

    /**
     * 没有查询结果
     */
    public void noResult() {
        noData(R.drawable.no_result, R.string.tips_noresult, false);
    }

    /**
     * 没有查询结果
     * strRes  内容
     */
    public void noResult(String strRes) {
        noData(R.drawable.no_result, strRes, false);
    }

    /**
     * 没有网络
     */
    public void noNetwork() {
        noData(R.drawable.no_network, R.string.tips_nonetwork, true);
    }

    /*是否是无权限*/
    protected boolean isNoAuth(String failed) {
        return TextUtils.equals(failed, mContext.getString(R.string.noAuthority)) || TextUtils.equals(failed, mContext.getString(R.string.tips_nopermissions));
    }

    public void dealWithFailed(String failed, boolean showToast) {
        dealWithFailed(failed, showToast, -1);
    }

    /*处理接口返回的结果 如果是无权限则展示“当前角色无操作权限”页面*/
    public void dealWithFailed(String failed, boolean showToast, int gravity) {
        if (isNoAuth(failed)) {
            noAuthority();
        } else {
            if (showToast) showShortToast(failed, gravity);
            noNetwork();
        }
    }

    /*无权限*/
    public void noAuthority() {
        noData(R.drawable.no_power, R.string.tips_nopermissions, false);
    }

    /**
     * 打电话
     */
    public void call(final String phoneNumber) {
        if (getActivity() == null) return;
        ((BaseActivity) getActivity()).call(phoneNumber);
    }

    /**
     * 判断TextView是否空
     */
    public boolean isTextEmpty(TextView textView) {
        return textView.getText().toString().length() == 0;
    }

    /**
     * 获取TextView内容
     */
    public String getText(TextView textView) {
        return textView.getText().toString();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

//        InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            KeyBoardUtil.hideSoftInput((Activity) mContext);
//            if (manager != null
//                    && ((Activity) mContext).getCurrentFocus() != null
//                    && ((Activity) mContext).getCurrentFocus().getWindowToken() != null) {
//                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
        }
        return false;
    }

    @SuppressWarnings("unused")
    protected void showShortToast(@StringRes int resId) {
        showShortToast(resId, -1);
    }

    protected void showShortToast(@StringRes int resId, int gravity) {
        showShortToast(mContext.getString(resId), gravity);
    }

    protected void showShortToast(CharSequence text) {
        showShortToast(text, -1);
    }

    protected void showShortToast(CharSequence text, int gravity) {
        if (TextUtils.isEmpty(text)) return;
        cancelToast();
        mToastCompat = AppToastCompat.makeText(mContext, Toast.LENGTH_SHORT, gravity);
        mToastCompat.show(text);
    }

    @SuppressWarnings("unused")
    protected void showLongToast(@StringRes int resId) {
        showLongToast(resId, -1);
    }

    public void showLongToast(@StringRes int resId, int gravity) {
        showLongToast(mContext.getString(resId), gravity);
    }

    @SuppressWarnings("unused")
    protected void showLongToast(CharSequence text) {
        showLongToast(text, -1);
    }

    protected void showLongToast(CharSequence text, int gravity) {
        if (TextUtils.isEmpty(text)) return;
        cancelToast();
        mToastCompat = AppToastCompat.makeText(mContext, Toast.LENGTH_LONG, gravity);
        mToastCompat.show(text);
    }

    private void cancelToast() {
        if (mToastCompat != null) mToastCompat.cancel();
    }

    protected void requestPermission(@NonNull String permission, int requestCode, OnRequestPermissionsCallback callback) {
        requestPermissions(new String[]{permission}, requestCode, callback);
    }

    protected void requestPermissions(String[] permissions, int requestCode, OnRequestPermissionsCallback callback) {
        this.mRequestPermissionCode = requestCode;
        this.mRequestPermissionsCallback = callback;
        requestPermissions(permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestPermissionCode) {
            PermissionHelper.convert(this, requestCode, permissions, grantResults, mRequestPermissionsCallback);
        }
    }
}
