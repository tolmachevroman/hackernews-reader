package com.reigndesign.hackernewsreader.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class NewsPOJO {

    @SerializedName("story_title")
    String storyTitle;

    String author;

    @SerializedName("created_at")
    Date createdAt;

    public String getStoryTitle() {
        return storyTitle;
    }

    public String getAuthor() {
        return author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
