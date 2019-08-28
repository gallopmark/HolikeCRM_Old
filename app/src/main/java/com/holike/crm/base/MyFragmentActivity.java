package com.holike.crm.base;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.holike.crm.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wqj on 2018/4/11.
 * 实现activity功能fragment的宿主
 */

public abstract class MyFragmentActivity<P extends BasePresenter, V extends BaseView> extends BaseActivity<P, V> {
    protected FragmentManager fragmentManager;
    protected List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void init() {
        fragmentManager = getSupportFragmentManager();
        setBack();

    }

    /**
     * 设置返回
     */
    public void setBack() {
        LinearLayout llBack = findViewById(R.id.ll_back);
        if (llBack != null) {
            llBack.setOnClickListener(v -> onBackPressed());
        }
    }

    /**
     * 设置右边菜单文字
     *
     * @param text
     */
    protected void setRightMenu(final String text) {
        TextView tvRightMenu = findViewById(R.id.tv_right_menu);
        if (tvRightMenu != null) {
            tvRightMenu.setText(text);
            tvRightMenu.setOnClickListener(v -> clickRightMenu());
        }
    }

    /**
     * 设置右边菜单文字
     *
     * @param isNewMsg
     */
    protected void setRightMsg(final boolean isNewMsg) {
        TextView tvRightMenu = findViewById(R.id.tv_right_menu);
        if (tvRightMenu != null) {
            ImageView ivRedPoint = findViewById(R.id.iv_red_point_msg);
            tvRightMenu.setText(getText(R.string.message_title));
            ivRedPoint.setVisibility(isNewMsg ? View.VISIBLE : View.GONE);
            tvRightMenu.setOnClickListener(v -> clickRightMenu());
        }
    }

    /**
     * 设置右边菜单图标
     *
     * @param imgRes
     */
    protected void setRightMenu(final int imgRes) {
        ImageView tvRightMenu = findViewById(R.id.iv_right_menu);
        if (tvRightMenu != null) {
            tvRightMenu.setImageResource(imgRes);
            tvRightMenu.setOnClickListener(v -> clickRightMenu());
        }
    }

    /**
     * 点击右边菜单
     */
    protected void clickRightMenu() {
    }

    /**
     * 设置左边菜单文字
     */
    @Deprecated
    protected void setLeft(String left) {
        TextView tvLeft = findViewById(R.id.tv_left);
        if (tvLeft != null) {
            if (left == null || left.equals("")) {
                tvLeft.setVisibility(View.GONE);
            } else
                tvLeft.setText(getString(R.string.back));
            if (tvLeft.getVisibility() != View.GONE) {
                tvLeft.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置标题
     */
    protected void setTitle(String title) {
        TextView tvTitle = findViewById(R.id.tv_title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    /**
     * 设置标题背景
     */
    protected void setTitleBg(int resId) {
        FrameLayout flTitle = findViewById(R.id.fl_fragment_title_bar);
        if (flTitle != null) {
            flTitle.setBackgroundResource(resId);
        }
    }

    /**
     * 打开fragment
     */
    protected void startFragment(Fragment fragment) {
        startFragment(null, fragment, false);
    }

    protected void startFragment(Map<String, Serializable> params, Fragment fragment) {
        startFragment(params, fragment, true);
    }

    protected void startFragment(Map<String, Serializable> params, Fragment fragment, boolean needAnimation) {
        if (params != null) {
            Bundle bundle = new Bundle();
            for (Map.Entry<String, Serializable> entry : params.entrySet()) {
                bundle.putSerializable(entry.getKey(), entry.getValue());
            }
            fragment.setArguments(bundle);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (needAnimation) {
            transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_right_out);
        }
        transaction.add(R.id.fl_fragment_main, fragment, fragment.getTag()).commitAllowingStateLoss();
        fragmentList.add(fragment);
    }

    /**
     * 关闭fragment
     */
    protected void finishFragment() {
        finishFragment(-1, -1, null);
    }

    protected void finishFragment(int requestCode, int resultCode, Map<String, Serializable> result) {
        finishFragment(requestCode, resultCode, result, true);
    }

    protected void finishFragment(int requestCode, int resultCode, Map<String, Serializable> result, boolean needAnimation) {
        int position = fragmentList.size() - 1;
        if (position > 0) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (needAnimation) {
                transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_right_out);
            }
            transaction.remove(fragmentList.get(position)).commitAllowingStateLoss();
            fragmentList.remove(position);
            ((MyFragment) fragmentList.get(position - 1)).onFinishResult(requestCode, resultCode, result);
        } else {
            finish();
        }
//        if (position == 0) {
//            finish();
//        } else {
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            if (needAnimation) {
//                transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_right_out);
//            }
//            transaction.remove(fragmentList.get(position)).commitAllowingStateLoss();
//            fragmentList.remove(position);
//            ((MyFragment) fragmentList.get(position - 1)).onFinishResult(requestCode, resultCode, result);
//        }
    }

}
