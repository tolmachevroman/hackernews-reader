# hackernews-reader
Loads and shows the latest Android related Hacker News articles

![Demo](https://cloud.githubusercontent.com/assets/560815/13704872/d0c2cbc8-e77c-11e5-9134-dfc64bdb481e.gif)

**The core architecture is based on:**

[Retrofit](http://square.github.io/retrofit/) for the network layer

[ContentProviders](http://developer.android.com/guide/topics/providers/content-providers.html) + SQLite for the database layer

Latest [Design library](http://developer.android.com/tools/support-library/index.html) (RecyclerView, CardView, CoordinatorLayout for animations) for the UI

**Some comments:**

1) [Hacker News API](http://hn.algolia.com/api/v1/search_by_date?query=android) gives unstable json ( **title** or **story_title**, **url** or **story_url** may or may not contain data), so app filters whatever field brings the valid information. The only reliable **id** I found was **objectID** field, which is used as unique key in the database. 

2) Some News don't contain **url** at all, for such cases app shows a Toast.
