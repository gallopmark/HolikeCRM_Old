package com.holike.crm.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectUtil {
    public static List<String> eachProperties(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        List<String> ss = new ArrayList<>();
        for (int i = 0, len = fields.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o;
                try {
                    o = fields[i].get(obj);
                    LogCat.d("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                    ss.add((String) o);
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        return ss;

    }
}
