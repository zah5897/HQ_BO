package com.zhan.bottle.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zhan.bottle.app.Application;

/**
 * 偏好参数存储工具类
 */
public class PrefUtil {
    public static final String SHARED_PREFERENCES_NAME = "zperf";
    private static volatile PrefUtil prefUtil;
    private SharedPreferences mSharedPreferences;
    private Editor mEditor;

    private PrefUtil() {
        mSharedPreferences = Application.getApp().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static PrefUtil get() {
        if (prefUtil == null) {
            synchronized (PrefUtil.class) {
                if (prefUtil == null) {
                    prefUtil = new PrefUtil();
                }
            }
        }
        return prefUtil;
    }

    /**
     * 存储数据(Long)
     */
    public void putLong(String key, long value) {
        mEditor.putLong(key, value).apply();
    }

    /**
     * 存储数据(Int)
     */
    public void putInt(String key, int value) {
        mEditor.putInt(key, value).apply();
    }

    /**
     * 存储数据(String)
     */
    public void putString(String key, String value) {
        mEditor.putString(key, value).apply();
    }

    /**
     * 存储数据(boolean)
     */
    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).apply();
    }

    /**
     * 取出数据(Long)
     */
    public long getLong(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    /**
     * 取出数据(int)
     */
    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    /**
     * 取出数据(boolean)
     */
    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 取出数据(String)
     */
    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    /**
     * 清空所有数据
     */
    public void clear() {
        mEditor.clear().apply();
    }

    /**
     * 移除指定数据
     */
    public void remove(String key) {
        mEditor.remove(key).apply();
    }


}