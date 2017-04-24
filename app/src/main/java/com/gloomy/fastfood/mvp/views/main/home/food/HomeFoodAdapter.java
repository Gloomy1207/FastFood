package com.gloomy.fastfood.mvp.views.main.home.food;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Food;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 11-Apr-17.
 */
public class HomeFoodAdapter extends BaseAdapter<HomeFoodAdapter.ItemHomeStoreVH> {

    private final List<Food> mFoods;
    private OnHomeFoodListener mOnHomeFoodListener;

    public HomeFoodAdapter(@NonNull Context mContext, List<Food> foods, OnHomeFoodListener onHomeFoodListener) {
        super(mContext);
        mFoods = foods;
        mOnHomeFoodListener = onHomeFoodListener;
    }

    @Override
    public ItemHomeStoreVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_home_food, parent, false);
        return new ItemHomeStoreVH(view, mOnHomeFoodListener);
    }

    @Override
    public void onBindViewHolder(ItemHomeStoreVH holder, int position) {
        Food food = mFoods.get(position);
        Picasso.with(getContext())
                .load(food.getMainImage())
                .into(holder.mImgFood);
        holder.mTvFoodName.setText(food.getFoodName());
        holder.mTvFoodDescription.setText(food.getDescription());
        holder.mTvNumberStar.setText(String.valueOf(food.getRating()));
        holder.mTvNumberRating.setText(food.getNumberOfRatingText());
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
        private final TextView mTvFoodDescription;
        private final TextView mTvNumberStar;
        private final TextView mTvNumberRating;

        ItemHomeStoreVH(View itemView, final OnHomeFoodListener onHomeFoodListener) {
            super(itemView);
            mImgFood = (ImageView) itemView.findViewById(R.id.imgFood);
            mTvFoodName = (TextView) itemView.findViewById(R.id.tvFoodName);
            mTvFoodDescription = (TextView) itemView.findViewById(R.id.tvFoodDescription);
            mTvNumberStar = (TextView) itemView.findViewById(R.id.tvNumberStar);
            mTvNumberRating = (TextView) itemView.findViewById(R.id.tvNumberRating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onHomeFoodListener != null) {
                        onHomeFoodListener.onFoodClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnHomeFoodListener interface
     */
    public interface OnHomeFoodListener {
        void onFoodClick(int position);
    }
}
