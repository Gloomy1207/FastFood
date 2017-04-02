package com.gloomy.fastfood.ui.presenters.main.post;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.views.main.post.IViewPost;
import com.gloomy.fastfood.ui.views.main.post.PostViewPagerAdapter;
import com.gloomy.fastfood.utils.TabLayoutUtil;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringArrayRes;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EBean
public class PostPresenter extends BasePresenter {
    private static final int[] PAGE_ICONS = {
            R.drawable.
    }

    @StringArrayRes(R.array.fragment_post_view_pager_titles)
    String[] mViewPagerTitles;

    @Setter
    @Accessors(prefix = "m")
    private IViewPost mView;

    public void onPostClick() {
        // TODO: 31-Mar-17 Handle when post click
    }

    public void initViewPager(ViewPager viewPager, TabLayout tabLayout, FragmentManager fragmentManager) {
        PostViewPagerAdapter adapter = new PostViewPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(PostViewPagerAdapter.PAGE_NUM);
        tabLayout.setupWithViewPager(viewPager);
        TabLayoutUtil.setCustomViewsTabLayout(tabLayout, );
    }
}
