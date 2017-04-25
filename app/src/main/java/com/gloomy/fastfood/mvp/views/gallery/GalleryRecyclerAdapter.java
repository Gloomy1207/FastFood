package com.gloomy.fastfood.mvp.views.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.GalleryImage;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.widgets.DynamicHeightImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 21-Apr-17.
 */
public class GalleryRecyclerAdapter extends BaseAdapter<GalleryRecyclerAdapter.ItemGalleryVH> {

    private final List<GalleryImage> mGalleryImages;
    private final OnGalleryDialogListener mOnGalleryDialogListener;
    private final Random mRandom;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<>();

    public GalleryRecyclerAdapter(@NonNull Context mContext, List<GalleryImage> galleryImages, OnGalleryDialogListener onGalleryDialogListener) {
        super(mContext);
        mGalleryImages = galleryImages;
        mOnGalleryDialogListener = onGalleryDialogListener;
        mRandom = new Random();
    }

    @Override
    public ItemGalleryVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_gallery, parent, false);
        return new ItemGalleryVH(view, mOnGalleryDialogListener);
    }

    @Override
    public void onBindViewHolder(ItemGalleryVH holder, int position) {
        holder.mThumbImgGallery.setHeightRatio(getPositionRatio(position));
        Picasso.with(getContext())
                .load(mGalleryImages.get(position).getImagePath())
                .into(holder.mThumbImgGallery);
    }

    @Override
    public int getItemCount() {
        return mGalleryImages.size();
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0;
    }

    /**
     * ViewHolder for item Image
     */
    static class ItemGalleryVH extends RecyclerView.ViewHolder {
        private DynamicHeightImageView mThumbImgGallery;

        ItemGalleryVH(View itemView, final OnGalleryDialogListener onGalleryDialogListener) {
            super(itemView);
            mThumbImgGallery = (DynamicHeightImageView) itemView.findViewById(R.id.imgThumbGallery);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onGalleryDialogListener != null) {
                        onGalleryDialogListener.onItemGalleryClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * OnGalleryDialogListener interface
     */
    public interface OnGalleryDialogListener {
        void onItemGalleryClick(int position);
    }
}
