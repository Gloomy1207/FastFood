package com.gloomy.fastfood.mvp.views.detail.store.comment;

import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.widgets.CustomConfirmDialog;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 25/04/2017.
 */
public interface IStoreCommentView extends IBaseView {
    void onLoadDataComplete();

    void onLoadDataFailure();

    void onDeleteCommentSuccess();

    void onDeleteCommentError(String message);

    void onNotLogin();

    void onCancelClick();

    void onConfirmClick();

    void onShowRequestDeleteComment(String message, String deleteButton, String cancelButton, CustomConfirmDialog.OnConfirmDialogListener onConfirmDialogListener);

    void onAddFakeComment();

    void onCommentComplete();

    void onUpdateUploadedComment();
}
