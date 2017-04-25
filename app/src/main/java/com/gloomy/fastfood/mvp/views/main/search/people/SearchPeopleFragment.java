package com.gloomy.fastfood.mvp.views.main.search.people;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.presenters.main.search.people.SearchPeoplePresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
@EFragment(R.layout.fragment_search_people)
public class SearchPeopleFragment extends BaseFragment implements ISearchPeopleView {

    @Bean
    SearchPeoplePresenter mPresenter;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.disableView)
    View mDisableView;

    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.initSwipeRefresh(mSwipeRefreshLayout, mDisableView);
        mPresenter.getSearchPeopleData();
    }

    @Override
    public void onShowProgressDialog() {
        showProgressDialog();
    }

    @Override
    public void onDismissProgressDialog() {
        dismissProgressDialog();
    }

    @Override
    public void onNoInternetConnection() {
        showNoInternetConnectionMessage();
    }

    @Override
    public void onLoadDataComplete() {
        mDisableView.setVisibility(View.GONE);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadDataFailure() {
        mDisableView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        showLoadDataFailure();
    }

    @Override
    public void onRefreshComplete() {
        mDisableView.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemPeopleClick(User user) {
        // TODO: 14/04/2017 Handle when click item people
    }
}
