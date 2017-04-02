package com.gloomy.fastfood.ui.views.main.search.result;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.gloomy.fastfood.ui.views.main.search.result.all.SearchResultAllFragment_;
import com.gloomy.fastfood.ui.views.main.search.result.food.SearchResultFoodFragment_;
import com.gloomy.fastfood.ui.views.main.search.result.people.SearchResultPeopleFragment_;
import com.gloomy.fastfood.ui.views.main.search.result.post.SearchResultPostFragment_;
import com.gloomy.fastfood.ui.views.main.search.result.store.SearchResultStoreFragment_;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
public class SearchResultViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int PAGE_NUM = 5;

    public SearchResultViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SearchResultAllFragment_.builder().build();
            case 1:
                return SearchResultStoreFragment_.builder().build();
            case 2:
                return SearchResultFoodFragment_.builder().build();
            case 3:
                return SearchResultPeopleFragment_.builder().build();
            case 4:
                return SearchResultPostFragment_.builder().build();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
