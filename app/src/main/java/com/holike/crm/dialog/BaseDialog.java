package com.holike.crm.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.holike.crm.R;
import com.holike.crm.util.DensityUtil;


/**
 * Created by wqj on 2017/11/2.
 * 通用dialog
 */

public abstract class BaseDialog extends Dialog {
    protected Context mContext;
    protected View mContentView;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        mContext = context;
        mContentView = LayoutInflater.from(context).inflate(setContentView(), null, false);
        initView();
    }

    protected void initView() {
    }

    abstract int setContentView();

    protected boolean isFullScreen() {
        return false;
    }

    @Override
    public void show() {
        if (getWindow() != null) {
            getWindow().setContentView(mContentView);
            if (isFullScreen()) {
                getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            } else {
                int width = (DensityUtil.getScreenWidth(mContext) - getContext().getResources().getDimensionPixelSize(R.dimen.dp_48) * 2);
                getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        }
        super.show();
    }
}
