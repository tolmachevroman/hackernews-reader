package com.reigndesign.hackernewsreader.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class NewsListPOJO {

    @SerializedName("hits")
    List<NewsPOJO> newsPOJOList;

    public List<NewsPOJO> getNewsPOJOList() {
        return newsPOJOList;
    }
}
