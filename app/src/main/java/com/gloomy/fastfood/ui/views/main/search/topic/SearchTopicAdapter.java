package com.gloomy.fastfood.ui.views.main.search.topic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Topic;
import com.gloomy.fastfood.ui.views.BaseAdapter;

import java.util.List;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
public class SearchTopicAdapter extends BaseAdapter<SearchTopicAdapter.ItemSearchTopicVH> {
    private final List<Topic> mTopics;
    private final OnSearchTopicListener mOnSearchTopicListener;

    public SearchTopicAdapter(@NonNull Context mContext, List<Topic> topics, OnSearchTopicListener onSearchTopicListener) {
        super(mContext);
        mOnSearchTopicListener = onSearchTopicListener;
        mTopics = topics;
    }

    @Override
    public ItemSearchTopicVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_search_topic, parent, false);
        return new ItemSearchTopicVH(view, mOnSearchTopicListener);
    }

    @Override
    public void onBindViewHolder(ItemSearchTopicVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    /**
     * ItemSearchTopicVH class
     */
    static class ItemSearchTopicVH extends RecyclerView.ViewHolder {

        public ItemSearchTopicVH(View itemView, final OnSearchTopicListener onSearchTopicListener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onSearchTopicListener != null) {
                        onSearchTopicListener.onItemTopicClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnSearchTopicListener interface
     */
    public interface OnSearchTopicListener {
        void onItemTopicClick(int position);
    }
}
