package com.gloomy.fastfood.mvp.views.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.models.GalleryImage;
import com.gloomy.fastfood.mvp.views.BaseAdapter;
import com.gloomy.fastfood.utils.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 21-Apr-17.
 */
public class GalleryRecyclerAdapter extends BaseAdapter<GalleryRecyclerAdapter.ItemGalleryVH> {

    private final List<GalleryImage> mGalleryImages;
    private final OnGalleryDialogListener mOnGalleryDialogListener;
    private int mImageWidth;

    public GalleryRecyclerAdapter(@NonNull Context context, List<GalleryImage> galleryImages, OnGalleryDialogListener onGalleryDialogListener) {
        super(context);
        mGalleryImages = galleryImages;
        mOnGalleryDialogListener = onGalleryDialogListener;
        mImageWidth = (int) ((ScreenUtil.getWidthScreen(context) - ScreenUtil.convertDpiToPixel(context, 20)) / 2);
    }

    @Override
    public ItemGalleryVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_recycler_gallery, parent, false);
        return new ItemGalleryVH(view, mOnGalleryDialogListener);
    }

    @Override
    public void onBindViewHolder(ItemGalleryVH holder, int position) {
        Picasso.with(getContext())
                .load(mGalleryImages.get(position).getImagePath())
                .resize(mImageWidth, 0)
                .into(holder.mThumbImgGallery);
    }

    @Override
    public int getItemCount() {
        return mGalleryImages.size();
    }

    /**
     * ViewHolder for item Image
     */
    static class ItemGalleryVH extends RecyclerView.ViewHolder {
        private ImageView mThumbImgGallery;

        ItemGalleryVH(View itemView, final OnGalleryDialogListener onGalleryDialogListener) {
            super(itemView);
            mThumbImgGallery = (ImageView) itemView.findViewById(R.id.imgThumbGallery);

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
