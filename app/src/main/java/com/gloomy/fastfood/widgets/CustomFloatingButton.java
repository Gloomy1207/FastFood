package com.gloomy.fastfood.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gloomy.fastfood.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 21/04/2017.
 */
@EViewGroup(R.layout.custom_floating_button)
public class CustomFloatingButton extends LinearLayout {
    @ViewById(R.id.imgStar)
    ImageView mImgStar;

    public CustomFloatingButton(Context context) {
        this(context, null);
    }

    public CustomFloatingButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelected(boolean selected) {
        mImgStar.setSelected(selected);
        super.setSelected(selected);
    }
}
