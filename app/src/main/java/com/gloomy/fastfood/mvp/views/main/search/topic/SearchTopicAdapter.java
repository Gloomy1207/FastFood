package com.gloomy.fastfood.mvp.views.main.search.topic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Topic;
import com.gloomy.fastfood.models.Comment;
import com.gloomy.fastfood.models.User;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
public class SearchTopicAdapter extends BaseAdapter<SearchTopicAdapter.ItemSearchTopicVH> {
    private final List<Topic> mTopics;
    private final OnSearchTopicListener mOnSearchTopicListener;
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());

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
        Topic topic = mTopics.get(position);
        Picasso.with(getContext())
                .load(topic.getMainImage())
                .into(holder.mImgTopic);
        holder.mTvTopicTitle.setText(topic.getTitle());
        User postUser = topic.getUser();
        if (postUser != null) {
            Picasso.with(getContext())
                    .load(postUser.getAvatar())
                    .into(holder.mImgPostAvatar);
            holder.mTvPostUsername.setText(postUser.getUsername());
        }
        holder.mTvNumberLike.setText(getContext().getString(R.string.number_of_like, topic.getCountTopicLikes()));
        holder.mBtnLike.setSelected(topic.isLike());
        holder.mTvPostTime.setText(mSimpleDateFormat.format(topic.getPostTime()));
        if (topic.getLatestComment() != null) {
            Comment comment = topic.getLatestComment();
            if (comment.getUser() != null) {
                Picasso.with(getContext())
                        .load(comment.getUser().getAvatar())
                        .into(holder.mImgCommentAvatar);
            }
            holder.mTvCommentTime.setText(mSimpleDateFormat.format(comment.getPostTime()));
            holder.mTvCommentContent.setText(comment.getContent());
            if (comment.getUser() != null) {
                holder.mTvCommentName.setText(comment.getUser().getUsername());
            }
            holder.mLayoutComment.setVisibility(View.VISIBLE);
            holder.mCommentDivider.setVisibility(View.VISIBLE);
        } else {
            holder.mLayoutComment.setVisibility(View.GONE);
            holder.mCommentDivider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    /**
     * ItemSearchTopicVH class
     */
    static class ItemSearchTopicVH extends RecyclerView.ViewHolder {
        private final ImageView mImgTopic;
        private final TextView mTvTopicTitle;
        private final ImageView mImgPostAvatar;
        private final TextView mTvNumberLike;
        private final ImageView mBtnLike;
        private final TextView mTvPostUsername;
        private final TextView mTvPostTime;
        private final ImageView mImgCommentAvatar;
        private final TextView mTvCommentName;
        private final TextView mTvCommentTime;
        private final TextView mTvCommentContent;
        private final View mCommentDivider;
        private final LinearLayout mLayoutComment;

        ItemSearchTopicVH(View itemView, final OnSearchTopicListener onSearchTopicListener) {
            super(itemView);
            mImgTopic = (ImageView) itemView.findViewById(R.id.imgTopic);
            mTvTopicTitle = (TextView) itemView.findViewById(R.id.tvTopicTitle);
            mImgPostAvatar = (ImageView) itemView.findViewById(R.id.imgPostAvatar);
            mTvNumberLike = (TextView) itemView.findViewById(R.id.tvNumberLike);
            mBtnLike = (ImageView) itemView.findViewById(R.id.btnLike);
            mTvPostUsername = (TextView) itemView.findViewById(R.id.tvPostUsername);
            mTvPostTime = (TextView) itemView.findViewById(R.id.tvPostTime);
            mImgCommentAvatar = (ImageView) itemView.findViewById(R.id.imgCommentAvatar);
            mTvCommentName = (TextView) itemView.findViewById(R.id.tvCommentName);
            mTvCommentTime = (TextView) itemView.findViewById(R.id.tvCommentTime);
            mTvCommentContent = (TextView) itemView.findViewById(R.id.tvCommentContent);
            mCommentDivider = itemView.findViewById(R.id.commentDivider);
            mLayoutComment = (LinearLayout) itemView.findViewById(R.id.layoutComment);

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
