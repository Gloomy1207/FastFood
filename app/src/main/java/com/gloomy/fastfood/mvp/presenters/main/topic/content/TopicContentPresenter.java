package com.gloomy.fastfood.mvp.presenters.main.topic.content;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.TopicResponse;
import com.gloomy.fastfood.mvp.models.Topic;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.views.main.topic.content.ITopicContentView;
import com.gloomy.fastfood.mvp.views.main.topic.content.TopicContentAdapter;
import com.gloomy.fastfood.mvp.views.main.topic.content.TopicContentFragment;
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
public class TopicContentPresenter extends BasePresenter implements TopicContentAdapter.OnTopicListener, SwipeRefreshLayout.OnRefreshListener {

    @Setter
    @Accessors(prefix = "m")
    private ITopicContentView mView;

    @Setter
    @Accessors(prefix = "m")
    private int mTopicType;

    private List<Topic> mTopics = new ArrayList<>();
    private EndlessScrollListener mEndlessScrollListener;
    private int mCurrentPage;
    private boolean mIsLast;
    private TopicResponse mTopicResponse;

    private View mDisableView;

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TopicContentAdapter adapter = new TopicContentAdapter(getContext(), mTopics, this);
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
        if (mIsLast) {
            return;
        }
        mCurrentPage++;
        Callback<TopicResponse> callback = new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                TopicResponse topicResponse = response.body();
                mTopics.addAll(topicResponse.getTopics());
                mCurrentPage = topicResponse.getCurrentPage();
                mIsLast = topicResponse.isLast();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        };
        getDataFromServer(mCurrentPage, callback);
    }

    @Override
    public void onItemTopicClick(int position) {
        mView.onItemTopicClick(mTopics.get(position));
    }

    public void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, View disableView) {
        mDisableView = disableView;
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (mEndlessScrollListener != null) {
            mEndlessScrollListener.resetValue();
        }
        mDisableView.setVisibility(View.VISIBLE);
        getTopicContentData(true);
    }

    public void getTopicContentData(boolean isRefresh) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            return;
        }
        if (!isRefresh) {
            mView.onShowProgressDialog();
        }
        Callback<TopicResponse> callback = new Callback<TopicResponse>() {
            @Override
            public void onResponse(Call<TopicResponse> call, Response<TopicResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                mTopicResponse = response.body();
                mTopics.clear();
                mTopics.addAll(mTopicResponse.getTopics());
                mCurrentPage = mTopicResponse.getCurrentPage();
                mIsLast = mTopicResponse.isLast();
                if (!mTopics.isEmpty()) {
                    mView.onLoadDataComplete();
                } else {
                    mView.onDataEmpty();
                }
            }

            @Override
            public void onFailure(Call<TopicResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        };
        getDataFromServer(null, callback);
    }

    private void getDataFromServer(Integer currentPage, Callback<TopicResponse> callback) {
        switch (mTopicType) {
            case TopicContentFragment.TopicType.HOT:
                ApiRequest.getInstance().getHotTopicData(currentPage, null, callback);
                break;
            case TopicContentFragment.TopicType.TRENDING:
                ApiRequest.getInstance().getTrendingTopicData(currentPage, null, callback);
                break;
            case TopicContentFragment.TopicType.FRESH:
                ApiRequest.getInstance().getFreshTopicData(currentPage, null, callback);
                break;
            case TopicContentFragment.TopicType.RANDOM:
                ApiRequest.getInstance().getRandomTopicData(currentPage, null, callback);
                break;
        }
    }
}
