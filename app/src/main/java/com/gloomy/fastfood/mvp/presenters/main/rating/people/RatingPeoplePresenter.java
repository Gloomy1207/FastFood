package com.gloomy.fastfood.mvp.presenters.main.rating.people;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.RatingPeopleResponse;
import com.gloomy.fastfood.models.User;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.views.main.rating.people.IRatingPeopleView;
import com.gloomy.fastfood.mvp.views.main.rating.people.RatingPeopleAdapter;
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
 * Created by HungTQB on 17/04/2017.
 */
@EBean
public class RatingPeoplePresenter extends BasePresenter implements RatingPeopleAdapter.OnRatingPeopleListener, SwipeRefreshLayout.OnRefreshListener {

    @Setter
    @Accessors(prefix = "m")
    private IRatingPeopleView mView;

    private List<User> mUsers = new ArrayList<>();
    private EndlessScrollListener mEndlessScrollListener;
    private boolean mIsLastPage;
    private int mCurrentPage;
    private boolean mIsRefresh;
    private RatingPeopleResponse mRatingPeopleResponse;

    private View mDisableView;

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RatingPeopleAdapter adapter = new RatingPeopleAdapter(getContext(), mUsers, this);
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
        ApiRequest.getInstance().getRatingPeopleData(mCurrentPage, null, new Callback<RatingPeopleResponse>() {
            @Override
            public void onResponse(Call<RatingPeopleResponse> call, Response<RatingPeopleResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                RatingPeopleResponse peopleResponse = response.body();
                mUsers.addAll(peopleResponse.getUsers());
                mIsLastPage = peopleResponse.isLast();
                mCurrentPage = peopleResponse.getCurrentPage();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<RatingPeopleResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    @Override
    public void onItemRatingPeopleClick(int position) {
        mView.onItemPeopleClick(mUsers.get(position));
    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, View disableView) {
        mDisableView = disableView;
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mIsRefresh = true;
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.resetValue();
        }
        mDisableView.setVisibility(View.VISIBLE);
        getRatingPeopleData();
    }

    public void getRatingPeopleData() {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mIsRefresh = false;
            mView.onNoInternetConnection();
            mDisableView.setVisibility(View.GONE);
            return;
        }
        if (!mIsRefresh) {
            mView.onShowProgressDialog();
        }
        ApiRequest.getInstance().getRatingPeopleData(null, null, new Callback<RatingPeopleResponse>() {
            @Override
            public void onResponse(Call<RatingPeopleResponse> call, Response<RatingPeopleResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                mRatingPeopleResponse = response.body();
                mIsLastPage = mRatingPeopleResponse.isLast();
                mCurrentPage = mRatingPeopleResponse.getCurrentPage();
                mUsers.clear();
                mUsers.addAll(mRatingPeopleResponse.getUsers());
                mIsRefresh = false;
                mView.onLoadDataComplete();
            }

            @Override
            public void onFailure(Call<RatingPeopleResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        });
    }
}
