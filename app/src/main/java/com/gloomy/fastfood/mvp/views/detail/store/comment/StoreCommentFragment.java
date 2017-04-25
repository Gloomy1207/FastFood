package com.gloomy.fastfood.mvp.views.detail.store.comment;

import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.mvp.BaseFragment;
import com.gloomy.fastfood.mvp.models.Comment;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.presenters.detail.store.comment.StoreCommentPresenter;
import com.gloomy.fastfood.observer.StoreDetailObserver;
import com.gloomy.fastfood.observer.listener.OnStoreDetailSendCommentObserverListener;
import com.gloomy.fastfood.widgets.CustomConfirmDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 24/04/2017.
 */
@EFragment(R.layout.fragment_store_comment)
public class StoreCommentFragment extends BaseFragment implements IStoreCommentView, OnStoreDetailSendCommentObserverListener {

    @Bean
    StoreCommentPresenter mPresenter;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @FragmentArg
    Store mStore;

    @AfterViews
    void afterViews() {
        mPresenter.setView(this);
        mPresenter.initRecyclerView(mRecyclerView);
        mPresenter.setStore(mStore);
        mPresenter.getStoreCommentData();
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
        showNoInternetConnectionMessage();
    }

    @Override
    public void onLoadDataComplete() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadDataFailure() {
        showLoadDataFailure();
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
    public void onNotLogin() {
        showLoginDialog();
    }

    @Override
    public void onShowRequestDeleteComment(String message, String deleteButton, String cancelButton, CustomConfirmDialog.OnConfirmDialogListener onConfirmDialogListener) {
        showConfirmDialog(message, cancelButton, deleteButton, onConfirmDialogListener);
    }

    @Override
    public void onAddFakeComment() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCommentComplete() {
        mPresenter.announceCommentComplete(getActivity());
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onUpdateUploadedComment() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCancelClick() {
        dismissConfirmDialog();
    }

    @Override
    public void onConfirmClick() {
        dismissConfirmDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        StoreDetailObserver.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        StoreDetailObserver.unregister(this);
    }

    @Override
    public void onSendCommentData(Comment comment) {
        mPresenter.onSendComment(comment);
    }
}
