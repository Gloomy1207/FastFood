package com.gloomy.fastfood.widgets.dialog;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;

import com.gloomy.fastfood.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 21-Apr-17.
 */
@EFragment(R.layout.layout_gallery)
public class GalleryDialog extends DialogFragment {
    @ViewById(R.id.imgGallery)
    ImageView mImgGallery;

    @FragmentArg
    String mImagePath;

    @AfterViews
    void afterViews() {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        Picasso.with(getActivity())
                .load(mImagePath)
                .into(mImgGallery);
    }
}
