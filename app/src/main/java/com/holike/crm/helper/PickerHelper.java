package com.holike.crm.helper;

import android.content.Context;

import androidx.annotation.Nullable;

import com.holike.crm.bean.DictionaryBean;
import com.holike.crm.dialog.DatetimePickerDialog;
import com.holike.crm.dialog.OptionsPickerDialog;

import java.util.Date;
import java.util.List;


/**
 * Created by pony on 2019/7/25.
 * Copyright holike possess 2019.
 */
public class PickerHelper {

    /*显示时间选择器，“年”、“月”*/
    public static void showTimeYMPicker(Context context, DatetimePickerDialog.OnDatetimePickerListener listener) {
        new DatetimePickerDialog.Builder(context)
                .withType(DatetimePickerDialog.TYPE_Y_M)
                .listener(listener).show();
    }

    /*显示时间选择器，默认 “年”“月”“日”*/
    public static void showTimePicker(Context context, DatetimePickerDialog.OnDatetimePickerListener listener) {
        new DatetimePickerDialog.Builder(context)
                .withType(DatetimePickerDialog.TYPE_Y_M_D)
                .listener(listener).show();
    }

    public static void showTimePicker2(Context context, Date selectDate, DatetimePickerDialog.OnDatetimePickerListener listener) {
        new DatetimePickerDialog.Builder(context)
                .withType(DatetimePickerDialog.TYPE_Y_M_D)
                .selectDate(selectDate)
                .listener(listener).show();
    }

    public static void showTimePicker(Context context, Date minDate, DatetimePickerDialog.OnDatetimePickerListener listener) {
        showTimePicker(context, minDate, null, listener);
    }

    public static void showTimePicker(Context context, @Nullable Date minDate, @Nullable Date maxDate,
                                      DatetimePickerDialog.OnDatetimePickerListener listener) {
        new DatetimePickerDialog.Builder(context)
                .withType(DatetimePickerDialog.TYPE_Y_M_D)
                .minDate(minDate)
                .maxDate(maxDate)
                .listener(listener).show();
    }

    /*显示时间选择器，“时”，“分”*/
    public static void showTimeHMPicker(Context context, DatetimePickerDialog.OnDatetimePickerListener listener) {
        new DatetimePickerDialog.Builder(context)
                .withType(DatetimePickerDialog.TYPE_HM)
                .listener(listener).show();
    }

    public static void showOptionsPicker(Context context, List<DictionaryBean> optionsItems, OptionsPickerDialog.OnOptionPickerListener listener) {
        showOptionsPicker(context, optionsItems, 0, listener);
    }

    public static void showOptionsPicker(Context context, List<DictionaryBean> optionsItems, int selectPosition,
                                         OptionsPickerDialog.OnOptionPickerListener listener) {
        new OptionsPickerDialog(context).withData(optionsItems, selectPosition).listener(listener).show();
    }
}
