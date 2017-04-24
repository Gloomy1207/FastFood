package com.gloomy.fastfood.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Topic;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
public class ItemNumberPhotoVH extends RecyclerView.ViewHolder {

    private final TextView mTvNumberLike;
    private final TextView mTvNumberComment;

    public ItemNumberPhotoVH(Context context, ViewGroup parent) {
        super(getItemView(context, parent));
        mTvNumberLike = (TextView) itemView.findViewById(R.id.tvNumberLike);
        mTvNumberComment = (TextView) itemView.findViewById(R.id.tvNumberComment);
    }

    private static View getItemView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_recycler_topic_number, parent, false);
    }

    public void setNumberPhotoData(Topic topic) {
        mTvNumberLike.setText(String.valueOf(topic.getCountTopicLikes()));
        mTvNumberComment.setText(String.valueOf(topic.getCountTopicComments()));
    }
}
