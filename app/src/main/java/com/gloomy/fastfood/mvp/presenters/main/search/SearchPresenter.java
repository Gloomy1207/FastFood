package com.gloomy.fastfood.mvp.presenters.main.search;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.SearchView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.main.search.IViewSearch;
import com.gloomy.fastfood.mvp.views.main.search.SearchViewPagerAdapter;
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
public class SearchPresenter extends BasePresenter {
    @StringArrayRes(R.array.fragment_search_view_pager_titles)
    String[] mTabTitles;

    private static final int[] TAB_ICONS = {
            R.drawable.ic_store_on,
            R.drawable.ic_food_on,
            R.drawable.ic_people_on,
            R.drawable.ic_post_on
    };

    @Setter
    @Accessors(prefix = "m")
    private IViewSearch mView;

    private ViewPager mViewPager;

    public void initViewPager(ViewPager viewPager, TabLayout tabLayout, FragmentManager fragmentManager) {
        mViewPager = viewPager;
        SearchViewPagerAdapter adapter = new SearchViewPagerAdapter(fragmentManager);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(SearchViewPagerAdapter.PAGE_NUM);
        TabLayoutUtil.setCustomViewsTabLayout(tabLayout, mTabTitles, TAB_ICONS, mContext);
    }

    public void initSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mView.onSearchSubmit(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // No-op
                return false;
            }
        });
    }
}
