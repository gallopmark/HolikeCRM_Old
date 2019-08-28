package com.holike.crm.base;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.holike.crm.R;
import com.holike.crm.customView.CompatToast;
import com.holike.crm.fragment.FragmentBackHandler;
import com.holike.crm.util.CopyUtil;
import com.holike.crm.util.KeyBoardUtil;
import com.holike.crm.util.NumberUtil;
import com.holike.crm.util.TimeUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by wqj on 2018/4/11.
 * 实现activity功能的fragment
 */

public abstract class MyFragment<P extends BasePresenter, V extends BaseView> extends BaseFragment<P, V> implements FragmentBackHandler {

    private TimePickerView pvTime;

    @Override
    protected void init() {
        setBack();
    }


    /**
     * 设置主题
     *
     * @param title a
     */
    protected void setTitle(String title) {
        TextView tvTitle = mContentView.findViewById(R.id.tv_title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    protected void copy(String content) {
        if (TextUtils.isEmpty(content)) return;
        CopyUtil.copy(mContext, content);
        showShortToast("已复制：" + content + " 到粘贴板", CompatToast.Gravity.CENTER);
    }

    /**
     * 复制文字到剪贴板
     *
     * @param view    需要长按的控件
     * @param content 内容
     */
    protected void copy(View view, String content) {
        view.setOnLongClickListener(v -> {
            copy(content);
            return true;
        });

    }

    /**
     * 设置左边按钮文字
     */
    protected void setLeft(String left) {
        TextView tvLeft = mContentView.findViewById(R.id.tv_left);
        if (tvLeft != null) {
            if (left == null || left.equals("")) {
                tvLeft.setVisibility(View.GONE);
            } else
                tvLeft.setText(getString(R.string.back));
            tvLeft.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题背景
     */
    protected void setTitleBg(@DrawableRes int resId) {
        View flTitle = mContentView.findViewById(R.id.fl_fragment_title_bar);
        if (flTitle != null) {
            flTitle.setBackgroundResource(resId);
        }
    }

    /**
     * 设置搜索栏
     */
    protected EditText setSearchBar(int resHint) {
        LinearLayout mSearchLayout = mContentView.findViewById(R.id.mSearchLayout);
        if (mSearchLayout != null) mSearchLayout.setVisibility(View.VISIBLE);
        EditText editText = mContentView.findViewById(R.id.et_search);
        ImageView ivClear = mContentView.findViewById(R.id.iv_clear);
        if (editText != null) {
            editText.setVisibility(View.VISIBLE);
            editText.setHint(mContext.getString(resHint));
            ivClear.setOnClickListener(v -> editText.setText(""));
            editText.setOnEditorActionListener((v, actionId, event) -> {
                        if ((actionId == EditorInfo.IME_NULL || actionId == EditorInfo.IME_ACTION_SEARCH) && event == null) {
                            doSearch();
                        }
                        return false;
                    }
            );
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s.toString().trim())) {
                        ivClear.setVisibility(View.VISIBLE);
                    } else {
                        ivClear.setVisibility(View.GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        return editText;
    }

    /**
     * 开始搜索
     */
    protected void doSearch() {

    }

    /**
     * 设置右边菜单文字
     */
    protected void setRightMenu(final String text) {
        TextView tvRightMenu = mContentView.findViewById(R.id.tv_right_menu);
        if (tvRightMenu != null) {
            tvRightMenu.setText(text);
            tvRightMenu.setOnClickListener(v -> clickRightMenu(text));
        }
    }

    /**
     * 设置右边菜单图标
     */
    protected void setRightMenu(final int imgRes) {
        ImageView tvRightMenu = mContentView.findViewById(R.id.iv_right_menu);
        if (tvRightMenu != null) {
            tvRightMenu.setImageResource(imgRes);
            tvRightMenu.setOnClickListener(v -> clickRightMenu(""));
        }
    }

    /**
     * 设置右边菜单图标
     */
    protected void setRightMenuMsg(final boolean isNewMsg) {
        TextView tvRightMenu = mContentView.findViewById(R.id.tv_right_menu);
        if (tvRightMenu != null) {
            ImageView ivNewMsg = mContentView.findViewById(R.id.iv_red_point_msg);
            ivNewMsg.setVisibility(isNewMsg ? View.VISIBLE : View.GONE);
            tvRightMenu.setText(getString(R.string.message_title));
            tvRightMenu.setOnClickListener(v -> clickRightMenu(getString(R.string.message_title)));
        }
    }


    /**
     * 点击右边菜单
     */
    protected void clickRightMenu(String menuText) {
    }

    /**
     * 设置返回
     */
    public void setBack() {
        LinearLayout llBack = mContentView.findViewById(R.id.ll_back);
        if (llBack != null) {
            llBack.setOnClickListener(v -> {
                KeyBoardUtil.hideKeyboard(v);
                back();
            });

        }
    }

    /**
     * 返回
     */
    protected void back() {
        finishFragment();
    }

    @Override
    public boolean onBackPressed() {
        finishFragment();
        return true;
    }

    /*
     * fragment加载动画
     *
     * @param transit
     * @param enter
     * @param nextAnim
     * @return
     */
//    @Override
//    public Animation onCreateAnimation(int transit, final boolean enter, int nextAnim) {
//        if (enter) {
//            try {
//                Animation anim = AnimationUtils.loadAnimation(mContext, nextAnim);
//                anim.setAnimationListener(new Animation.AnimationListener() {
//                    public void onAnimationStart(Animation animation) {
//                    }
//
//                    public void onAnimationRepeat(Animation animation) {
//                    }
//
//                    public void onAnimationEnd(Animation animation) {
//                        initFragment();
//                    }
//                });
//                return anim;
//            } catch (Exception e) {
//                initFragment();
//            }
//        }
//        return super.onCreateAnimation(transit, enter, nextAnim);
//    }

    /**
     * 打开fragment
     */
    public void startFragment(Fragment fragment) {
        if (mContext instanceof MyFragmentActivity) {
            ((MyFragmentActivity) mContext).startFragment(fragment);
        }
    }

    public void startFragment(Fragment fragment, boolean needAnim) {
        if (mContext instanceof MyFragmentActivity) {
            ((MyFragmentActivity) mContext).startFragment(null, fragment, needAnim);
        }
    }

    public void startFragment(Map<String, Serializable> params, Fragment fragment) {
        if (mContext instanceof MyFragmentActivity) {
            ((MyFragmentActivity) mContext).startFragment(params, fragment);
        }
    }

    /**
     * 关闭fragment
     */
    protected void finishFragment() {
        if (mContext instanceof MyFragmentActivity)
            ((MyFragmentActivity) mContext).finishFragment();
    }

    protected void finishFragment(int requestCode, int resultCode, Map<String, Serializable> result) {
        if (mContext instanceof MyFragmentActivity)
            ((MyFragmentActivity) mContext).finishFragment(requestCode, resultCode, result);
    }

    /**
     * 显示loading
     */
    protected void showLoading() {
        if (mContext instanceof BaseActivity)
            ((BaseActivity) mContext).showLoading();
    }

    /**
     * 隐藏loading
     */
    protected void dismissLoading() {
        if (mContext instanceof BaseActivity)
            ((BaseActivity) mContext).dismissLoading();
    }

    protected void selectTime(Date date) {
    }

    /**
     * 选择时间
     */
    protected void showTimePickerView(Context context, String time, View view) {
        if (view != null) {
            hideSoftInput(view);
        }
        if (pvTime == null) {
            pvTime = new TimePickerBuilder(context, (date, v) ->
                    selectTime(date)).setType(new boolean[]{true, true, true, false, false, false})
                    .setBackgroundId(getResources().getColor(R.color.bg_transparent1)).build();
        }
        if (TextUtils.isEmpty(time) || time.equals("无法显示时间")) {
            pvTime.setDate(Calendar.getInstance());
        } else {
            pvTime.setDate(TimeUtil.stringToCalendar(time, "yyyy.MM.dd"));

        }
        pvTime.show();
    }

    public static String textEmpty(String content) {
        return TextUtils.isEmpty(content) ? "-" : content;
    }

    public static String textEmpty(String content, String symbol) {
        return TextUtils.isEmpty(content) ? symbol : content;
    }

    public static String textEmptyNumber(String content) {
        try {
            return TextUtils.isEmpty(content) ? "-" : NumberUtil.format(content);
        } catch (Exception e) {
            return content;
        }
    }

    protected void onFinishResult(int requestCode, int resultCode, Map<String, Serializable> result) {
    }

}
