package com.zhan.bottle.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by zah on 2017/6/30.
 */

public class ShapeUtil {


    public static GradientDrawable createRoundBg(String borderColorStr, String fillColorStr) {
        int strokeWidth = 5; // 3dp 边框宽度
        int roundRadius = 15; // 8dp 圆角半径
        int strokeColor = Color.parseColor(borderColorStr);//边框颜色
        int fillColor = Color.parseColor(fillColorStr);//内部填充颜色
        return createRoundBg(strokeColor, fillColor);
    }

    public static GradientDrawable createRoundBg(int strokeColor, int fillColor) {
        int strokeWidth = 5; // 3dp 边框宽度
        int roundRadius = 15; // 8dp 圆角半径
//        int strokeColor = Color.parseColor(borderColorStr);//边框颜色
//        int fillColor = Color.parseColor(fillColorStr);//内部填充颜色
//        int strokeColor = Color.parseColor("#2E3135");//边框颜色
//        int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }
}
