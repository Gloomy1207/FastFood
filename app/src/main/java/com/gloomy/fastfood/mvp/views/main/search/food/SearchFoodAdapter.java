package com.gloomy.fastfood.mvp.views.main.search.food;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.Food;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
public class SearchFoodAdapter extends BaseAdapter<SearchFoodAdapter.ItemSearchFoodVH> {
    private List<Food> mFoods;
    private OnSearchFoodListener mOnSearchFoodListener;

    public SearchFoodAdapter(@NonNull Context mContext, List<Food> foods, OnSearchFoodListener onSearchFoodListener) {
        super(mContext);
        mFoods = foods;
        mOnSearchFoodListener = onSearchFoodListener;
    }

    @Override
    public ItemSearchFoodVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_search_food, parent, false);
        return new ItemSearchFoodVH(view, mOnSearchFoodListener);
    }

    @Override
    public void onBindViewHolder(ItemSearchFoodVH holder, int position) {
        Food food = mFoods.get(position);
        Picasso.with(getContext())
                .load(food.getMainImage())
                .into(holder.mImgFood);
        holder.mTvFoodDescription.setText(food.getDescription());
        holder.mTvFoodName.setText(food.getFoodName());
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    /**
     * ViewHolder for item SearchFood
     */
    static class ItemSearchFoodVH extends RecyclerView.ViewHolder {
        private TextView mTvFoodName;
        private TextView mTvFoodDescription;
        private ImageView mImgFood;

        ItemSearchFoodVH(View itemView, final OnSearchFoodListener onSearchFoodListener) {
            super(itemView);
            mTvFoodName = (TextView) itemView.findViewById(R.id.tvFoodName);
            mTvFoodDescription = (TextView) itemView.findViewById(R.id.tvFoodDescription);
            mImgFood = (ImageView) itemView.findViewById(R.id.imgFood);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onSearchFoodListener != null) {
                        onSearchFoodListener.onItemSearchFoodClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnSearchFoodListener interface
     */
    public interface OnSearchFoodListener {
        void onItemSearchFoodClick(int position);
    }
}
