package com.gloomy.fastfood.ui.views.main.search;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.gloomy.fastfood.ui.views.main.search.food.SearchFoodFragment_;
import com.gloomy.fastfood.ui.views.main.search.people.SearchPeopleFragment_;
import com.gloomy.fastfood.ui.views.main.search.topic.SearchTopicFragment_;
import com.gloomy.fastfood.ui.views.main.search.store.SearchStoreFragment_;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
public class SearchViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int PAGE_NUM = 4;

    public SearchViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SearchStoreFragment_.builder().build();
            case 1:
                return SearchFoodFragment_.builder().build();
            case 2:
                return SearchPeopleFragment_.builder().build();
            case 3:
                return SearchTopicFragment_.builder().build();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
