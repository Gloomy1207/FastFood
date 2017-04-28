package com.gloomy.fastfood.mvp.presenters.main.search.result;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.SearchView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.SearchResultResponse;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.main.search.result.IViewSearchResult;
import com.gloomy.fastfood.mvp.views.main.search.result.SearchResultViewPagerAdapter;
import com.gloomy.fastfood.observer.SearchResultObserver;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.utils.TabLayoutUtil;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringArrayRes;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
@EBean
public class SearchResultPresenter extends BasePresenter {
    @StringArrayRes(R.array.fragment_search_result_view_pager_title)
    String[] mPagerTitles;

    private static final int[] PAGER_ICONS = {
            R.drawable.ic_store_on,
            R.drawable.ic_food_on,
            R.drawable.ic_people_on,
            R.drawable.ic_post_on
    };

    @Accessors(prefix = "m")
    @Setter
    private IViewSearchResult mView;

    public void initViewPager(ViewPager viewPager, TabLayout tabLayout, FragmentManager fragmentManager) {
        SearchResultViewPagerAdapter adapter = new SearchResultViewPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(SearchResultViewPagerAdapter.PAGE_NUM);
        tabLayout.setupWithViewPager(viewPager);
        TabLayoutUtil.setCustomViewsTabLayout(tabLayout, mPagerTitles, PAGER_ICONS, mContext);
    }

    public void initSearchView(SearchView searchView, String query) {
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
        searchView.setQuery(query, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // No-op
                return false;
            }
        });
    }

    public void searchData(String query) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        mView.onShowProgressDialog();
        ApiRequest.getInstance().searchData(query, new Callback<SearchResultResponse>() {
            @Override
            public void onResponse(Call<SearchResultResponse> call, Response<SearchResultResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                SearchResultObserver.post(response.body());
            }

            @Override
            public void onFailure(Call<SearchResultResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        });
    }
}
