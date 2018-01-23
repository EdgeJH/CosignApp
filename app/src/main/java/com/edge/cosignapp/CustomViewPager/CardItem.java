package com.edge.cosignapp.CustomViewPager;


public class CardItem {

    private int mBackImage;
    private int mTitleResource;

    public CardItem(int title, int image) {
        mTitleResource = title;
        mBackImage = image;
    }

    public int getmBackImage() {
        return mBackImage;
    }

    public int getTitle() {
        return mTitleResource;
    }
}
