package com.gloomy.fastfood.ui.views.detail.topic;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.models.Topic;
import com.gloomy.fastfood.models.User;
import com.gloomy.fastfood.ui.BaseActivity;
import com.gloomy.fastfood.ui.presenters.detail.topic.TopicDetailPresenter;
import com.gloomy.fastfood.widgets.CustomConfirmDialog;
import com.gloomy.fastfood.widgets.CustomFloatingButton;
import com.gloomy.fastfood.widgets.CustomTextInputLayout;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 21/04/2017.
 */
@EActivity(R.layout.activity_detail_topic)
public class TopicDetailActivity extends BaseActivity implements ITopicDetailView {

    @Bean
    TopicDetailPresenter mPresenter;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;

    @ViewById(R.id.textInputLayout)
    CustomTextInputLayout mCommentLayout;

    @ViewById(R.id.btnLike)
    CustomFloatingButton mBtnLike;

    @ViewById(R.id.layoutParent)
    RelativeLayout mLayoutParent;

    @Extra
    Parcelable mTopicParcel;

    @AfterViews
    void afterViews() {
        mLayoutParent.requestFocus();
        Topic topic = Parcels.unwrap(mTopicParcel);
        mPresenter.setView(this);
        mPresenter.setTopic(topic);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.initHeaderBar(mHeaderBar);
        mPresenter.getTopicComment(false);
        mPresenter.initButtonLike(mBtnLike);
        mPresenter.initCommentLayout(mCommentLayout);
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

    @Override
    public void onBackClick() {
        finish();
        overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    }

    @Override
    public void onLoadCommentComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadDataFailure() {
        showLoadDataFailure();
    }

    @Override
    public void onUserClick(User user) {
        // TODO: 23-Apr-17 Handle when click use
    }

    @Override
    public void onEmptyComment() {
        showMessageDialog(getString(R.string.comment_is_required), getString(R.string.button_close));
    }

    @Override
    public void onSendingComment() {
        showMessageDialog(getString(R.string.sending_comment), getString(R.string.button_close));
    }

    @Override
    public void onNotLogin() {
        showLoginDialog();
    }

    @Override
    public void onFakeAddComment() {
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onUpdateUploadedComment() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCommentComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLikeComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDeleteCommentSuccess() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDeleteCommentError(String message) {
        showMessageDialog(message, getString(R.string.button_close));
    }

    @Override
    public void onShowRequestDeleteComment(String message, String deleteButton, String cancelButton, CustomConfirmDialog.OnConfirmDialogListener onConfirmDialogListener) {
        showConfirmDialog(message, cancelButton, deleteButton, onConfirmDialogListener);
    }

    @Override
    public void onCancelClick() {
        dismissConfirmDialog();
    }

    @Override
    public void onConfirmClick() {
        dismissConfirmDialog();
    }

    @Click(R.id.btnLike)
    void onLikeClick() {
        mPresenter.onLikeClick(mBtnLike);
    }
}
