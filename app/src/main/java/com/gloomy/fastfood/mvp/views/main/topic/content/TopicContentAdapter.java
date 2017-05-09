package com.gloomy.fastfood.mvp.views.main.topic.content;

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
import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.utils.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 17/04/2017.
 */
public class TopicContentAdapter extends BaseAdapter<TopicContentAdapter.ItemTopicVH> {
    private final List<Topic> mTopics;
    private final OnTopicListener mOnTopicListener;
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
    private final int mImageTopicSize;
    private final int mAvatarSize;

    public TopicContentAdapter(@NonNull Context mContext, List<Topic> topics, OnTopicListener onTopicListener) {
        super(mContext);
        mTopics = topics;
        mOnTopicListener = onTopicListener;
        mImageTopicSize = ScreenUtil.getWidthScreen(getContext());
        mAvatarSize = getResources().getDimensionPixelSize(R.dimen.topic_avatar_size);
    }

    @Override
    public ItemTopicVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_topic, parent, false);
        return new ItemTopicVH(view, mOnTopicListener);
    }

    @Override
    public void onBindViewHolder(ItemTopicVH holder, int position) {
        Topic topic = mTopics.get(position);
        Picasso.with(getContext())
                .load(topic.getMainImage())
                .resize(mImageTopicSize, 0)
                .into(holder.mImgTopic);
        holder.mBtnLike.setSelected(topic.isLike());
        holder.mTvTopicTitle.setText(topic.getTitle());
        holder.mTvNumberLike.setText(getContext().getString(R.string.number_of_like, topic.getCountTopicLikes()));
        holder.mTvNumberComment.setText(getContext().getString(R.string.number_of_comment, topic.getCountTopicComments()));
        if (topic.getPostTime() != null) {
            holder.mTvPostTime.setText(mSimpleDateFormat.format(topic.getPostTime()));
        }
        User user = topic.getUser();
        if (user != null) {
            Picasso.with(getContext())
                    .load(user.getAvatar())
                    .resize(mAvatarSize, 0)
                    .into(holder.mImgAvatar);
            holder.mTvUsername.setText(user.getUsername());
        }
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    /**
     * ViewHolder for item topic
     */
    static class ItemTopicVH extends RecyclerView.ViewHolder {
        private final ImageView mImgTopic;
        private final ImageView mBtnLike;
        private final TextView mTvTopicTitle;
        private final TextView mTvNumberLike;
        private final TextView mTvNumberComment;
        private final CircleImageView mImgAvatar;
        private final TextView mTvUsername;
        private final TextView mTvPostTime;

        ItemTopicVH(View itemView, final OnTopicListener onTopicListener) {
            super(itemView);
            mImgTopic = (ImageView) itemView.findViewById(R.id.imgTopic);
            mBtnLike = (ImageView) itemView.findViewById(R.id.btnLike);
            mTvTopicTitle = (TextView) itemView.findViewById(R.id.tvTopicTitle);
            mTvNumberLike = (TextView) itemView.findViewById(R.id.tvNumberLike);
            mTvNumberComment = (TextView) itemView.findViewById(R.id.tvNumberComment);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            mTvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            mTvPostTime = (TextView) itemView.findViewById(R.id.tvPostTime);

            mBtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTopicListener.onItemLikeClick(getLayoutPosition());
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTopicListener != null) {
                        onTopicListener.onItemTopicClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnTopicListener interface
     */
    public interface OnTopicListener {
        void onItemTopicClick(int position);

        void onItemLikeClick(int position);
    }
}
