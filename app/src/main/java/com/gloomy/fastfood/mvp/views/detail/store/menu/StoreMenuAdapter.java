package com.gloomy.fastfood.mvp.views.detail.store.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.Food;
import com.gloomy.fastfood.mvp.models.StoreFood;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.utils.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 25-Apr-17.
 */
public class StoreMenuAdapter extends BaseAdapter<StoreMenuAdapter.ItemStoreMenuVH> {
    private final List<StoreFood> mStoreFoods;
    private final OnStoreMenuListener mOnStoreMenuListener;
    private final int mImageWidth;
    private final NumberFormat mMoneyFormat;

    public StoreMenuAdapter(@NonNull Context context, List<StoreFood> storeFoods, OnStoreMenuListener onStoreMenuListener) {
        super(context);
        mStoreFoods = storeFoods;
        mOnStoreMenuListener = onStoreMenuListener;
        mImageWidth = (int) ((ScreenUtil.getWidthScreen(context) - ScreenUtil.convertDpiToPixel(context, 20)) / 2);
        Locale locale = new Locale("vi", "VN");
        Currency currency = Currency.getInstance(locale);
        mMoneyFormat = NumberFormat.getCurrencyInstance();
        mMoneyFormat.setCurrency(currency);
    }

    @Override
    public ItemStoreMenuVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_store_menu, parent, false);
        return new ItemStoreMenuVH(view, mOnStoreMenuListener);
    }

    @Override
    public void onBindViewHolder(ItemStoreMenuVH holder, int position) {
        StoreFood storeFood = mStoreFoods.get(position);
        Food food = storeFood.getFood();
        if (food != null) {
            Picasso.with(getContext())
                    .load(food.getMainImage())
                    .resize(mImageWidth, 0)
                    .into(holder.mImgFood);
            holder.mTvFoodName.setText(TextUtils.isEmpty(storeFood.getLocalName()) ? food.getFoodName() : storeFood.getLocalName());
            holder.mTvFoodDescription.setText(food.getDescription());
            holder.mTvNumberStar.setText(String.valueOf(food.getRating()));
            holder.mTvNumberRating.setText(food.getNumberOfRatingText());
            holder.mTvFoodPrice.setText(mMoneyFormat.format(storeFood.getPrice()));
        }
    }

    @Override
    public int getItemCount() {
        return mStoreFoods.size();
    }

    /**
     * OnStoreMenuListener interface
     */
    public interface OnStoreMenuListener {
        void onFoodClick(int position);
    }

    /**
     * ItemStoreMenuVH class
     */
    static class ItemStoreMenuVH extends RecyclerView.ViewHolder {
        private final ImageView mImgFood;
        private final TextView mTvFoodName;
        private final TextView mTvFoodDescription;
        private final TextView mTvNumberStar;
        private final TextView mTvNumberRating;
        private final TextView mTvFoodPrice;

        ItemStoreMenuVH(View itemView, final OnStoreMenuListener onStoreMenuListener) {
            super(itemView);

            mImgFood = (ImageView) itemView.findViewById(R.id.imgFood);
            mTvFoodName = (TextView) itemView.findViewById(R.id.tvFoodName);
            mTvFoodDescription = (TextView) itemView.findViewById(R.id.tvFoodDescription);
            mTvNumberStar = (TextView) itemView.findViewById(R.id.tvNumberStar);
            mTvNumberRating = (TextView) itemView.findViewById(R.id.tvNumberRating);
            mTvFoodPrice = (TextView) itemView.findViewById(R.id.tvFoodPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onStoreMenuListener != null) {
                        onStoreMenuListener.onFoodClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
