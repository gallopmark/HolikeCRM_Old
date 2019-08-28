package com.holike.crm.customView;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.holike.crm.R;
import com.holike.crm.util.DensityUtil;

public class AppToastCompat {
    private Context mContext;
    private Toast mToast;

    private AppToastCompat(Context context, int duration, int gravity) {
        this.mContext = context;
        mToast = new CompatToast(context);
        mToast.setDuration(duration);
        switch (gravity) {
            case CompatToast.Gravity.LOW:
                mToast.setGravity(Gravity.BOTTOM, 0, DensityUtil.dp2px(200));
                break;
            case CompatToast.Gravity.HIGH:
                mToast.setGravity(Gravity.TOP, 0, 0);
                break;
            case CompatToast.Gravity.CENTER:
                mToast.setGravity(Gravity.CENTER, 0, 0);
                break;
        }
    }

    private View getToastView(CharSequence text) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_compat_toast, new LinearLayout(mContext), false);
        TextView textView = view.findViewById(R.id.mToastTextView);
        textView.setText(text);
        return view;
    }

    public static AppToastCompat makeText(Context context, int duration) {
        return makeText(context, duration, -1);
    }

    public static AppToastCompat makeText(Context context, int duration, int gravity) {
        return new AppToastCompat(context, duration, gravity);
    }

    public void show(CharSequence text) {
        View view = getToastView(text);
        mToast.setView(view);
        mToast.show();
    }

    public void cancel() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
