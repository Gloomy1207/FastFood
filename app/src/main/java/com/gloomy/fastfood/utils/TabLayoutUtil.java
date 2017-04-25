package com.gloomy.fastfood.utils;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
public final class TabLayoutUtil {

    private TabLayoutUtil() {
        // No-op
    }

    public static List<View> getCustomHeaders(String[] titles, int[] resIds, Context context) {
        List<View> views = new ArrayList<>();
        if (titles.length != resIds.length) {
            throw new IllegalArgumentException("Number of titles must be equal with number of resIds");
        }
        for (int i = 0, n = titles.length; i < n; i++) {
            views.add(getCustomHeader(titles[i], resIds[i], context));
        }
        return views;
    }

    public static View getCustomHeader(String title, int resId, Context context) {
        View view = View.inflate(context, R.layout.layout_home_tab_layout, null);
        TextView tvTabTitle = (TextView) view.findViewById(R.id.tvTabTitle);
        ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
        tvTabTitle.setText(title);
        Picasso.with(context)
                .load(resId)
                .into(imgIcon);
        return view;
    }

    public static View getCustomHeader(int resId, Context context) {
        View view = View.inflate(context, R.layout.layout_home_tab_layout, null);
        TextView tvTabTitle = (TextView) view.findViewById(R.id.tvTabTitle);
        ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
        tvTabTitle.setVisibility(View.GONE);
        Picasso.with(context)
                .load(resId)
                .into(imgIcon);
        return view;
    }

    public static View getCustomHeader(String title, Context context) {
        View view = View.inflate(context, R.layout.layout_home_tab_layout, null);
        TextView tvTabTitle = (TextView) view.findViewById(R.id.tvTabTitle);
        ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
        tvTabTitle.setVisibility(View.VISIBLE);
        imgIcon.setVisibility(View.GONE);
        tvTabTitle.setText(title);
        return view;
    }

    public static void setCustomViewsTabLayout(TabLayout tabLayout, List<View> views) {
        for (int i = 0, n = tabLayout.getTabCount(); i < n; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(views.get(i));
            }
        }
    }

    public static void setCustomViewsTabLayout(TabLayout tabLayout, String[] titles, int[] resIds, Context context) {
        for (int i = 0, n = tabLayout.getTabCount(); i < n; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getCustomHeader(titles[i], resIds[i], context));
            }
        }
    }

    public static void setCustomViewsTabLayout(TabLayout tabLayout, int[] resIds, Context context) {
        for (int i = 0, n = tabLayout.getTabCount(); i < n; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getCustomHeader(resIds[i], context));
            }
        }
    }

    public static void setCustomViewsTabLayout(TabLayout tabLayout, String[] titles, Context context) {
        for (int i = 0, n = tabLayout.getTabCount(); i < n; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getCustomHeader(titles[i], context));
            }
        }
    }
}
