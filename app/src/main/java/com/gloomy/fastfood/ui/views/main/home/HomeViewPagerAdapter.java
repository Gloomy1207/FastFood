package com.gloomy.fastfood.ui.views.main.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.ui.views.main.home.favorite.HomeFavoriteFragment_;
import com.gloomy.fastfood.ui.views.main.home.food.HomeFoodFragment_;
import com.gloomy.fastfood.ui.views.main.home.place.HomePlaceFragment_;
import com.gloomy.fastfood.ui.views.main.home.store.HomeStoreFragment_;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int PAGE_NOT_LOGIN_NUM = 3;
    public static final int PAGE_LOGIN_NUM = 4;

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFoodFragment_.builder().build();
            case 1:
                return HomeStoreFragment_.builder().build();
            case 2:
                return HomePlaceFragment_.builder().build();
            case 3:
                return HomeFavoriteFragment_.builder().build();
        }
        return null;
    }

    @Override
    public int getCount() {
        if (AuthSession.isLogIn()) {
            return PAGE_LOGIN_NUM;
        } else {
            return PAGE_NOT_LOGIN_NUM;
        }
    }
}
