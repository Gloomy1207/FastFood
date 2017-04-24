package com.gloomy.fastfood.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gloomy.fastfood.R;
import com.squareup.picasso.Picasso;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
public class ItemImageVH extends RecyclerView.ViewHolder {
    private final ImageView mImgPhoto;
    private final Context mContext;

    public ItemImageVH(Context context, ViewGroup parent) {
        super(getItemView(context, parent));
        mContext = context;
        mImgPhoto = (ImageView) itemView.findViewById(R.id.imgPhoto);
    }

    private static View getItemView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_recycler_photo, parent, false);
    }

    public void setPhotoData(String imagePath) {
        Picasso.with(mContext)
                .load(imagePath)
                .into(mImgPhoto);
    }
}
