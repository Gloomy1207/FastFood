package com.gloomy.fastfood.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gloomy.fastfood.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 13/04/2017.
 */
public class ItemTitleVH extends RecyclerView.ViewHolder {

    private TextView mTvTitle;

    public ItemTitleVH(Context context, ViewGroup parent) {
        super(getItemView(context, parent));
        mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
    }

    private static View getItemView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.layout_title_view_holder, parent, false);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }
}
