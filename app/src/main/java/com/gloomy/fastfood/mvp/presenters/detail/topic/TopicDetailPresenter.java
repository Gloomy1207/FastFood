package com.gloomy.fastfood.mvp.presenters.detail.topic;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.gloomy.fastfood.R;
import com.gloomy.fastfood.api.ApiRequest;
import com.gloomy.fastfood.api.responses.CommentResponse;
import com.gloomy.fastfood.api.responses.DeleteCommentResponse;
import com.gloomy.fastfood.api.responses.LikeResponse;
import com.gloomy.fastfood.api.responses.TopicCommentResponse;
import com.gloomy.fastfood.auth.AuthSession;
import com.gloomy.fastfood.mvp.models.Comment;
import com.gloomy.fastfood.mvp.models.Topic;
import com.gloomy.fastfood.mvp.models.User;
import com.gloomy.fastfood.mvp.presenters.BasePresenter;
import com.gloomy.fastfood.mvp.presenters.EndlessScrollListener;
import com.gloomy.fastfood.mvp.presenters.gallery.GalleryPresenter;
import com.gloomy.fastfood.mvp.views.detail.topic.ITopicDetailView;
import com.gloomy.fastfood.mvp.views.detail.topic.TopicDetailAdapter;
import com.gloomy.fastfood.mvp.views.gallery.GalleryActivity_;
import com.gloomy.fastfood.utils.NetworkUtil;
import com.gloomy.fastfood.viewholders.ItemCommentVH;
import com.gloomy.fastfood.widgets.CustomConfirmDialog;
import com.gloomy.fastfood.widgets.CustomFloatingButton;
import com.gloomy.fastfood.widgets.CustomTextInputLayout;
import com.gloomy.fastfood.widgets.HeaderBar;

import org.androidannotations.annotations.EBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 21/04/2017.
 */
@EBean
public class TopicDetailPresenter extends BasePresenter implements TopicDetailAdapter.OnTopicDetailListener, ItemCommentVH.OnCommentListener, CustomTextInputLayout.OnTextInputLayoutListener {

    @Setter
    @Accessors(prefix = "m")
    ITopicDetailView mView;

    @Setter
    @Accessors(prefix = "m")
    Topic mTopic;

    private List<Comment> mComments = new ArrayList<>();
    private int mCurrentPage;
    private boolean mIsLast;
    private boolean mIsSendingComment;

    public void initRecyclerView(RecyclerView recyclerView) {
        TopicDetailAdapter adapter = new TopicDetailAdapter(getContext(), mComments, mTopic, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessScrollListener(LOAD_MORE_THRESHOLD) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    private void loadMoreData() {
        mCurrentPage++;
        getTopicComment(true);
    }

    public void initHeaderBar(HeaderBar headerBar) {
        headerBar.setTitle(mTopic.getTitle());
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

    @Override
    public void onUserClick(User user) {
        mView.onUserClick(user);
    }

    @Override
    public void onViewImageClick() {
        GalleryActivity_.intent(getContext())
                .mGalleryType(GalleryPresenter.GalleryType.TOPIC_TYPE)
                .mGalleryId(mTopic.getTopicId())
                .mGalleryName(mTopic.getTitle())
                .start();
    }

    public void getTopicComment(boolean isLoadMore) {
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        if (mComments.size() == mTopic.getCountTopicComments() || mIsLast) {
            return;
        }
        if (!isLoadMore) {
            mView.onShowProgressDialog();
        }
        ApiRequest.getInstance().getTopicComment(mCurrentPage, null, mTopic.getTopicId(), new Callback<TopicCommentResponse>() {
            @Override
            public void onResponse(Call<TopicCommentResponse> call, Response<TopicCommentResponse> response) {
                mView.onDismissProgressDialog();
                if (response == null || response.body() == null) {
                    return;
                }
                TopicCommentResponse commentResponse = response.body();
                mComments.addAll(commentResponse.getComments());
                mCurrentPage = commentResponse.getCurrentPage();
                mIsLast = commentResponse.isLast();
                mView.onLoadCommentComplete();
            }

            @Override
            public void onFailure(Call<TopicCommentResponse> call, Throwable t) {
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
        ApiRequest.getInstance().deleteComment(commentId, new Callback<DeleteCommentResponse>() {
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

    public void onLikeClick(final CustomFloatingButton btnLike) {
        if (!AuthSession.isLogIn()) {
            mView.onNotLogin();
            return;
        }
        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            mView.onNoInternetConnection();
            return;
        }
        btnLike.setSelected(!btnLike.isSelected());
        ApiRequest.getInstance().likeTopic(mTopic.getTopicId(), new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if (response == null || response.body() == null) {
                    btnLike.setSelected(!btnLike.isSelected());
                    return;
                }
                if (!response.body().isStatus()) {
                    btnLike.setSelected(!btnLike.isSelected());
                } else {
                    mTopic.setCountTopicLikes(btnLike.isSelected() ? mTopic.getCountTopicLikes() + 1 : mTopic.getCountTopicLikes() - 1);
                    mView.onLikeComplete();
                }
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                btnLike.setSelected(!btnLike.isSelected());
                // No-op
            }
        });
    }

    public void initButtonLike(CustomFloatingButton btnLike) {
        if (AuthSession.isLogIn()) {
            btnLike.setVisibility(View.VISIBLE);
        } else {
            btnLike.setVisibility(View.GONE);
        }
        btnLike.setSelected(mTopic.isLike());
    }

    public void initCommentLayout(CustomTextInputLayout commentLayout) {
        commentLayout.setHint(getString(R.string.comment_topic));
        commentLayout.setOnTextInputLayoutListener(this);
    }

    @Override
    public void onSendClick(String message) {
        if (!AuthSession.isLogIn()) {
            mView.onNotLogin();
            return;
        }
        if (TextUtils.isEmpty(message)) {
            mView.onEmptyComment();
            return;
        }
        if (mIsSendingComment) {
            mView.onSendingComment();
            return;
        }
        mIsSendingComment = true;
        mComments.add(0, Comment.builder()
                .content(message)
                .postTime(new Timestamp(System.currentTimeMillis()))
                .user(AuthSession.getInstance().getAuthLogin().getUser())
                .status(Comment.CommentStatus.LOADING)
                .build());
        mView.onFakeAddComment();
        ApiRequest.getInstance().commentTopic(mTopic.getTopicId(), message, new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                mIsSendingComment = false;
                if (response == null || response.body() == null) {
                    updateFirstCommentStatus(Comment.CommentStatus.ERROR);
                    return;
                }
                if (response.body().isStatus()) {
                    updateFirstCommentStatus(Comment.CommentStatus.SUCCESS);
                    mTopic.setCountTopicComments(mTopic.getCountTopicComments() + 1);
                    mView.onCommentComplete();
                } else {
                    updateFirstCommentStatus(Comment.CommentStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                mIsSendingComment = false;
                updateFirstCommentStatus(Comment.CommentStatus.ERROR);
                mView.onCommentComplete();
            }
        });
    }

    private void updateFirstCommentStatus(int status) {
        mComments.get(0).setStatus(status);
        mView.onUpdateUploadedComment();
    }
}
