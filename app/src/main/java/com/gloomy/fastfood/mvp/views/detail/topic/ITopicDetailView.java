package com.gloomy.fastfood.mvp.views.detail.topic;

import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.IBaseView;
import com.gloomy.fastfood.widgets.CustomConfirmDialog;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 21/04/2017.
 */
public interface ITopicDetailView extends IBaseView {
    void onBackClick();

    void onLoadCommentComplete();

    void onLoadDataFailure();

    void onUserClick(User user);

    void onEmptyComment();

    void onSendingComment();

    void onNotLogin();

    void onFakeAddComment();

    void onUpdateUploadedComment();

    void onCommentComplete();

    void onLikeComplete();

    void onDeleteCommentSuccess();

    void onDeleteCommentError(String message);

    void onShowRequestDeleteComment(String message, String deleteButton, String cancelButton, CustomConfirmDialog.OnConfirmDialogListener onConfirmDialogListener);

    void onCancelClick();

    void onConfirmClick();
}
