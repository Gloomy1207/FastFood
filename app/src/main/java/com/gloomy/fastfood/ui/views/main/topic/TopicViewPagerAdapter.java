package com.gloomy.fastfood.ui.views.main.topic;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.gloomy.fastfood.ui.views.main.topic.fresh.TopicFreshFragment_;
import com.gloomy.fastfood.ui.views.main.topic.hot.TopicHotFragment_;
import com.gloomy.fastfood.ui.views.main.topic.random.TopicRandomFragment_;
import com.gloomy.fastfood.ui.views.main.topic.trending.TopicTrendingFragment_;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 31-Mar-17.
 */
public class TopicViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int PAGE_NUM = 4;

    public TopicViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TopicHotFragment_.builder().build();
            case 1:
                return TopicTrendingFragment_.builder().build();
            case 2:
                return TopicFreshFragment_.builder().build();
            case 3:
                return TopicRandomFragment_.builder().build();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
