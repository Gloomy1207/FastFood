package com.gloomy.fastfood.widgets.dialog.rating;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.RatingType;
import com.gloomy.fastfood.mvp.views.BaseAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 26/04/2017.
 */
class RatingAdapter extends BaseAdapter {
    static final int TITLE_ITEM_OFFSET = 0;
    static final int RATING_ITEM_OFFSET = 1;
    private final List<RatingType> mRatingTypes;
    private final OnRatingListener mOnRatingListener;
    private final String mTitle;

    RatingAdapter(@NonNull Context mContext, List<RatingType> ratingTypes, OnRatingListener onRatingListener, String title) {
        super(mContext);
        mRatingTypes = ratingTypes;
        mOnRatingListener = onRatingListener;
        mTitle = title;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RatingItemType.RATING) {
            return new ItemRatingVH(getViewFromLayout(R.layout.item_recycler_rating, parent));
        } else if (viewType == RatingItemType.TITLE) {
            return new ItemTitleVH(getViewFromLayout(R.layout.item_recycler_rating_title, parent));
        } else {
            return new ItemButtonVH(getViewFromLayout(R.layout.item_recycler_rating_button, parent), mOnRatingListener);
        }
    }

    private View getViewFromLayout(int resId, ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(resId, parent, false);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TITLE_ITEM_OFFSET) {
            return RatingItemType.TITLE;
        } else if (position == mRatingTypes.size() + 1) {
            return RatingItemType.BUTTON;
        } else {
            return RatingItemType.RATING;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemRatingVH) {
            onBindRatingItem((ItemRatingVH) holder, position - RATING_ITEM_OFFSET);
        } else if (holder instanceof ItemTitleVH) {
            onBindTitleItem((ItemTitleVH) holder);
        }
    }

    private void onBindRatingItem(ItemRatingVH holder, int position) {
        final RatingType ratingType = mRatingTypes.get(position);
        if (ratingType != null) {
            holder.mTvRatingName.setText(ratingType.getRatingTypeName());
            holder.mRatingBar.setRating(5);
            ratingType.setValue(5);
            holder.mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    ratingType.setValue(v);
                }
            });
        }
    }

    private void onBindTitleItem(ItemTitleVH holder) {
        holder.mTvTitle.setText(mTitle);
    }

    @Override
    public int getItemCount() {
        return mRatingTypes.size() + RATING_ITEM_OFFSET + 1;
    }

    /**
     * RatingItemType definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RatingItemType.TITLE, RatingItemType.BUTTON, RatingItemType.RATING})
    private @interface RatingItemType {
        int TITLE = 1;
        int RATING = 2;
        int BUTTON = 3;
    }

    /**
     * OnRatingListener interface
     */
    interface OnRatingListener {
        void onSubmitClick();
    }

    /**
     * ItemRatingVH class
     */
    private static class ItemRatingVH extends RecyclerView.ViewHolder {
        private TextView mTvRatingName;
        private RatingBar mRatingBar;

        ItemRatingVH(View itemView) {
            super(itemView);
            mTvRatingName = (TextView) itemView.findViewById(R.id.tvRatingName);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }

    /**
     * ItemTitleVH class
     */
    private static class ItemTitleVH extends RecyclerView.ViewHolder {
        private TextView mTvTitle;

        ItemTitleVH(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }

    /**
     * ItemButtonVH class
     */
    private static class ItemButtonVH extends RecyclerView.ViewHolder {
        private TextView mBtnSubmit;

        ItemButtonVH(View itemView, final OnRatingListener onRatingListener) {
            super(itemView);
            mBtnSubmit = ((TextView) itemView.findViewById(R.id.btnSubmit));
            mBtnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRatingListener != null) {
                        onRatingListener.onSubmitClick();
                    }
                }
            });
        }
    }
}
