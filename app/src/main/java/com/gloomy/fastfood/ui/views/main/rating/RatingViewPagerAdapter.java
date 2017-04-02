package com.gloomy.fastfood.ui.views.main.rating;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.gloomy.fastfood.ui.views.main.rating.people.RatingPeopleFragment_;
import com.gloomy.fastfood.ui.views.main.rating.store.RatingStoreFragment_;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 31-Mar-17.
 */
public class RatingViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int PAGE_NUM = 2;

    public RatingViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RatingStoreFragment_.builder().build();
            case 1:
                return RatingPeopleFragment_.builder().build();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
