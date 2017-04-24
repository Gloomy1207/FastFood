package com.gloomy.fastfood.mvp.views.detail.store;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Comment;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.viewholders.ItemCommentVH;
import com.gloomy.fastfood.viewholders.ItemImageVH;
import com.gloomy.fastfood.viewholders.ItemNumberPhotoVH;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
public class StoreDetailAdapter extends BaseAdapter {
    /**
     * StoreItemType definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({StoreItemType.STORE_IMAGE, StoreItemType.STORE_INFORMATION, StoreItemType.NUMBER_LIKE_COMMENT, StoreItemType.COMMENT})
    @interface StoreItemType {
        int STORE_IMAGE = 1;
        int STORE_INFORMATION = 2;
        int NUMBER_LIKE_COMMENT = 3;
        int COMMENT = 4;
    }

    private static final int IMAGE_POSITION = 0;
    private static final int STORE_POSITION = 1;
    private static final int LIKE_COMMENT_POSITION = 2;
    private static final int COMMENT_OFFSET = 3;

    private final com.gloomy.fastfood.models.Store mStore;
    private final List<Comment> mComments;
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy", Locale.getDefault());
    private final ItemCommentVH.OnCommentListener mOnCommentListener;

    public StoreDetailAdapter(@NonNull Context mContext, com.gloomy.fastfood.models.Store store, List<Comment> comments, ItemCommentVH.OnCommentListener onCommentListener) {
        super(mContext);
        mStore = store;
        mComments = comments;
        mOnCommentListener = onCommentListener;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case IMAGE_POSITION:
                return StoreItemType.STORE_IMAGE;
            case STORE_POSITION:
                return StoreItemType.STORE_INFORMATION;
            case LIKE_COMMENT_POSITION:
                return StoreItemType.NUMBER_LIKE_COMMENT;
            default:
                return StoreItemType.COMMENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case StoreItemType.STORE_IMAGE:
                return new ItemImageVH(getContext(), parent);
            case StoreItemType.STORE_INFORMATION:
                return new ItemStoreInformationVH(getViewFromResId(R.layout.item_recycler_topic_user, parent));
            case StoreItemType.NUMBER_LIKE_COMMENT:
                return new ItemNumberPhotoVH(getContext(), parent);
            case StoreItemType.COMMENT:
                return new ItemCommentVH(getContext(), parent, mOnCommentListener);
        }
        return null;
    }

    private View getViewFromResId(int resId, ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(resId, parent, false);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mComments.size() + COMMENT_OFFSET;
    }

    /**
     * ItemStoreInformation ViewHolder
     */
    private static class ItemStoreInformationVH extends RecyclerView.ViewHolder {

        ItemStoreInformationVH(View itemView) {
            super(itemView);
        }
    }
}
