package com.gloomy.fastfood.models;

import java.sql.Timestamp;

import lombok.Getter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 14/04/2017.
 */
@Getter
public class Topic {
    private int topicId;
    private User user;
    private String content;
    private Timestamp postTime;
    private String title;
    private int countTopicLikes;
    private int countTopicComments;
    private Comment latestComment;
    private String mainImage;
    private boolean isLike;
}
