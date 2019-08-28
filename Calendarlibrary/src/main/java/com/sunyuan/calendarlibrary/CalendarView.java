package com.sunyuan.calendarlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

import com.sunyuan.calendarlibrary.model.CalendarSelectDay;

public class CalendarView extends RecyclerView {


    private CalendarAdapter calendarAdapter;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);
        setLayoutManager(new LinearLayoutManager(context));
        calendarAdapter = new CalendarAdapter(context, typedArray);
        setAdapter(calendarAdapter);
        typedArray.recycle();
    }

    /**
     * 设置RecycleView itemDecoration,设置数据时会回调MonthTitleViewCallBack中getMonthTitleView方法
     */
    public void setMonthTitleViewCallBack(MonthTitleViewCallBack monthTitleViewCallBack) {
        boolean isShowMonthTitleView = calendarAdapter.isShowMonthTitleView();
        if (isShowMonthTitleView) {
            MonthTitleDecoration monthTitleDecoration = new MonthTitleDecoration();
            monthTitleDecoration.setMonthTitleViewCallBack(monthTitleViewCallBack);
            addItemDecoration(monthTitleDecoration);
        }
    }

    /**
     * 释放 MonthTitleDecoration中资源
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        int itemDecorationCount = getItemDecorationCount();
        for (int itemDecorationIndex = 0; itemDecorationIndex < itemDecorationCount; itemDecorationIndex++) {
            ItemDecoration itemDecoration = getItemDecorationAt(itemDecorationIndex);
            if (itemDecoration instanceof MonthTitleDecoration) {
                ((MonthTitleDecoration) itemDecoration).destroy();
                break;
            }
        }
    }

    public void setOnCalendarSelectDayListener(OnCalendarSelectDayListener
                                                       onCalendarSelectDayListener) {
        calendarAdapter.setOnCalendarSelectDayListener(onCalendarSelectDayListener);
    }

    public void setCalendarSelectDay(CalendarSelectDay calendarSelectDay) {
        calendarAdapter.setCalendarSelectDay(calendarSelectDay);
        calendarAdapter.notifyDataSetChanged();
    }


    public interface OnCalendarSelectDayListener<K> {
        /**
         * 回调选中日期
         * 当isSingleSelect为true时,calendarSelectDay中firstSelectDay为单选后的值lastSelectDay为null。
         * 当isSingleSelect为false时,calendarSelectDay中firstSelectDay为第一次选择的日期,lastSelectDay为最后一次选择的日期
         */
        void onCalendarSelectDay(CalendarSelectDay<K> calendarSelectDay);
    }


}
