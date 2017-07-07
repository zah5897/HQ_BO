package com.zhan.bottle.utils;

import android.text.TextUtils;

/**
 * Created by zah on 2017/6/29.
 */

public class Constact {
    public static String URL_ROOT_PREFIX = "http://117.143.221.190:8080";

    public static String getFullURL(String subUrl) {
        return URL_ROOT_PREFIX + subUrl;
    }

    public static String getFullIMGURL(String subUrl) {
        return URL_ROOT_PREFIX + "/avatar/" + subUrl;
    }
}
