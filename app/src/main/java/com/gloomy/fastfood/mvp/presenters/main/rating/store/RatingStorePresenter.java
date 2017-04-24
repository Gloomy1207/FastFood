package com.gloomy.fastfood.mvp.presenters.main.rating.store;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.RatingStoreResponse;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.views.main.rating.store.IRatingStoreView;
import com.gloomy.fastfood.mvp.views.main.rating.store.RatingStoreAdapter;
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
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 16-Apr-17.
 */
@EBean
public class RatingStorePresenter extends BasePresenter implements RatingStoreAdapter.OnRatingStoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Setter
    @Accessors(prefix = "m")
    private IRatingStoreView mView;

    private List<Store> mStores = new ArrayList<>();
    private EndlessScrollListener mEndlessScrollListener;
    private boolean mIsLastPage;
    private int mCurrentPage;
    private boolean mIsRefresh;
    private RatingStoreResponse mRatingStoreResponse;

    private View mDisableView;

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RatingStoreAdapter adapter = new RatingStoreAdapter(getContext(), mStores, this);
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
        ApiRequest.getInstance().getRatingStoreData(mCurrentPage, null, new Callback<RatingStoreResponse>() {
            @Override
            public void onResponse(Call<RatingStoreResponse> call, Response<RatingStoreResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                RatingStoreResponse storeResponse = response.body();
                mStores.addAll(storeResponse.getStores());
                mCurrentPage = storeResponse.getCurrentPage();
                mIsLastPage = storeResponse.isLast();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<RatingStoreResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    @Override
    public void onItemStoreClick(int position) {
        mView.onItemStoreClick(mStores.get(position));
    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, View disableView) {
        swipeRefreshLayout.setOnRefreshListener(this);
        mDisableView = disableView;
    }

    @Override
    public void onRefresh() {
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.resetValue();
        }
        mIsRefresh = true;
        mDisableView.setVisibility(View.VISIBLE);
        getRatingStoreData();
    }

    public void getRatingStoreData() {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mIsRefresh = false;
            mView.onNoInternetConnection();
            mDisableView.setVisibility(View.GONE);
            return;
        }
        if (!mIsRefresh) {
            mView.onShowProgressDialog();
        }
        ApiRequest.getInstance().getRatingStoreData(null, null, new Callback<RatingStoreResponse>() {
            @Override
            public void onResponse(Call<RatingStoreResponse> call, Response<RatingStoreResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                mRatingStoreResponse = response.body();
                mStores.clear();
                mStores.addAll(mRatingStoreResponse.getStores());
                mIsLastPage = mRatingStoreResponse.isLast();
                mCurrentPage = mRatingStoreResponse.getCurrentPage();
                mIsRefresh = false;
                mView.onLoadDataComplete();
            }

            @Override
            public void onFailure(Call<RatingStoreResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        });
    }
}
