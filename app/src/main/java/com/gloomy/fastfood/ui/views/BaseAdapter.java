package com.gloomy.fastfood.ui.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 11/04/2017.
 */
public abstract class BaseAdapter<V extends ViewHolder> extends RecyclerView.Adapter<V> {
    private final Context mContext;

    public BaseAdapter(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    protected Context getContext() {
        return mContext;
    }

    protected Resources getResources() {
        return mContext.getResources();
    }
}
