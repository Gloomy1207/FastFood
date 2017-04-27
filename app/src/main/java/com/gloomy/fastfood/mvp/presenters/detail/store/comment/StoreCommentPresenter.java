package com.gloomy.fastfood.mvp.presenters.detail.store.comment;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.CommentResponse;
import com.gloomy.fastfood.api.responses.DeleteCommentResponse;
import com.gloomy.fastfood.api.responses.PostCommentResponse;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.mvp.models.Comment;
import com.gloomy.fastfood.mvp.models.Store;
import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.views.detail.store.StoreDetailActivity;
import com.gloomy.fastfood.mvp.views.detail.store.comment.IStoreCommentView;
import com.gloomy.fastfood.mvp.views.detail.store.comment.StoreCommentAdapter;
import com.gloomy.fastfood.mvp.views.profile.ProfileActivity_;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.viewholders.ItemCommentVH;
import com.gloomy.fastfood.widgets.CustomConfirmDialog;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 25/04/2017.
 */
@EBean
public class StoreCommentPresenter extends BasePresenter implements ItemCommentVH.OnCommentListener {

    @Setter
    @Accessors(prefix = "m")
    private IStoreCommentView mView;

    @Setter
    @Accessors(prefix = "m")
    private Store mStore;

    private List<Comment> mComments = new ArrayList<>();
    private int mCurrentPage;
    private boolean mIsLastPage;

    public void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StoreCommentAdapter adapter = new StoreCommentAdapter(getContext(), mComments, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    private void loadMoreData() {
        if (mIsLastPage) {
            return;
        }
        mCurrentPage++;
        ApiRequest.getInstance().getStoreComment(mStore.getStoreId(), mCurrentPage, null, new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                CommentResponse commentResponse = response.body();
                mComments.addAll(commentResponse.getComments());
                mIsLastPage = commentResponse.isLast();
                mCurrentPage = commentResponse.getCurrentPage();
                mView.onLoadDataComplete();
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                // No-op
            }
        });
    }

    public void getStoreCommentData() {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        mView.onShowProgressDialog();
        ApiRequest.getInstance().getStoreComment(mStore.getStoreId(), null, null, new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                CommentResponse commentResponse = response.body();
                mComments.clear();
                mComments.addAll(commentResponse.getComments());
                mIsLastPage = commentResponse.isLast();
                mCurrentPage = commentResponse.getCurrentPage();
                mView.onLoadDataComplete();
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                mView.onDismissProgressDialog();
                mView.onLoadDataFailure();
            }
        });
    }

    @Override
    public void onDeleteClick(final int commentId, final int position) {
        if (!AuthSession.isLogIn()) {
            mView.onNotLogin();
            return;
        }
        mView.onShowRequestDeleteComment(getString(R.string.confirm_delete_comment), getString(R.string.delete_button), getString(R.string.button_cancel), new CustomConfirmDialog.OnConfirmDialogListener() {
            @Override
            public void onLeftClick() {
                mView.onCancelClick();
            }

            @Override
            public void onRightClick() {
                mView.onConfirmClick();
                deleteComment(commentId, position);
            }
        });
    }

    private void deleteComment(int commentId, final int position) {
        ApiRequest.getInstance().deleteComment(commentId, ApiRequest.DeleteCommentType.STORE, new Callback<DeleteCommentResponse>() {
            @Override
            public void onResponse(Call<DeleteCommentResponse> call, Response<DeleteCommentResponse> response) {
                if (response == null || response.body() == null) {
                    return;
                }
                if (response.body().isStatus()) {
                    mComments.remove(position);
                    mView.onDeleteCommentSuccess();
                } else {
                    mView.onDeleteCommentError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteCommentResponse> call, Throwable t) {
                mView.onLoadDataFailure();
            }
        });
    }

    @Override
    public void onUserClick(User user) {
        ProfileActivity_.intent(getContext()).mUser(user).start();
    }

    public void onSendComment(Comment comment) {
        mComments.add(0, comment);
        mView.onAddFakeComment();
        ApiRequest.getInstance().commentStore(mStore.getStoreId(), comment.getContent(), new Callback<PostCommentResponse>() {
            @Override
            public void onResponse(Call<PostCommentResponse> call, Response<PostCommentResponse> response) {
                if (response == null || response.body() == null) {
                    updateFirstCommentStatus(Comment.CommentStatus.ERROR);
                    return;
                }
                if (response.body().isStatus()) {
                    updateFirstCommentStatus(Comment.CommentStatus.SUCCESS);
                    mView.onCommentComplete();
                } else {
                    updateFirstCommentStatus(Comment.CommentStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<PostCommentResponse> call, Throwable t) {
                updateFirstCommentStatus(Comment.CommentStatus.ERROR);
                mView.onCommentComplete();
            }
        });
    }

    private void updateFirstCommentStatus(int status) {
        mComments.get(0).setStatus(status);
        mView.onUpdateUploadedComment();
    }

    public void announceCommentComplete(Activity activity) {
        if (activity instanceof StoreDetailActivity) {
            ((StoreDetailActivity) activity).onSendCommentComplete();
        }
    }
}
