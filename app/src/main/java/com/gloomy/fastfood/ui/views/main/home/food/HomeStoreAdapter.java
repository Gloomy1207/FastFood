package com.gloomy.fastfood.ui.views.main.home.food;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Food;
import com.gloomy.fastfood.ui.views.BaseAdapter;

import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 11-Apr-17.
 */
public class HomeStoreAdapter extends BaseAdapter<HomeStoreAdapter.ItemHomeStoreVH> {

    private final List<Food> mFoods;
    private OnItemFoodListener mOnItemFoodListener;

    protected HomeStoreAdapter(@NonNull Context mContext, List<Food> foods, OnItemFoodListener onItemFoodListener) {
        super(mContext);
        mFoods = foods;
        mOnItemFoodListener = onItemFoodListener;
    }

    @Override
    public ItemHomeStoreVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_home_food, parent, false);
        return new ItemHomeStoreVH(view, mOnItemFoodListener);
    }

    @Override
    public void onBindViewHolder(ItemHomeStoreVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    /**
     * ViewHolder for item HomeStore
     */
    static class ItemHomeStoreVH extends RecyclerView.ViewHolder {
        private final ImageView mImgFood;
        private final TextView mTvFoodName;
        private final RatingBar mRatingBar;
        private final TextView mTvFoodDescription;

        ItemHomeStoreVH(View itemView, final OnItemFoodListener onItemFoodListener) {
            super(itemView);
            mImgFood = (ImageView) itemView.findViewById(R.id.imgFood);
            mTvFoodName = (TextView) itemView.findViewById(R.id.tvFoodName);
            mTvFoodDescription = (TextView) itemView.findViewById(R.id.tvFoodDescription);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemFoodListener != null) {
                        onItemFoodListener.onFoodClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnItemFoodListener interface
     */
    public interface OnItemFoodListener {
        void onFoodClick(int position);
    }
}
