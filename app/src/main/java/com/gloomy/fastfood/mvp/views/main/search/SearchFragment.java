package com.gloomy.fastfood.mvp.views.main.search;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.SearchView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.presenters.main.search.SearchPresenter;
import com.gloomy.fastfood.mvp.views.main.search.result.SearchResultFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 27-Mar-17.
 */
@EFragment(R.layout.fragment_search)
public class SearchFragment extends BaseFragment implements IViewSearch {

    @ViewById(R.id.searchView)
    SearchView mSearchView;

    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.tabLayout)
    TabLayout mTabLayout;

    @Bean
    SearchPresenter mPresenter;

    private SearchViewPagerAdapter mAdapter;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initViewPager(mViewPager, mTabLayout, getChildFragmentManager(), mAdapter);
        mPresenter.initSearchView(mSearchView);
    }

    @Override
    public void onSearchSubmit(String query) {
        replaceFragment(SearchResultFragment_.builder()
                .mQuery(query)
                .build(), true);
    }

    @Override
    public void onInitViewPageComplete(SearchViewPagerAdapter adapter) {
        mAdapter = adapter;
    }
}
