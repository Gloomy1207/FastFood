package com.gloomy.fastfood.mvp.presenters.main.topic;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.main.topic.IViewTopic;
import com.gloomy.fastfood.mvp.views.main.topic.TopicViewPagerAdapter;
import com.gloomy.fastfood.utils.TabLayoutUtil;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringArrayRes;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EBean
public class TopicPresenter extends BasePresenter {
    private static final int[] PAGE_ICONS = {
            R.drawable.ic_topic_hot,
            R.drawable.ic_topic_trending,
            R.drawable.ic_topic_fresh,
            R.drawable.ic_topic_random
    };

    @StringArrayRes(R.array.fragment_topic_view_pager_titles)
    String[] mViewPagerTitles;

    @Setter
    @Accessors(prefix = "m")
    private IViewTopic mView;

    public void initViewPager(ViewPager viewPager, TabLayout tabLayout, FragmentManager fragmentManager) {
        TopicViewPagerAdapter adapter = new TopicViewPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(TopicViewPagerAdapter.PAGE_NUM);
        tabLayout.setupWithViewPager(viewPager);
        TabLayoutUtil.setCustomViewsTabLayout(tabLayout, mViewPagerTitles, PAGE_ICONS, mContext);
    }

    public void onPostClick() {
        // TODO: 31-Mar-17 Handle when post click
    }

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setLeftButtonVisibility(View.INVISIBLE);
        headerBar.setTitle(getString(R.string.footer_bar_topic));
    }
}
