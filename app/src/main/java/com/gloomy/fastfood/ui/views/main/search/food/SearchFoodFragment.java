package com.gloomy.fastfood.ui.views.main.search.food;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Food;
import com.gloomy.fastfood.ui.BaseFragment;
import com.gloomy.fastfood.ui.presenters.main.search.food.SearchFoodPresenter;
import com.gloomy.fastfood.ui.views.detail.food.FoodDetailActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 30-Mar-17.
 */
@EFragment(R.layout.fragment_search_food)
public class SearchFoodFragment extends BaseFragment implements ISearchFoodView {

    @Bean
    SearchFoodPresenter mPresenter;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @ViewById(R.id.disableView)
    View mDisableView;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.initSwipeRefresh(mSwipeRefreshLayout, mDisableView);
        mPresenter.getDataSearchFood();
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
        showNoInternetConnectionMessage();
    }

    @Override
    public void onLoadDataComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadDataFailure() {
        mSwipeRefreshLayout.setRefreshing(false);
        mDisableView.setVisibility(View.GONE);
        showLoadDataFailure();
    }

    @Override
    public void onRefreshComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
        mDisableView.setVisibility(View.GONE);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemSearchFoodClick(Food food) {
        FoodDetailActivity_.intent(getActivity()).mFoodParcelable(Parcels.wrap(food)).start();
        getActivity().overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    }

    @Override
    public void notifyDataSetChanged() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
