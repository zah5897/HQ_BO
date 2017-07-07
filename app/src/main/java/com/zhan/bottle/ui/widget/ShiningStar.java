package com.zhan.bottle.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.zhan.bottle.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ShiningStar extends View {

    /**
     * 星星的坐标及大小
     */
//    private static final int[][] starPosition = new int[][]{
//            {40, 40, 40}, {80, 40, 50}, {120, 80, 30}, {60, 120, 60}, {180, 240, 66}, {300, 600, 120}, {720, 500, 120},
//            {360, 100, 66}, {600, 160, 120}, {720, 240, 120}, {860, 80, 80}
//    };

    /**
     * 星星存储器
     */
    private List<Star> stars = new ArrayList<Star>();

    /**
     * 星星资源
     */
    private Bitmap bitmap = null;

    /**
     * 画笔
     */
    private Paint paint = null;

    private int width;
    private int height;

    private Random random = new Random();
    boolean hasInit = false;

    public ShiningStar(Context context) {
        super(context);
    }

    private int type = 0;

    public void changeNightLight(int type) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        width = widthSize;
        height = width / 2;
        setMeasuredDimension(width, height);
        if (width > 0 && !hasInit) {
            init();
        }

    }

    public ShiningStar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShiningStar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) {
            for (int i = 0; i < stars.size(); i++) {
                canvas.save();//这样使每一个星星的状态独立
                Rect dst = new Rect(stars.get(i).x, stars.get(i).y, stars.get(i).x + stars.get(i).size, stars.get(i).y + stars.get(i).size);
                canvas.scale(stars.get(i).scale, stars.get(i).scale, stars.get(i).x + stars.get(i).size / 2, stars.get(i).y + stars.get(i).size / 2);
                paint.setAlpha((int) stars.get(i).alpha);
                canvas.drawBitmap(bitmap, null, dst, paint);
                canvas.restore();
            }
        }
    }

    /**
     * 初始化
     */
    private void init() {
        hasInit = true;
        initStars();
        initAnimation();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int now = c.get(Calendar.HOUR_OF_DAY) + 1;

        if (now > 6 && now < 19 && type == 1) {
            changeNightLight(0);
        } else {
            changeNightLight(1);
        }
    }

    /**
     * 初始化星星对象
     */
    private void initStars() {
        for (int i = 0; i < 10; i++) {
            final Star star = new Star();
            star.x = getRandowX();
            star.y = getRandowY();
            star.size = getRandowSize();
            star.scale = 1;
            star.alpha = 255;
            stars.add(star);
        }
    }


    private int getRandowX() {
        return random.nextInt(width);
    }

    private int getRandowY() {
        return 10 + random.nextInt(height / 2);
    }

    private int getRandowSize() {
        return 15 + random.nextInt(10);
    }

    /**
     * 初始化动画及绘制元素的对象
     */
    private void initAnimation() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sun);
        paint = new Paint();
        paint.setAlpha(255);

        ValueAnimator scaleAnim = ValueAnimator.ofFloat(0, 255, 0);
        scaleAnim.setInterpolator(new LinearInterpolator());
        scaleAnim.setDuration(3000);
        scaleAnim.setRepeatCount(-1);
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                boolean flag = false;
                for (int i = 0; i < stars.size(); i++) {
                    if (flag) {
                        stars.get(i).scale = ((float) animation.getAnimatedValue()) / 255;
                        stars.get(i).alpha = (float) animation.getAnimatedValue();
                    } else {
                        stars.get(i).scale = 1 - ((float) animation.getAnimatedValue()) / 255;
                        stars.get(i).alpha = 255 - (float) animation.getAnimatedValue();
                    }
                    flag = !flag;
                }
                postInvalidate();
            }
        });
        scaleAnim.start();
    }

    /**
     * 星星属性
     */
    class Star {
        int x;
        int y;
        int size;
        float scale;
        float alpha;
    }

    class Sun {
        int x;
        int y;
        int size;
        float scale;
        float alpha;
    }


}