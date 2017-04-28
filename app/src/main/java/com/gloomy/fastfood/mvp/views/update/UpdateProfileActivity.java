package com.gloomy.fastfood.mvp.views.update;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseActivity;
import com.gloomy.fastfood.mvp.presenters.update.UpdateProfilePresenter;
import com.gloomy.fastfood.widgets.HeaderBar;
import com.gloomy.fastfood.widgets.dialog.CustomMessageDialog;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
@EActivity(R.layout.activity_update_profile)
public class UpdateProfileActivity extends BaseActivity implements IUpdateProfileView {

    @Bean
    UpdateProfilePresenter mPresenter;

    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;

    @ViewById(R.id.imgAvatar)
    CircleImageView mImgAvatar;

    @ViewById(R.id.edtFullName)
    EditText mEdtFullName;

    @ViewById(R.id.edtEmail)
    EditText mEdtEmail;

    @ViewById(R.id.edtDescription)
    EditText mEdtDescription;

    @ViewById(R.id.edtPassword)
    EditText mEdtPassword;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initValueForViews();
        mPresenter.initHeaderBar(mHeaderBar);
    }

    @Override
    public void onShowProgressDialog() {
        showProgressDialog();
    }

    @Override
    public void onDismissProgressDialog() {
        dismissProgressDialog();
    }

    @Override
    public void onNoInternetConnection() {
        showNoInternetConnection();
    }

    @Click(R.id.tvChangeAvatar)
    void onChangeAvatarClick() {
        mPresenter.onChangeAvatarClick(this);
    }

    @Click(R.id.btnUpdateProfile)
    void onUpdateProfileClick() {
        mPresenter.onUpdateProfileClick(mEdtFullName.getText().toString(), mEdtDescription.getText().toString(), mEdtEmail.getText().toString(), mEdtPassword.getText().toString());
    }

    @Override
    public void onSetFullName(String fullName) {
        mEdtFullName.setText(fullName);
    }

    @Override
    public void onSetDescription(String description) {
        mEdtDescription.setText(description);
    }

    @Override
    public void onSetAvatar(String avatar, int avatarSize) {
        Picasso.with(this)
                .load(avatar)
                .resize(avatarSize, 0)
                .into(mImgAvatar);
    }

    @Override
    public void onSetAvatar(Uri avatar, int avatarSize) {
        Picasso.with(this)
                .load(avatar)
                .resize(avatarSize, 0)
                .into(mImgAvatar);
    }

    @Override
    public void onSetEmail(String email) {
        mEdtEmail.setText(email);
    }

    @Override
    public void onUpdateFailure(String message) {
        showMessageDialog(message, getString(R.string.button_close));
    }

    @Override
    public void onUpdateComplete(String message) {
        showMessageDialog(message, getString(R.string.button_close), new CustomMessageDialog.OnCustomMessageDialogListener() {
            @Override
            public void onCloseClick() {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void onBackClick() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.onRequestPermissionResult(requestCode, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }
}
