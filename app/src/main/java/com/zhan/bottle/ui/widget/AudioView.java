package com.zhan.bottle.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.zhan.bottle.R;
import com.zhan.bottle.utils.DensityUtil;

public class AudioView extends View {

    /**
     * 星星资源
     */
    private Bitmap bitmap = null;
    private Bitmap cloud = null;

    /**
     * 画笔
     */
    private Paint paint = null;

    private int width;
    private int height;
    boolean hasInit = false;

    private int bmgWidth;
    private int cloudWidth;

    private int cloud_x, cloud_y;

    public AudioView(Context context) {
        super(context);
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
        postInvalidate();
    }

    public AudioView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AudioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) {
            Rect dst = new Rect(width - bmgWidth, 50, width, bmgWidth + 50);
            canvas.drawBitmap(bitmap, null, dst, paint);
//            for (int i = 0; i < stars.size(); i++) {
//                canvas.save();//这样使每一个星星的状态独立
//                Rect dst = new Rect(stars.get(i).x, stars.get(i).y, stars.get(i).x + stars.get(i).size, stars.get(i).y + stars.get(i).size);
//                canvas.scale(stars.get(i).scale, stars.get(i).scale, stars.get(i).x + stars.get(i).size / 2, stars.get(i).y + stars.get(i).size / 2);
//                paint.setAlpha((int) stars.get(i).alpha);
//                canvas.drawBitmap(bitmap, null, dst, paint);
//                canvas.restore();
//            }
        }

        if (cloud != null) {
            Rect dst = new Rect(cloud_x, cloud_y, cloud_x + cloudWidth, cloud_y + bmgWidth);
            canvas.drawBitmap(cloud, null, dst, paint);

            dst = new Rect(100, height - bmgWidth, cloudWidth, height - bmgWidth / 2);
            canvas.drawBitmap(cloud, null, dst, paint);

            dst = new Rect(200, 50, cloudWidth + 150, bmgWidth);
            canvas.drawBitmap(cloud, null, dst, paint);
        }
    }

    /**
     * 初始化
     */
    private void init() {
        hasInit = true;
        bmgWidth = DensityUtil.dip2px(getContext(), 50);
        cloudWidth = DensityUtil.dip2px(getContext(), 80);
        cloud_x = width / 2 + cloudWidth;
        cloud_y = height / 2 - (bmgWidth / 2);
        initAnimation();

    }

    /**
     * 初始化动画及绘制元素的对象
     */
    private void initAnimation() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.sun);
        cloud = BitmapFactory.decodeResource(getResources(), R.mipmap.yun);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setAlpha(255);
        postInvalidate();

//        ValueAnimator scaleAnim = ValueAnimator.ofFloat(0, 255, 0);
//        scaleAnim.setInterpolator(new LinearInterpolator());
//        scaleAnim.setDuration(3000);
//        scaleAnim.setRepeatCount(-1);
//        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
////                boolean flag = false;
////                for (int i = 0; i < stars.size(); i++) {
////                    if (flag) {
////                        stars.get(i).scale = ((float) animation.getAnimatedValue()) / 255;
////                        stars.get(i).alpha = (float) animation.getAnimatedValue();
////                    } else {
////                        stars.get(i).scale = 1 - ((float) animation.getAnimatedValue()) / 255;
////                        stars.get(i).alpha = 255 - (float) animation.getAnimatedValue();
////                    }
////                    flag = !flag;
////                }
//                postInvalidate();
//            }
//        });
//        scaleAnim.start();

    }


    class Sun {
        int x;
        int y;
        int size;
        float scale;
        float alpha;
    }


}