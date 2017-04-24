package com.gloomy.fastfood.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.Comment;
import com.gloomy.fastfood.mvp.models.User;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 21/04/2017.
 */
public class ItemCommentVH extends RecyclerView.ViewHolder {

    private final CircleImageView mImgAvatar;
    private final TextView mTvUsername;
    private final TextView mTvCommentTime;
    private final TextView mTvCommentContent;
    private final Context mContext;
    private final TextView mTvDelete;
    private final LinearLayout mLayoutControl;
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
    private final OnCommentListener mOnCommentListener;
    private final TextView mTvStatus;

    public ItemCommentVH(Context context, ViewGroup parent, final OnCommentListener onCommentListener) {
        super(getItemView(context, parent));
        mImgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
        mTvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
        mTvCommentTime = (TextView) itemView.findViewById(R.id.tvCommentTime);
        mTvCommentContent = (TextView) itemView.findViewById(R.id.tvCommentContent);
        mTvDelete = (TextView) itemView.findViewById(R.id.tvDelete);
        mTvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        mLayoutControl = (LinearLayout) itemView.findViewById(R.id.layoutControl);
        mOnCommentListener = onCommentListener;
        mContext = context;
    }

    private static View getItemView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_recycler_comment, parent, false);
    }

    public void setCommentData(final Comment comment, final int position) {
        User user = comment.getUser();
        if (user != null) {
            Picasso.with(mContext)
                    .load(user.getAvatar())
                    .into(mImgAvatar);
            mTvUsername.setText(user.getUsername());
        }
        mTvCommentTime.setText(mSimpleDateFormat.format(comment.getPostTime()));
        if (comment.getStatus() == Comment.CommentStatus.SUCCESS) {
            mLayoutControl.setVisibility(View.VISIBLE);
            mTvStatus.setVisibility(View.GONE);
            mTvCommentContent.setText(comment.getContent());
            if (comment.isAllowDelete()) {
                mTvDelete.setVisibility(View.VISIBLE);
            } else {
                mTvDelete.setVisibility(View.GONE);
            }
            mTvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnCommentListener != null) {
                        mOnCommentListener.onDeleteClick(comment.getCommentId(), position);
                    }
                }
            });
        } else if (comment.getStatus() == Comment.CommentStatus.LOADING) {
            mLayoutControl.setVisibility(View.GONE);
            mTvStatus.setVisibility(View.VISIBLE);
            mTvStatus.setText(R.string.sending_comment);
        } else if (comment.getStatus() == Comment.CommentStatus.ERROR) {
            mLayoutControl.setVisibility(View.GONE);
            mTvStatus.setVisibility(View.VISIBLE);
            mTvStatus.setText(R.string.comment_error);
        }
    }

    /**
     * OnCommentListener
     */
    public interface OnCommentListener {
        void onDeleteClick(int commentId, int position);
    }
}
