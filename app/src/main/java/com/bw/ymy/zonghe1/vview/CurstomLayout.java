package com.bw.ymy.zonghe1.vview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class CurstomLayout  extends LinearLayout {
    //孩子最高的一个
    private int mChildMaxHeight;

    //每一个左边距20 px
    private int mHSpace = 20;

    //上下间距 20 px
    private int mVSpace = 20;

    public CurstomLayout(Context context) {
        super(context);
    }

    public CurstomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //拿到父容器的宽高和计算模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //测量孩子大小，必须写
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //寻找孩子最高的一个
        //在下方
        findMaxChildMaxHeight();

        //初始化值
        int left = 0, top = 0;

        //开始循环
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            //是否是一行的第一个
            if (left != 0) {
                //换行，放不下
                if ((left + view.getMeasuredWidth()) > sizeWidth) {
                    top += mChildMaxHeight + mVSpace;
                    left = 0;
                }
            }
            left += view.getMeasuredWidth() + mHSpace;
        }
        setMeasuredDimension(sizeWidth, (top + mChildMaxHeight) > sizeHeight ? sizeHeight : top + mChildMaxHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        findMaxChildMaxHeight();
        //
        //开始
        //初始化
        int left = 0, top = 0;
        //循环
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            //是否第一个
            if (left != 0) {
                if ((left + view.getMeasuredWidth()) > getWidth()) {
                    //计算下一行top
                    top += mChildMaxHeight + mVSpace;
                    //换行
                    left = 0;
                }
            }
            //安排位置
            view.layout(left, top, left + view.getMeasuredWidth(), top + getMeasuredHeight());
            left += view.getMeasuredWidth() + mHSpace;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    //z最高的孩子
    private void findMaxChildMaxHeight() {
        mChildMaxHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view.getMeasuredHeight() > mChildMaxHeight) {
                mChildMaxHeight = view.getMeasuredHeight();
            }
        }
    }
}

