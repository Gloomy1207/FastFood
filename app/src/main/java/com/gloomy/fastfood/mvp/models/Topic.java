package com.gloomy.fastfood.mvp.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
@Getter
@Setter
@Parcel
public class Topic {
    int topicId;
    User user;
    String content;
    Timestamp postTime;
    String title;
    int countTopicLikes;
    int countTopicComments;
    Comment latestComment;
    String mainImage;
    boolean isLike;

    @ParcelConstructor
    public Topic(int topicId, User user, String content, Timestamp postTime, String title, int countTopicLikes, int countTopicComments, Comment latestComment, String mainImage, boolean isLike) {
        this.topicId = topicId;
        this.user = user;
        this.content = content;
        this.postTime = postTime;
        this.title = title;
        this.countTopicLikes = countTopicLikes;
        this.countTopicComments = countTopicComments;
        this.latestComment = latestComment;
        this.mainImage = mainImage;
        this.isLike = isLike;
    }
}
