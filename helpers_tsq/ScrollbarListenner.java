package com.app.helpers;

import android.util.Log;
import android.widget.AbsListView;

import com.ingic.garagediscount.interfaces.scrollDownListenner;

/**
 * Created by muhamamdtouseeq on 4/7/2017.
 */

public class ScrollbarListenner implements AbsListView.OnScrollListener {
    private int mLastFirstVisibleItem;
    public Boolean isScrollDown = false;
    public com.ingic.garagediscount.interfaces.scrollDownListenner scrollDownListenner;

    public ScrollbarListenner(com.ingic.garagediscount.interfaces.scrollDownListenner scrollDownListenner) {
        this.scrollDownListenner = scrollDownListenner;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    public void setScrollDownListenner(com.ingic.garagediscount.interfaces.scrollDownListenner scrollDownListenner) {
        this.scrollDownListenner = scrollDownListenner;
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mLastFirstVisibleItem < firstVisibleItem) {
            Log.e("Tag", "scroll down");
            isScrollDown = true;
            scrollDownListenner.onChange();
        }
        if (mLastFirstVisibleItem > firstVisibleItem) {
            // Log.i("SCROLLING UP","TRUE");
        }
        mLastFirstVisibleItem = firstVisibleItem;
    }


}
