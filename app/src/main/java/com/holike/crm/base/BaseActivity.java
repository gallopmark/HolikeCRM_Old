package com.holike.crm.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.holike.crm.R;
import com.holike.crm.customView.AppToastCompat;
import com.holike.crm.dialog.LoadingDialog;
import com.holike.crm.dialog.SimpleDialog;
import com.holike.crm.helper.BackHandlerHelper;
import com.holike.crm.helper.SystemBarTintHelper;
import com.holike.crm.util.LogCat;
import com.umeng.analytics.MobclickAgent;

import java.util.Random;

import butterknife.ButterKnife;

/**
 * Created by wqj on 2017/9/19.
 */

public abstract class BaseActivity<P extends BasePresenter, V extends BaseView> extends AppCompatActivity {
    public final int REQUEST_CODE = new Random().nextInt(65536);
    protected CountDownTimer timer;
    protected P mPresenter;
    protected Dialog loadingDialog;
    protected AppToastCompat mToastCompat;
    /*权限申请请求码*/
    private int mPermissionsRequestCode;
    /*权限申请回调*/
    private OnRequestPermissionsCallback mRequestPermissionsCallback;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientation();
        setContentView(setContentViewId());
        ButterKnife.bind(this);
        try {
            mPresenter = attachPresenter();
//            mPresenter = ((Class<P>) GenericsUtils.getSuperClassGenricType(getClass())).newInstance();
            if (this instanceof BaseView) {
                if (mPresenter != null) mPresenter.attach((V) this);
            }
        } catch (Exception e) {
            LogCat.e(e);
        }
        if (isFullScreen()) {
            fullScreen();
        } else {
            setStatusBarColor(R.color.bg_state_bar);
        }
        init();
    }

    protected abstract P attachPresenter();

    /**
     * 设置layout
     */
    protected abstract int setContentViewId();

    /***
     * 初始化
     */
    protected abstract void init();

    /**
     * 获取当前activity
     */
    protected BaseActivity getActivity() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (mPresenter != null) {
            mPresenter.deAttach();
            mPresenter = null;
        }
        cancelToast();
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    /**
     * 是否充满全屏
     */
    protected boolean isFullScreen() {
        return false;
    }

    /**
     * 是否修改导航栏颜色
     */
    protected boolean isSetStatusBarColor() {
        return true;
    }

    protected void startActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * 设置屏幕方向
     */
    protected void setOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 返回
     */
    public void back(View view) {
        hideSoftInput(getWindow().getDecorView());
        finish();
    }

    /**
     * 显示键盘
     */
    protected void showSoftInput(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setSelection(((EditText) view).getText().toString().length());
        }
        view.requestFocus();
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏键盘
     */
    protected void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 通过设置全屏，设置状态栏透明
     */
    protected void fullScreen() {
        SystemBarTintHelper.fullScreen(this);
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor(int colorId) {
        if (!isFullScreen()) {
            SystemBarTintHelper.setStatusBarColor(this, ContextCompat.getColor(this, colorId));
        } else {
            View statusView = findViewById(R.id.statusView);
            if (statusView != null && colorId != 0) {
                ViewGroup.LayoutParams params = statusView.getLayoutParams();
                params.height = SystemBarTintHelper.getStatusBarHeight();
                statusView.setBackgroundResource(colorId);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE == requestCode) {
            onActivityResult(resultCode, data);
        }
    }

    protected void onActivityResult(int resultCode, Intent data) {
    }

    private boolean isContainEmptyView() {
        return findViewById(R.id.ll_empty_page) != null;
    }

    /**
     * 没数据
     */
    public void noData(int imgId, int strId, boolean needReload) {
        if (isContainEmptyView()) {
            noDataImg(imgId, needReload);
            ((TextView) findViewById(R.id.tv_empty_page)).setText(strId);
        }
    }

    /**
     * 没数据
     */
    public void noData(int imgId, String strId, boolean needReload) {
        if (isContainEmptyView()) {
            noDataImg(imgId, needReload);
            ((TextView) findViewById(R.id.tv_empty_page)).setText(strId);
        }
    }

    public void noDataImg(int imgId, boolean needReload) {
        if (isContainEmptyView()) {
            findViewById(R.id.ll_empty_page).setVisibility(View.VISIBLE);
            ((ImageView) findViewById(R.id.iv_empty_page)).setImageResource(imgId);
            if (needReload) {
                findViewById(R.id.btn_empty_page_reload).setVisibility(View.VISIBLE);
                findViewById(R.id.btn_empty_page_reload).setOnClickListener(v -> {
                    hasData();
                    reload();
                });
            } else {
                findViewById(R.id.btn_empty_page_reload).setVisibility(View.GONE);
            }
        }
    }

    /**
     * 没有查询结果
     */
    public void noResult() {
        noData(R.drawable.no_result, R.string.tips_noresult, false);
    }

    /**
     * 网络加载出错
     */
    public void noNetwork() {
        noData(R.drawable.no_network, R.string.tips_nonetwork, true);
    }

    /*是否是无权限*/
    protected boolean isNoAuth(String failed) {
        return TextUtils.equals(failed, getString(R.string.noAuthority)) || TextUtils.equals(failed, getString(R.string.tips_nopermissions));
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
     * 重新加载，隐藏没数据提示
     */
    public void hasData() {
        if (isContainEmptyView()) {
            findViewById(R.id.ll_empty_page).setVisibility(View.GONE);
        }
    }

    /**
     * 重新加载
     */
    public void reload() {

    }

    /**
     * 显示正在加载
     */
    public void showLoading() {
        dismissLoading();
        if (loadingDialog == null) {
            loadingDialog = getLoadingDialog();
        }
        loadingDialog.show();
//        loadingDialog.show(getSupportFragmentManager(), "loading");
    }

    /**
     * 隐藏正在加载
     */
    public void dismissLoading() {
//        if ((loadingDialog = (DialogFragment) getSupportFragmentManager().findFragmentByTag("loading")) != null) {
//            loadingDialog.dismissAllowingStateLoss();
//        }
        if (loadingDialog != null) loadingDialog.dismiss();
    }

    protected Dialog getLoadingDialog() {
        return new LoadingDialog(this);
    }

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
        mToastCompat = AppToastCompat.makeText(this, Toast.LENGTH_SHORT, gravity);
        mToastCompat.show(text);
    }

    @SuppressWarnings("unused")
    protected void showLongToast(@StringRes int resId) {
        showLongToast(resId, -1);
    }

    public void showLongToast(@StringRes int resId, int gravity) {
        showLongToast(getString(resId), gravity);
    }

    protected void showLongToast(CharSequence text) {
        showLongToast(text, -1);
    }

    protected void showLongToast(CharSequence text, int gravity) {
        cancelToast();
        mToastCompat = AppToastCompat.makeText(this, Toast.LENGTH_LONG, gravity);
        mToastCompat.show(text);
    }

    private void cancelToast() {
        if (mToastCompat != null) mToastCompat.cancel();
    }

    /**
     * 拨打电话
     */
    public void call(final String phoneNumber) {
        new SimpleDialog(getActivity()).setDate("拨打电话", phoneNumber, "取消", "拨打").setListener(new SimpleDialog.ClickListener() {
            @Override
            public void left() {
            }

            @Override
            public void right() {
                callPhone(phoneNumber);
            }
        }).show();
    }

    protected void callPhone(String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phoneNumber);
            intent.setData(data);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断TextView是否空
     */
    public boolean textEmpty(TextView textView) {
        return textView.getText().toString().length() == 0;
    }

    /**
     * 获取TextView内容
     */
    public String getText(TextView textView) {
        return textView.getText().toString();
    }

    /*是否授予了权限*/
    protected boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 申请单个权限
     *
     * @param permission  需要申请的权限
     * @param requestCode 请求码
     * @param callback    callback
     */
    protected void requestPermission(@NonNull String permission, int requestCode, OnRequestPermissionsCallback callback) {
        requestPermissions(new String[]{permission}, requestCode, callback);
    }

    /**
     * 申请权限
     *
     * @param permissions 需要申请的权限数组
     * @param requestCode 请求码
     * @param callback    请求回调
     */
    protected void requestPermissions(@NonNull final String[] permissions, final int requestCode, OnRequestPermissionsCallback callback) {
        this.mPermissionsRequestCode = requestCode;
        this.mRequestPermissionsCallback = callback;
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mPermissionsRequestCode) {
            PermissionHelper.convert(this, requestCode, permissions, grantResults, mRequestPermissionsCallback);
        }
    }
}
