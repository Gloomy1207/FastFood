package com.gloomy.fastfood.mvp.views.detail.store.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ViewGroup;

import com.gloomy.fastfood.mvp.models.Comment;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.viewholders.ItemCommentVH;

import java.util.List;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 25/04/2017.
 */
public class StoreCommentAdapter extends BaseAdapter<ItemCommentVH> {

    private final List<Comment> mComments;
    private final ItemCommentVH.OnCommentListener mOnCommentListener;

    public StoreCommentAdapter(@NonNull Context mContext, List<Comment> comments, ItemCommentVH.OnCommentListener onCommentListener) {
        super(mContext);
        mComments = comments;
        mOnCommentListener = onCommentListener;
    }

    @Override
    public ItemCommentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemCommentVH(getContext(), parent, mOnCommentListener);
    }

    @Override
    public void onBindViewHolder(ItemCommentVH holder, int position) {
        holder.setCommentData(mComments.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }
}
