package com.gloomy.fastfood.widgets.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gloomy.fastfood.R;
import com.squareup.picasso.Picasso;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 21-Apr-17.
 */
public class GalleryDialog extends DialogFragment {
    @Setter
    @Accessors(prefix = "m")
    private String mImagePath;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.layout_gallery, null);
        ImageView imgGallery = (ImageView) view.findViewById(R.id.imgGallery);
        RelativeLayout layoutParent = (RelativeLayout) view.findViewById(R.id.layoutParent);
        layoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        builder.setView(view);
        Dialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        Picasso.with(getActivity())
                .load(mImagePath)
                .into(imgGallery);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        return dialog;
    }
}
