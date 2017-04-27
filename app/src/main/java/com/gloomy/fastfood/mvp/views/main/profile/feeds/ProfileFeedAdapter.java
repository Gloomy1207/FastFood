package com.gloomy.fastfood.mvp.views.main.profile.feeds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.Topic;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.utils.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 18-Apr-17.
 */
public class ProfileFeedAdapter extends BaseAdapter<ProfileFeedAdapter.ItemProfileFeedVH> {
    private final List<Topic> mTopics;
    private final OnItemProfileFeedListener mOnItemProfileFeedListener;
    private final int mImageWidth;

    public ProfileFeedAdapter(@NonNull Context mContext, List<Topic> topics, OnItemProfileFeedListener onItemProfileFeedListener) {
        super(mContext);
        mTopics = topics;
        mOnItemProfileFeedListener = onItemProfileFeedListener;
        mImageWidth = (int) (ScreenUtil.getWidthScreen(getContext()) - ScreenUtil.convertDpiToPixel(getContext(), 20));
    }

    @Override
    public ItemProfileFeedVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_profile_feed, parent, false);
        return new ItemProfileFeedVH(view, mOnItemProfileFeedListener);
    }

    @Override
    public void onBindViewHolder(ItemProfileFeedVH holder, int position) {
        Topic topic = mTopics.get(position);
        Picasso.with(getContext())
                .load(topic.getMainImage())
                .resize(mImageWidth, 0)
                .into(holder.mImgTopic);
        holder.mTvTopicTitle.setText(topic.getTitle());
        holder.mTvNumberLike.setText(getContext().getString(R.string.number_of_like, topic.getCountTopicLikes()));
        holder.mTvNumberComment.setText(getContext().getString(R.string.number_of_comment, topic.getCountTopicComments()));
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    /**
     * ViewHolder for item ProfileFeed
     */
    static class ItemProfileFeedVH extends RecyclerView.ViewHolder {
        private final ImageView mImgTopic;
        private final TextView mTvTopicTitle;
        private final TextView mTvNumberLike;
        private final TextView mTvNumberComment;

        ItemProfileFeedVH(View itemView, final OnItemProfileFeedListener onItemProfileFeedListener) {
            super(itemView);
            mImgTopic = (ImageView) itemView.findViewById(R.id.imgTopic);
            mTvTopicTitle = (TextView) itemView.findViewById(R.id.tvTopicTitle);
            mTvNumberLike = (TextView) itemView.findViewById(R.id.tvNumberLike);
            mTvNumberComment = (TextView) itemView.findViewById(R.id.tvNumberComment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemProfileFeedListener != null) {
                        onItemProfileFeedListener.onItemTopicClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnItemProfileFeedListener interface
     */
    public interface OnItemProfileFeedListener {
        void onItemTopicClick(int position);
    }
}
