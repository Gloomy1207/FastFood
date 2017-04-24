package com.gloomy.fastfood.mvp.views.detail.topic;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Comment;
import com.gloomy.fastfood.models.Topic;
import com.gloomy.fastfood.models.User;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.viewholders.ItemCommentVH;
import com.gloomy.fastfood.viewholders.ItemImageVH;
import com.gloomy.fastfood.viewholders.ItemNumberPhotoVH;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 21/04/2017.
 */
public class TopicDetailAdapter extends BaseAdapter {

    /**
     * TopicItemType definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TopicItemType.TOPIC_IMAGE, TopicItemType.USER_INFORMATION, TopicItemType.NUMBER_LIKE_COMMENT, TopicItemType.COMMENT})
    @interface TopicItemType {
        int TOPIC_IMAGE = 1;
        int USER_INFORMATION = 2;
        int NUMBER_LIKE_COMMENT = 3;
        int COMMENT = 4;
    }

    private static final int IMAGE_POSITION = 0;
    private static final int USER_POSITION = 1;
    private static final int LIKE_COMMENT_POSITION = 2;
    private static final int COMMENT_OFFSET = 3;

    private final Topic mTopic;
    private final List<Comment> mComments;
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
    private final OnTopicDetailListener mOnTopicDetailListener;
    private final ItemCommentVH.OnCommentListener mOnCommentListener;

    public TopicDetailAdapter(@NonNull Context mContext, List<Comment> comments, Topic topic, OnTopicDetailListener onTopicDetailListener, ItemCommentVH.OnCommentListener onCommentListener) {
        super(mContext);
        mTopic = topic;
        mComments = comments;
        mOnTopicDetailListener = onTopicDetailListener;
        mOnCommentListener = onCommentListener;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case IMAGE_POSITION:
                return TopicItemType.TOPIC_IMAGE;
            case USER_POSITION:
                return TopicItemType.USER_INFORMATION;
            case LIKE_COMMENT_POSITION:
                return TopicItemType.NUMBER_LIKE_COMMENT;
            default:
                return TopicItemType.COMMENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TopicItemType.TOPIC_IMAGE:
                return new ItemImageVH(getContext(), parent);
            case TopicItemType.USER_INFORMATION:
                return new ItemUserInformationVH(getViewFromResId(R.layout.item_recycler_topic_user, parent), mOnTopicDetailListener);
            case TopicItemType.NUMBER_LIKE_COMMENT:
                return new ItemNumberPhotoVH(getContext(), parent);
            case TopicItemType.COMMENT:
                return new ItemCommentVH(getContext(), parent, mOnCommentListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemImageVH) {
            onBindItemTopicImage((ItemImageVH) holder);
        } else if (holder instanceof ItemUserInformationVH) {
            onBindItemUserInformation(((ItemUserInformationVH) holder));
        } else if (holder instanceof ItemNumberPhotoVH) {
            onBindItemNumberLike(((ItemNumberPhotoVH) holder));
        } else {
            onBindItemComment((ItemCommentVH) holder, position);
        }
    }

    private void onBindItemTopicImage(ItemImageVH holder) {
        holder.setPhotoData(mTopic.getMainImage());
    }

    private void onBindItemNumberLike(ItemNumberPhotoVH holder) {
        holder.setNumberPhotoData(mTopic);
    }

    private void onBindItemUserInformation(ItemUserInformationVH holder) {
        final User user = mTopic.getUser();
        if (user != null) {
            Picasso.with(getContext())
                    .load(user.getAvatar())
                    .into(holder.mImgAvatar);
            holder.mTvUsername.setText(user.getUsername());
            holder.mTvPostTime.setText(mSimpleDateFormat.format(mTopic.getPostTime()));
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnTopicDetailListener != null) {
                        mOnTopicDetailListener.onUserClick(user);
                    }
                }
            };
            holder.mTvUsername.setOnClickListener(onClickListener);
            holder.mImgAvatar.setOnClickListener(onClickListener);
        }
    }

    private void onBindItemComment(ItemCommentVH holder, int position) {
        holder.setCommentData(mComments.get(position - COMMENT_OFFSET), position - COMMENT_OFFSET);
    }

    @Override
    public int getItemCount() {
        return mComments.size() + COMMENT_OFFSET;
    }

    private View getViewFromResId(int resId, ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(resId, parent, false);
    }

    /**
     * ItemUserInformationVH class
     */
    private static class ItemUserInformationVH extends RecyclerView.ViewHolder {
        private final CircleImageView mImgAvatar;
        private final TextView mTvUsername;
        private final TextView mTvPostTime;
        private final FloatingActionButton mBtnViewImage;

        ItemUserInformationVH(View itemView, final OnTopicDetailListener listener) {
            super(itemView);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            mTvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            mTvPostTime = (TextView) itemView.findViewById(R.id.tvPostTime);
            mBtnViewImage = (FloatingActionButton) itemView.findViewById(R.id.btnViewImage);
            mBtnViewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onViewImageClick();
                    }
                }
            });
        }
    }

    /**
     * OnTopicDetailListener interface
     */
    public interface OnTopicDetailListener {
        void onUserClick(User user);

        void onViewImageClick();
    }
}
