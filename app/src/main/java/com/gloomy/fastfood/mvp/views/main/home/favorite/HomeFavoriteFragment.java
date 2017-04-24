package com.gloomy.fastfood.mvp.views.main.home.favorite;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Store;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.presenters.main.home.favorite.HomeFavoritePresenter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 29-Mar-17.
 */
@EFragment(R.layout.fragment_home_favorite)
public class HomeFavoriteFragment extends BaseFragment implements IHomeFavoriteView {

    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.disableView)
    View mDisableView;

    @Bean
    HomeFavoritePresenter mPresenter;

    @ViewById(R.id.tvEmptyMessage)
    TextView mTvEmptyMessage;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.initSwipeRefresh(mSwipeRefreshLayout, mDisableView);
        mPresenter.getHomeFavoriteData(false);
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
        mSwipeRefreshLayout.setRefreshing(false);
        mDisableView.setVisibility(View.GONE);
        showNoInternetConnectionMessage();
    }

    @Override
    public void onStoreClick(Store store) {
        // TODO: 20/04/2017 Handle when click store
    }

    @Override
    public void onLoadDataComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        mDisableView.setVisibility(View.GONE);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadDataFailure() {
        showLoadDataFailure();
    }

    @Override
    public void onLoadMoreComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onEmptyData(String message) {
        mTvEmptyMessage.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mTvEmptyMessage.setText(message);
    }
}
