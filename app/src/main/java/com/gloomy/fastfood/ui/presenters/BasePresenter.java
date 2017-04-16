package com.gloomy.fastfood.ui.presenters;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EBean
public abstract class BasePresenter {
    protected static final int LOAD_MORE_THRESHOLD = 15;

    @RootContext
    protected Context mContext;

    protected String getString(int resId) {
        return mContext.getString(resId);
    }

    protected Context getContext() {
        return mContext;
    }
}
