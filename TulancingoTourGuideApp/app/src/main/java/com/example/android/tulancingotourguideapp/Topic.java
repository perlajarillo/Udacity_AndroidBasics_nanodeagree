package com.example.android.tulancingotourguideapp;

/**
 * Created by PerlaIvet on 19/11/2017.
 */

public class Topic {
    /*Description for the topic*/
    private String mShortDescription;
    /*Description for the topic*/
    private String mDescription;
    /*Title for the topic*/
    private String mTitle;
    /*Image resource for the topic */
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    /**
     * Constant value that represents no image was provided for this word
     */
    private static final int NO_IMAGE_PROVIDED = -1;

    public Topic(String titleTopic, String shortDescriptionTopic) {
        mShortDescription = shortDescriptionTopic;
        mTitle = titleTopic;
    }

    public Topic(String titleTopic, String shortDescriptionTopic, String descriptionTopic, int imageResourceId) {
        mDescription = descriptionTopic;
        mShortDescription = shortDescriptionTopic;
        mTitle = titleTopic;
        mImageResourceId = imageResourceId;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                '}';

    }
}
