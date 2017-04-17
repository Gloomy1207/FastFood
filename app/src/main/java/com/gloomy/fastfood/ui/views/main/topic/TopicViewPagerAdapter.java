package com.gloomy.fastfood.ui.views.main.topic;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.gloomy.fastfood.ui.views.main.topic.content.TopicContentFragment;
import com.gloomy.fastfood.ui.views.main.topic.content.TopicContentFragment_;

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
                return TopicContentFragment_.builder().mTopicType(TopicContentFragment.TopicType.HOT).build();
            case 1:
                return TopicContentFragment_.builder().mTopicType(TopicContentFragment.TopicType.TRENDING).build();
            case 2:
                return TopicContentFragment_.builder().mTopicType(TopicContentFragment.TopicType.FRESH).build();
            case 3:
                return TopicContentFragment_.builder().mTopicType(TopicContentFragment.TopicType.RANDOM).build();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
