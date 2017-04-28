package com.gloomy.fastfood.mvp.presenters.main.search.result.topic;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.api.responses.SearchResultResponse;
import com.gloomy.fastfood.mvp.models.Topic;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.main.search.result.topic.ISearchResultTopicView;
import com.gloomy.fastfood.mvp.views.main.search.topic.SearchTopicAdapter;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
@EBean
public class SearchResultTopicPresenter extends BasePresenter implements SearchTopicAdapter.OnSearchTopicListener {

    @Setter
    @Accessors(prefix = "m")
    private ISearchResultTopicView mView;

    private List<Topic> mTopics = new ArrayList<>();

    public void onReceiveSearchResult(SearchResultResponse response) {
        mTopics.clear();
        if (response.getTopics() != null && !response.getTopics().isEmpty()) {
            mTopics.addAll(response.getTopics());
            mView.onUpdateData();
        } else {
            mView.onEmptyData();
        }
    }

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SearchTopicAdapter(getContext(), mTopics, this));
    }

    @Override
    public void onItemTopicClick(int position) {
        mView.onTopicClick(mTopics.get(position));
    }
}
