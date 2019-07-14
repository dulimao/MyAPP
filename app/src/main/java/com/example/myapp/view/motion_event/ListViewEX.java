package com.example.myapp.view.motion_event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * author : dulimao
 * e-mail : dulimao@yuewen.com
 * date   : 2019/7/1214:51
 * desc   : 内部拦截法
 * version: 1.0
 */
public class ListViewEX extends ListView {
    private static final String TAG = "ListViewEX";

    private int mLastX;
    private int mLastY;

    private HorizontalScrollViewEX1 mHorizontalScrollViewEX1;


    public ListViewEX(Context context) {
        super(context);
    }

    public ListViewEX(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewEX(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setmHorizontalScrollViewEX1 (HorizontalScrollViewEX1 view) {
        this.mHorizontalScrollViewEX1 = view;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHorizontalScrollViewEX1.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    mHorizontalScrollViewEX1.requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(event);
    }
}
