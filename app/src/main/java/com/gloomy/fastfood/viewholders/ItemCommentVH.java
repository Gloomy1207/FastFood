package com.gloomy.fastfood.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Comment;
import com.gloomy.fastfood.models.User;
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
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());

    public ItemCommentVH(Context context, ViewGroup parent) {
        super(getItemView(context, parent));
        mImgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
        mTvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
        mTvCommentTime = (TextView) itemView.findViewById(R.id.tvCommentTime);
        mTvCommentContent = (TextView) itemView.findViewById(R.id.tvCommentContent);
        mContext = context;
    }

    private static View getItemView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_recycler_comment, parent, false);
    }

    public void setCommentData(Comment comment) {
        User user = comment.getUser();
        if (user != null) {
            Picasso.with(mContext)
                    .load(user.getAvatar())
                    .into(mImgAvatar);
            mTvUsername.setText(user.getUsername());
        }
        mTvCommentTime.setText(mSimpleDateFormat.format(comment.getPostTime()));
        mTvCommentContent.setText(comment.getContent());
    }
}

