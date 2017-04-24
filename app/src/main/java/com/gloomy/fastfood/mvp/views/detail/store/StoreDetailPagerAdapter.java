package com.gloomy.fastfood.mvp.views.detail.store;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.gloomy.fastfood.mvp.views.detail.store.comment.StoreCommentFragment_;
import com.gloomy.fastfood.mvp.views.detail.store.menu.StoreMenuFragment_;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
public class StoreDetailPagerAdapter extends FragmentStatePagerAdapter {
    public static final int NUM_PAGE = 2;

    public StoreDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return StoreMenuFragment_.builder().build();
        } else {
            return StoreCommentFragment_.builder().build();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGE;
    }
}
