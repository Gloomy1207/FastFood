package com.gloomy.fastfood.mvp.presenters.main.home.favorite;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.HomeFavoriteResponse;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.views.main.home.favorite.HomeFavoriteAdapter;
import com.gloomy.fastfood.mvp.views.main.home.favorite.IHomeFavoriteView;
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
 * Created by HungTQB on 20/04/2017.
 */
@EBean
public class HomeFavoritePresenter extends BasePresenter implements HomeFavoriteAdapter.OnHomeFavoriteListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int NUM_COLUMN = 2;

    @Setter
    @Accessors(prefix = "m")
    private IHomeFavoriteView mView;

    private List<Store> mStores = new ArrayList<>();
    private EndlessScrollListener mEndlessScrollListener;
    private HomeFavoriteResponse mHomeFavoriteResponse;
    private int mCurrentPage;
    private boolean mIsLastPage;

    private View mDisableView;

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(NUM_COLUMN, StaggeredGridLayoutManager.VERTICAL));
        HomeFavoriteAdapter adapter = new HomeFavoriteAdapter(getContext(), mStores, this);
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
        ApiRequest.getInstance().getHomeFavoriteData(mCurrentPage, null, new Callback<HomeFavoriteResponse>() {
            @Override
            public void onResponse(Call<HomeFavoriteResponse> call, Response<HomeFavoriteResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                HomeFavoriteResponse favoriteResponse = response.body();
                HomeFavoriteResponse.FavoritePlaces places = favoriteResponse.getPlaces();
                mStores.addAll(places.getStores());
                mCurrentPage = places.getCurrentPage();
                mIsLastPage = places.isLast();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<HomeFavoriteResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    @Override
    public void onStoreClick(int position) {
        mView.onStoreClick(mStores.get(position));
    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, View disableView) {
        mDisableView = disableView;
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mDisableView.setVisibility(View.VISIBLE);
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.resetValue();
        }
        getHomeFavoriteData(true);
    }

    public void getHomeFavoriteData(boolean isRefresh) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            mDisableView.setVisibility(View.GONE);
            return;
        }
        if (!isRefresh) {
            mView.onShowProgressDialog();
        }
        ApiRequest.getInstance().getHomeFavoriteData(null, null, new Callback<HomeFavoriteResponse>() {
            @Override
            public void onResponse(Call<HomeFavoriteResponse> call, Response<HomeFavoriteResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                mHomeFavoriteResponse = response.body();
                HomeFavoriteResponse.FavoritePlaces places = mHomeFavoriteResponse.getPlaces();
                if (!mHomeFavoriteResponse.isStatus()) {
                    mIsLastPage = true;
                    mView.onEmptyData(mHomeFavoriteResponse.getMessage());
                } else if (places != null && !places.getStores().isEmpty()) {
                    mCurrentPage = places.getCurrentPage();
                    mIsLastPage = places.isLast();
                    mStores.clear();
                    mStores.addAll(places.getStores());
                    mView.onLoadDataComplete();
                } else {
                    mIsLastPage = true;
                    mView.onEmptyData(mHomeFavoriteResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<HomeFavoriteResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        });
    }
}
