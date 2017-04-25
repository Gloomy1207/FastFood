package com.gloomy.fastfood.mvp.models;

import android.support.annotation.IntDef;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
@Getter
@Builder
@Parcel
@Setter
@ToString
public class Comment {
    /**
     * CommentStatus definition
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef
    public @interface CommentStatus {
        int LOADING = 1;
        int ERROR = 2;
        int SUCCESS = 0;
    }

    int commentId;
    User user;
    String content;
    Timestamp postTime;
    boolean isAllowDelete;
    int status;

    @ParcelConstructor
    public Comment(int commentId, User user, String content, Timestamp postTime, boolean isAllowDelete, int status) {
        this.commentId = commentId;
        this.user = user;
        this.content = content;
        this.postTime = postTime;
        this.isAllowDelete = isAllowDelete;
        this.status = status;
    }
}
