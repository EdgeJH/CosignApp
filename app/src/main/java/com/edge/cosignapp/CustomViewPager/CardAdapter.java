package com.edge.cosignapp.CustomViewPager;


public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 3;

    float getBaseElevation();

    CustomCardView getCardViewAt(int position);

    int getCount();
}
