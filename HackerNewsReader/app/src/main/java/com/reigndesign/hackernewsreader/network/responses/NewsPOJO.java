package com.reigndesign.hackernewsreader.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class NewsPOJO {

    @SerializedName("title")
    String title;

    @SerializedName("story_title")
    String storyTitle;

    @SerializedName("url")
    String url;

    @SerializedName("story_url")
    String storyUrl;

    @SerializedName("author")
    String author;

    @SerializedName("created_at")
    Date createdAt;

    public String getTitle() {
        return title;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public String getUrl() {
        return url;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    public String getAuthor() {
        return author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
