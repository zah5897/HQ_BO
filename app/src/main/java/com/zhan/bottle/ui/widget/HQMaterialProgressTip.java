package com.zhan.bottle.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhan.bottle.R;


/**
 * Created by zah on 2016/11/29.
 */

public class HQMaterialProgressTip {
    private View view;
    PopupWindow pop;

    public HQMaterialProgressTip(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.view_alert_window_spot_layout, null);
        ((TextView) view.findViewById(R.id.message)).setText(msg);
        //创建PopupWindow，参数为显示对象，宽，高
        pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //PopupWindow的设置
        pop.setBackgroundDrawable(new BitmapDrawable());
        //点击外边消失
        pop.setOutsideTouchable(false);
        //设置此参数获得焦点，否则无法点击
        pop.setFocusable(false);
        pop.setAnimationStyle(R.anim.popup_animation);
    }

    public void setMsg(String msg) {
        ((TextView) view.findViewById(R.id.message)).setText(msg);
    }

    public void show() {
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void dismiss() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
    }

    public boolean isShow() {
        if (pop != null && pop.isShowing()) {
            return true;
        }
        return false;
    }
}
