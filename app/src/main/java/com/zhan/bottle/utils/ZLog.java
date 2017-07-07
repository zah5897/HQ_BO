package com.zhan.bottle.utils;

import android.util.Log;

/**
 * Created by zah on 2017/6/15.
 */

public final class ZLog {
    public static final String TAG = "Z_DEBUG";

    public static final void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static final void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static final void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static final void e(String msg) {
        Log.e(TAG, msg);
    }

    public static final void e(String tag, String msg, Throwable e) {
        Log.e(tag, msg, e);
    }
}
