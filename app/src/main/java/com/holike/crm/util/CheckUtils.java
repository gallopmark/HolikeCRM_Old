package com.holike.crm.util;

import android.text.TextUtils;

public class CheckUtils {
    public static boolean isMobile(String phone) {
        if (TextUtils.isEmpty(phone)) return false;
        String regex = "[1]\\d{10}";
        return phone.matches(regex);
    }
}
