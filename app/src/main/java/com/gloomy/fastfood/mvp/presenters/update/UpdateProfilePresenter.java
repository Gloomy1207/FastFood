package com.gloomy.fastfood.mvp.presenters.update;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.requests.EditProfileRequest;
import com.gloomy.fastfood.api.responses.EditProfileResponse;
import com.gloomy.fastfood.auth.Auth;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.views.update.IUpdateProfileView;
import com.gloomy.fastfood.mvp.views.update.UpdateProfileActivity;
import com.gloomy.fastfood.utils.FileUtil;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.utils.PermissionUtil;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.DimensionPixelOffsetRes;

import java.io.File;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Copyright © 2017 Gloomy
 * Created by HungTQB on 28-Apr-17.
 */
@EBean
public class UpdateProfilePresenter extends BasePresenter {
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 124;
    private static final int PICK_IMAGE_REQUEST_CODE = 212;
    private Uri mAvatarUri;

    @Setter
    @Accessors(prefix = "m")
    IUpdateProfileView mView;

    @DimensionPixelOffsetRes(R.dimen.avatar_size_profile)
    int mAvatarSize;

    public void initValueForViews() {
        Auth auth = AuthSession.getInstance().getAuthLogin();
        if (auth != null) {
            User user = auth.getUser();
            mView.onSetFullName(user.getFullname());
            mView.onSetDescription(user.getDescription());
            mView.onSetAvatar(user.getAvatar(), mAvatarSize);
            mView.onSetEmail(user.getEmail());
        }
    }

    public void onChangeAvatarClick(UpdateProfileActivity activity) {
        if (!PermissionUtil.isPermissionGranted(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            PermissionUtil.requestPermissions(activity, READ_EXTERNAL_STORAGE_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            return;
        }
        pickImage(activity);
    }

    public void onRequestPermissionResult(int requestCode, int[] grantResults, UpdateProfileActivity activity) {
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage(activity);
            }
        }
    }

    private void pickImage(UpdateProfileActivity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mAvatarUri = data.getData();
                mView.onSetAvatar(mAvatarUri, mAvatarSize);
            }
        }
    }

    public void onUpdateProfileClick(String fullName, String description, String email, String password) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        File file = null;
        String realPath;
        if (mAvatarUri != null) {
            realPath = FileUtil.getRealPathFromURI(getContext(), mAvatarUri);
            if (realPath != null) {
                file = new File(realPath);
            }
        }
        User user = AuthSession.getInstance().getAuthLogin().getUser();
        EditProfileRequest request = EditProfileRequest.builder()
                .description(description)
                .fullName(fullName)
                .email(email)
                .username(user.getUsername())
                .password(password)
                .build();
        mView.onShowProgressDialog();
        ApiRequest.getInstance().editProfile(file, request, new Callback<EditProfileResponse>() {
            @Override
            public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                EditProfileResponse profileResponse = response.body();
                if (profileResponse.isStatus()) {
                    mView.onUpdateComplete(profileResponse.getMessage());
                } else {
                    mView.onUpdateFailure(profileResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<EditProfileResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onUpdateFailure(getString(R.string.load_data_error));
            }
        });
    }

    public void initHeaderBar(HeaderBar headerBar) {
        User user = AuthSession.getInstance().getAuthLogin().getUser();
        headerBar.setTitle(user.getUsername());
        headerBar.setOnHeaderBarListener(new HeaderBar.OnHeaderBarListener() {
            @Override
            public void onLeftButtonClick() {
                mView.onBackClick();
            }

            @Override
            public void onRightButtonClick() {
                // No-op
            }
        });
    }
}
