package com.gloomy.fastfood.mvp.views.main.search.result;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.presenters.main.search.result.SearchResultPresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
@EFragment(R.layout.fragment_search_result)
public class SearchResultFragment extends BaseFragment implements IViewSearchResult {

    @FragmentArg
    String mQuery;

    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.tabLayout)
    TabLayout mTabLayout;

    @ViewById(R.id.searchView)
    SearchView mSearchView;

    @ViewById(R.id.layoutParent)
    LinearLayout mLayoutParent;

    @Bean
    SearchResultPresenter mPresenter;

    @AfterViews
    void afterViews() {
        hideKeyboard();
        mPresenter.setView(this);
        mPresenter.initViewPager(mViewPager, mTabLayout, getChildFragmentManager());
        mPresenter.initSearchView(mSearchView, mQuery);
        mLayoutParent.requestFocus();
    }
}
