package com.gloomy.fastfood.mvp.presenters.main.search.topic;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.SearchTopicResponse;
import com.gloomy.fastfood.mvp.models.Topic;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.views.main.search.topic.ISearchTopicView;
import com.gloomy.fastfood.mvp.views.main.search.topic.SearchTopicAdapter;
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
public class SearchTopicPresenter extends BasePresenter implements SearchTopicAdapter.OnSearchTopicListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int LOAD_MORE_THRESHOLD = 15;

    @Setter
    @Accessors(prefix = "m")
    private ISearchTopicView mView;

    private List<Topic> mTopics = new ArrayList<>();
    private EndlessScrollListener mEndlessScrollListener;
    private boolean mIsLast;
    private int mCurrentPage;
    private boolean mIsRefresh;
    private SearchTopicResponse mSearchTopicResponse;
    private View mDisableView;

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SearchTopicAdapter adapter = new SearchTopicAdapter(getContext(), mTopics, this);
        mEndlessScrollListener = new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(mEndlessScrollListener);
    }

    private void loadMoreData() {
        if (mIsLast) {
            return;
        }
        mCurrentPage++;
        ApiRequest.getInstance().getSearchTopicData(mCurrentPage, null, new Callback<SearchTopicResponse>() {
            @Override
            public void onResponse(Call<SearchTopicResponse> call, Response<SearchTopicResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                SearchTopicResponse topicResponse = response.body();
                mTopics.addAll(topicResponse.getTopics());
                mCurrentPage = topicResponse.getCurrentPage();
                mIsLast = topicResponse.isLast();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<SearchTopicResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    @Override
    public void onItemTopicClick(int position) {
        mView.onItemTopicClick(mTopics.get(position));
    }

    public void getSearchTopicData(SearchTopicResponse response) {
        if (response == null) {
            if (!NetworkUtil.isNetworkAvailable(getContext())) {
                mIsRefresh = false;
                mView.onNoInternetConnection();
                return;
            }
            if (!mIsRefresh) {
                mView.onShowProgressDialog();
            }
            ApiRequest.getInstance().getSearchTopicData(null, null, new Callback<SearchTopicResponse>() {
                @Override
                public void onResponse(Call<SearchTopicResponse> call, Response<SearchTopicResponse> response) {
                    mView.onDismissProgressDialog();
                    if (response == null || response.body() == null) {
                        return;
                    }
                    mSearchTopicResponse = response.body();
                    initRecyclerViewData();
                }

                @Override
                public void onFailure(Call<SearchTopicResponse> call, Throwable t) {
                    mView.onLoadDataFailure();
                }
            });
        } else {
            mSearchTopicResponse = response;
            initRecyclerViewData();
        }
    }

    private void initRecyclerViewData() {
        mTopics.clear();
        mTopics.addAll(mSearchTopicResponse.getTopics());
        mIsLast = mSearchTopicResponse.isLast();
        mCurrentPage = mSearchTopicResponse.getCurrentPage();
        if (mIsRefresh) {
            mView.onRefreshComplete();
        } else {
            mView.onLoadDataComplete(mSearchTopicResponse);
        }
    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, View disableView) {
        swipeRefreshLayout.setOnRefreshListener(this);
        mDisableView = disableView;
    }

    @Override
    public void onRefresh() {
        mIsRefresh = true;
        mDisableView.setVisibility(View.VISIBLE);
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.resetValue();
        }
        getSearchTopicData(null);
    }
}
