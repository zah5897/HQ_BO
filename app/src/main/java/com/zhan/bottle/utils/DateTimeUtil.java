package com.zhan.bottle.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zah on 2017/6/29.
 */

public class DateTimeUtil {

    public static String getNowStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }
}
