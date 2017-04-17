package com.gloomy.fastfood.ui.presenters.main.search.food;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.SearchFoodResponse;
import com.gloomy.fastfood.models.Food;
import com.gloomy.fastfood.models.LatLng;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.presenters.EndlessScrollListener;
import com.gloomy.fastfood.ui.views.main.search.food.ISearchFoodView;
import com.gloomy.fastfood.ui.views.main.search.food.SearchFoodAdapter;
import com.gloomy.fastfood.utils.LocationUtil;
import com.gloomy.fastfood.utils.NetworkUtil;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
@EBean
public class SearchFoodPresenter extends BasePresenter implements SwipeRefreshLayout.OnRefreshListener, Callback<SearchFoodResponse>, SearchFoodAdapter.OnSearchFoodListener {
    private static final int LOAD_MORE_THRESHOLD = 15;

    @Setter
    @Accessors(prefix = "m")
    private ISearchFoodView mView;
    private List<Food> mFoods = new ArrayList<>();
    private EndlessScrollListener mEndlessScrollListener;
    private LatLng mCurrentPosition;
    private int mCurrentPage;
    private boolean mIsLastPage;
    private boolean mIsRefresh;
    private SearchFoodResponse mSearchFoodResponse;

    private View mDisableView;

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SearchFoodAdapter adapter = new SearchFoodAdapter(getContext(), mFoods, this);
        recyclerView.setAdapter(adapter);
        mEndlessScrollListener = new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        };
        recyclerView.addOnScrollListener(mEndlessScrollListener);
    }

    private void loadMoreData() {
        if (mIsLastPage) {
            return;
        }
        mCurrentPage++;
        ApiRequest.getInstance().getSearchFoodData(mCurrentPage, null, mCurrentPosition, new Callback<SearchFoodResponse>() {
            @Override
            public void onResponse(Call<SearchFoodResponse> call, Response<SearchFoodResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                SearchFoodResponse foodResponse = response.body();
                mFoods.addAll(foodResponse.getFoods());
                mCurrentPage = foodResponse.getCurrentPage();
                mIsLastPage = foodResponse.isLast();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<SearchFoodResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, View disableView) {
        mDisableView = disableView;
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mDisableView.setVisibility(View.VISIBLE);
        mIsRefresh = true;
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.resetValue();
        }
        getDataSearchFood();
    }

    public void getDataSearchFood() {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            mDisableView.setVisibility(View.GONE);
            return;
        }
        mCurrentPosition = LocationUtil.getCurrentLatLng(getContext());
        if (!mIsRefresh) {
            mView.onShowProgressDialog();
        }
        if (mSearchFoodResponse == null || mIsRefresh) {
            ApiRequest.getInstance().getSearchFoodData(null, null, mCurrentPosition, this);
        } else {
            initValueForRecyclerView();
        }
    }

    @Override
    public void onResponse(Call<SearchFoodResponse> call, Response<SearchFoodResponse> response) {
        mView.onDismissProgressDialog();
        if (response == null || response.body() == null) {
            return;
        }
        mSearchFoodResponse = response.body();
        initValueForRecyclerView();
    }

    @Override
    public void onFailure(Call<SearchFoodResponse> call, Throwable t) {
        mView.onDismissProgressDialog();
        mView.onLoadDataFailure();
    }

    @Override
    public void onItemSearchFoodClick(int position) {
        mView.onItemSearchFoodClick(mFoods.get(position));
    }

    private void initValueForRecyclerView() {
        mFoods.clear();
        mFoods.addAll(mSearchFoodResponse.getFoods());
        mCurrentPage = mSearchFoodResponse.getCurrentPage();
        mIsLastPage = mSearchFoodResponse.isLast();
        if (!mIsRefresh) {
            mView.onLoadDataComplete();
        } else {
            mView.onRefreshComplete();
        }
    }
}
