package com.zhan.bottle.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhan.bottle.R;
import com.zhan.bottle.model.BottleModel;
import com.zhan.bottle.utils.DensityUtil;
import com.zhan.bottle.utils.ZLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zah on 2017/7/4.
 */

public class BottlePoolView extends RelativeLayout {
    private int bottle_w, bottle_h;
    private Random random = new Random();
    private List<BottleModel> models = new ArrayList<>();
    private int width, height;
    boolean hasInited = false;
    private HQMaterialProgressTip progressTip;

    public BottlePoolView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSize > 0 && !hasInited) {
            hasInited = true;
            width = widthSize;
            height = heightSize;
            bottle_w = DensityUtil.dip2px(getContext(), 80);
            bottle_h = DensityUtil.dip2px(getContext(), 46);
//            progressTip = new HQMaterialProgressTip(getContext(), "瓶子正在加载中...");
//            progressTip.show();
            refresh();
        }
    }


    private void refresh() {
        removeAllViews();
        for (BottleModel bottleModel : models) {
            setRandmoXY(bottleModel);
            addBottle(bottleModel);
        }
    }

    private void addBottle(BottleModel bottleModel) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.bottle_icon);
        RelativeLayout.LayoutParams param = new LayoutParams(bottle_w, bottle_h);
        param.setMargins(bottleModel.x, bottleModel.y, 0, 0);
        imageView.setTag(bottleModel);
        addView(imageView, param);
    }

    private void setRandmoXY(BottleModel bottleModel) {
        int left = random.nextInt(width - bottle_w);
        int top = random.nextInt(height - bottle_h);
        bottleModel.x = left;
        bottleModel.y = top;
    }
}
