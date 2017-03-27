package com.gloomy.fastfood.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gloomy.fastfood.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EViewGroup(R.layout.layout_header_bar)
public class HeaderBar extends RelativeLayout {

    @ViewById(R.id.tvHeaderTitle)
    TextView mTvHeaderTitle;

    @ViewById(R.id.btnLeft)
    ImageView mBtnLeft;

    @ViewById(R.id.btnRight)
    ImageView mBtnRight;

    public HeaderBar(Context context) {
        this(context, null);
    }

    public HeaderBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, getResources().getDimensionPixelOffset(R.dimen.header_bar_height));
    }

    public void setTitle(String title) {
        mTvHeaderTitle.setText(title);
    }
}
