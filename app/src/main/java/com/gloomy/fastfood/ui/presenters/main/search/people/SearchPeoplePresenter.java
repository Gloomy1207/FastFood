package com.gloomy.fastfood.ui.presenters.main.search.people;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.SearchPeopleResponse;
import com.gloomy.fastfood.models.User;
import com.gloomy.fastfood.ui.presenters.BasePresenter;
import com.gloomy.fastfood.ui.presenters.EndlessScrollListener;
import com.gloomy.fastfood.ui.views.main.search.people.ISearchPeopleView;
import com.gloomy.fastfood.ui.views.main.search.people.SearchPeopleAdapter;
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
public class SearchPeoplePresenter extends BasePresenter implements SearchPeopleAdapter.OnSearchPeopleListener, Callback<SearchPeopleResponse>, SwipeRefreshLayout.OnRefreshListener {
    private static final int LOAD_MORE_THRESHOLD = 15;

    @Setter
    @Accessors(prefix = "m")
    private ISearchPeopleView mView;

    private List<User> mUsers = new ArrayList<>();
    private EndlessScrollListener mEndlessScrollListener;
    private SearchPeopleResponse mSearchPeopleResponse;
    private int mCurrentPage;
    private boolean mIsLastPage;
    private boolean mIsRefresh;
    private View mDisableView;

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SearchPeopleAdapter adapter = new SearchPeopleAdapter(getContext(), mUsers, this);
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
        ApiRequest.getInstance().getSearchPeopleData(String.valueOf(mCurrentPage), null, new Callback<SearchPeopleResponse>() {
            @Override
            public void onResponse(Call<SearchPeopleResponse> call, Response<SearchPeopleResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                SearchPeopleResponse peopleResponse = response.body();
                mUsers.addAll(peopleResponse.getUsers());
                mCurrentPage = peopleResponse.getCurrentPage();
                mIsLastPage = peopleResponse.isLast();
            }

            @Override
            public void onFailure(Call<SearchPeopleResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, View disableView) {
        swipeRefreshLayout.setOnRefreshListener(this);
        mDisableView = disableView;
    }

    @Override
    public void onUserClick(int position) {
        mView.onItemPeopleClick(mUsers.get(position));
    }

    public void getSearchPeopleData() {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            mDisableView.setVisibility(View.GONE);
            return;
        }
        if (!mIsRefresh) {
            mView.onShowProgressDialog();
        }
        if (mSearchPeopleResponse == null || mIsRefresh) {
            ApiRequest.getInstance().getSearchPeopleData(null, null, this);
        } else {
            initValueForRecyclerView();
        }
    }

    @Override
    public void onResponse(Call<SearchPeopleResponse> call, Response<SearchPeopleResponse> response) {
        mView.onDismissProgressDialog();
        if (response == null || response.body() == null) {
            return;
        }
        mSearchPeopleResponse = response.body();
        initValueForRecyclerView();
    }

    @Override
    public void onFailure(Call<SearchPeopleResponse> call, Throwable t) {
        mView.onDismissProgressDialog();
        mView.onLoadDataFailure();
    }

    @Override
    public void onRefresh() {
        mDisableView.setVisibility(View.VISIBLE);
        mIsRefresh = true;
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.resetValue();
        }
        getSearchPeopleData();
    }

    private void initValueForRecyclerView() {
        mUsers.clear();
        mUsers.addAll(mSearchPeopleResponse.getUsers());
        mCurrentPage = mSearchPeopleResponse.getCurrentPage();
        mIsLastPage = mSearchPeopleResponse.isLast();
        if (mIsRefresh) {
            mIsRefresh = false;
            mView.onRefreshComplete();
        } else {
            mView.onLoadDataComplete();
        }
    }
}
