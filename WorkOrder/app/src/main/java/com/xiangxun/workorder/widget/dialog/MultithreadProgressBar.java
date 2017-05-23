package com.xiangxun.workorder.widget.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by Zhangyuhui/Darly on 2017/5/5.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 在对话框自定义控件中。设置多线程下载直线加载
 */
public class MultithreadProgressBar extends ProgressBar {

    String text;
    Paint mPaint;

    public MultithreadProgressBar(Context context) {
        super(context);
        initText();
    }

    public MultithreadProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initText();
    }

    public MultithreadProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initText();
    }

    //初始化，画笔
    private void initText() {
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.WHITE);
        this.mPaint.setStrokeWidth(10);
    }

    //设置文字内容
    public void setText(float progress) {
        float i = (progress * 100) / this.getMax();
        this.text = String.valueOf(i) + "%";
    }

    @Override
    public synchronized void setProgress(int progress) {
        // TODO Auto-generated method stub
        setText(progress);
        super.setProgress(progress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //this.setText();
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }
}
