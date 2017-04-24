package com.gloomy.fastfood.mvp.presenters.main.profile.feed;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.ProfileResponse;
import com.gloomy.fastfood.mvp.models.Topic;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.views.main.profile.feeds.IProfileFeedView;
import com.gloomy.fastfood.mvp.views.main.profile.feeds.ProfileFeedAdapter;

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
 * Created by HungTQB on 18-Apr-17.
 */
@EBean
public class ProfileFeedPresenter extends BasePresenter implements ProfileFeedAdapter.OnItemProfileFeedListener {

    @Setter
    @Accessors(prefix = "m")
    private IProfileFeedView mView;

    private List<Topic> mTopics = new ArrayList<>();
    private boolean mIsLast;
    private int mCurrentPage;

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProfileFeedAdapter adapter = new ProfileFeedAdapter(getContext(), mTopics, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    private void loadMoreData() {
        if (mIsLast) {
            return;
        }
        mCurrentPage++;
        ApiRequest.getInstance().loadMoreUserFeed(mCurrentPage, null, new Callback<ProfileResponse.ProfileTopics>() {
            @Override
            public void onResponse(Call<ProfileResponse.ProfileTopics> call, Response<ProfileResponse.ProfileTopics> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                ProfileResponse.ProfileTopics profileTopics = response.body();
                mTopics.addAll(profileTopics.getTopics());
                mIsLast = profileTopics.isLast();
                mCurrentPage = profileTopics.getCurrentPage();
                mView.onLoadMoreComplete();
            }

            @Override
            public void onFailure(Call<ProfileResponse.ProfileTopics> call, Throwable t) {
                //  No-op
            }
        });
    }

    @Override
    public void onItemTopicClick(int position) {
        mView.onItemTopicClick(mTopics.get(position));
    }

    public void onReceiveProfileData(ProfileResponse profileResponse) {
        ProfileResponse.ProfileTopics profileTopics = profileResponse.getTopics();
        mTopics.addAll(profileTopics.getTopics());
        mCurrentPage = profileTopics.getCurrentPage();
        mIsLast = profileTopics.isLast();
        mView.onLoadDataComplete();
    }
}
