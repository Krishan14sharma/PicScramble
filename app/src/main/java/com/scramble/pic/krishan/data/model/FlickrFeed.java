
package com.scramble.pic.krishan.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlickrFeed {

    @SerializedName ("items")
    private List<FeedItem> items;

    public List<FeedItem> getItems() {
        return items;
    }

    public static class FeedItem {

        @SerializedName ("media")
        private Media media;

        public String getImageUrl() {
            return media.getImageUrl();
        }

        private static class Media {

            @SerializedName ("m")
            private String imageUrl;

            String getImageUrl() {
                return imageUrl;
            }
        }
    }

}
