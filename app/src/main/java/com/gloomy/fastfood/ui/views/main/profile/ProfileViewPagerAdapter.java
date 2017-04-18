package com.gloomy.fastfood.ui.views.main.profile;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.gloomy.fastfood.ui.views.main.profile.favorite.ProfileFavoriteFragment_;
import com.gloomy.fastfood.ui.views.main.profile.feeds.ProfileFeedsFragment_;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 01-Apr-17.
 */
public class ProfileViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int PAGE_NUM = 2;

    public ProfileViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ProfileFeedsFragment_.builder().build();
        } else if (position == 1) {
            return ProfileFavoriteFragment_.builder().build();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
