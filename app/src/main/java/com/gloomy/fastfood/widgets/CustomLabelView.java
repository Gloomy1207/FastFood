package com.gloomy.fastfood.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.gloomy.fastfood.utils.LabelViewHelper;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 4/24/17.
 */
public class CustomLabelView extends AppCompatTextView {
    private LabelViewHelper mLaLabelViewHelper;

    public CustomLabelView(Context context) {
        this(context, null);
    }

    public CustomLabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLaLabelViewHelper = new LabelViewHelper(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLaLabelViewHelper.onDraw(canvas, getMeasuredWidth(), getMeasuredHeight());
    }
}
