package com.holike.crm.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import android.widget.TextView;

import com.holike.crm.R;


/**
 * Created by wqj on 2017/12/6.
 * 简单的两个按钮dialog
 */

public class SimpleDialog extends BaseDialog {
    private TextView tvTitle;
    private TextView tvContent;
    private TextView btnLeft, btnRight;
    private ClickListener listener;

    public SimpleDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    int setContentView() {
        return R.layout.dialog_simple;
    }

    @Override
    protected void initView() {
        tvTitle = mContentView.findViewById(R.id.mTitleTv);
        tvContent = mContentView.findViewById(R.id.mContentTv);
        btnLeft = mContentView.findViewById(R.id.btn_dialog_cancel_left);
        btnRight = mContentView.findViewById(R.id.btn_dialog_cancel_right);
        btnLeft.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                listener.left();
            }
        });
        btnRight.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                listener.right();
            }
        });
    }

    public SimpleDialog setDate(String title, String content, String left, String right) {
        tvTitle.setText(title);
        tvContent.setText(content);
        btnLeft.setText(left);
        btnRight.setText(right);
        return this;
    }

    public SimpleDialog setDate(int titleId, int contentId, int leftId, int rightId) {
        tvTitle.setText(mContext.getString(titleId));
        tvContent.setText(mContext.getString(contentId));
        btnLeft.setText(mContext.getString(leftId));
        btnRight.setText(mContext.getString(rightId));
        return this;
    }

    public SimpleDialog setContentGravity(int gravity) {
        tvContent.setGravity(gravity);
        return this;
    }

    public SimpleDialog setListener(ClickListener listener) {
        this.listener = listener;
        return this;
    }

    public interface ClickListener {
        void left();

        void right();
    }

}
