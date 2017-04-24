package com.gloomy.fastfood.mvp.presenters.main.search.result;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.SearchView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.main.search.result.IViewSearchResult;
import com.gloomy.fastfood.mvp.views.main.search.result.SearchResultViewPagerAdapter;
import com.gloomy.fastfood.utils.TabLayoutUtil;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringArrayRes;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
@EBean
public class SearchResultPresenter extends BasePresenter {
    @StringArrayRes(R.array.fragment_search_result_view_pager_title)
    String[] mPagerTitles;

    private static final int[] PAGER_ICONS = {
            R.drawable.ic_all_on,
            R.drawable.ic_store_on,
            R.drawable.ic_food_on,
            R.drawable.ic_people_on,
            R.drawable.ic_post_on
    };

    @Accessors(prefix = "m")
    @Setter
    private IViewSearchResult mView;

    private ViewPager mViewPager;

    public void initViewPager(ViewPager viewPager, TabLayout tabLayout, FragmentManager fragmentManager) {
        mViewPager = viewPager;
        SearchResultViewPagerAdapter adapter = new SearchResultViewPagerAdapter(fragmentManager);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
        TabLayoutUtil.setCustomViewsTabLayout(tabLayout, mPagerTitles, PAGER_ICONS, mContext);
    }

    public void initSearchView(SearchView searchView, String query) {
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
        searchView.setQuery(query, false);
    }
}
