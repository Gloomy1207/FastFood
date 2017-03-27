package com.gloomy.fastfood.ui.views.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.gloomy.fastfood.ui.views.main.home.HomeContainerFragment_;
import com.gloomy.fastfood.ui.views.main.post.PostContainerFragment_;
import com.gloomy.fastfood.ui.views.main.profile.ProfileContainerFragment_;
import com.gloomy.fastfood.ui.views.main.rating.RatingContainerFragment_;
import com.gloomy.fastfood.ui.views.main.search.SearchContainerFragment_;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int TOTAL_PAGE_NUM = 5;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeContainerFragment_.builder().build();
            case 1:
                return SearchContainerFragment_.builder().build();
            case 2:
                return RatingContainerFragment_.builder().build();
            case 3:
                return PostContainerFragment_.builder().build();
            case 4:
                return ProfileContainerFragment_.builder().build();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_PAGE_NUM;
    }
}
