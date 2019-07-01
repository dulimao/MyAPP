package com.example.myapp.view.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Switch;

/**
 * Scroller使用案例：模拟ViewPager
 */
public class ScrollerLayout extends ViewGroup {
    private static final String TAG = "ScrollerLayout";
    private Scroller mScroller;
    //最小滑动距离
    private int mTouchSlop;

    //界面可滑动的左边界
    private int mLeftBorder;
    //界面可滑动的有边界
    private int mRightBorder;
    private float mXDown;
    private float mXMove;
    private float mXLastMove;

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledTouchSlop();//density * 8
        Log.d(TAG,"ScrollerLayout() mTouchSlop : " + mTouchSlop);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            //测量每一个子View的大小
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                //布局每一个子View在水平方向
                child.layout(i * child.getMeasuredWidth(),
                        0,
                        (i + 1) * child.getMeasuredWidth(),
                        child.getMeasuredHeight());
            }

            //初始化左右边界值
            mLeftBorder = getChildAt(0).getLeft();
            mRightBorder = getChildAt(childCount - 1).getRight();
            Log.d(TAG,"mLeftBorder : " + mLeftBorder + " mRightBorder: " + mRightBorder);

        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
//                Log.d(TAG,"onInterceptTouchEvent()-ACTION_DOWN- mXDown : " + mXDown + " mXLastMove: " + mXLastMove);
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
//                Log.d(TAG,"onInterceptTouchEvent()-ACTION_MOVE- mXMove : " + mXMove + " mXLastMove: " + mXLastMove);
//                Log.d(TAG,"onInterceptTouchEvent() diff : " + diff);

                //当手指拖动距离大于mTouchSlop时，认为应该进行滑动操作，拦截这一些列事件，
                // 不再往子View传递，交给当前View的onTouchEvent（）处理
                if (diff > mTouchSlop) {
                    //一旦拦截这个事件序列的其他事件就不再进入拦截方法了
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
//                Log.d(TAG,"onTouchEvent()-ACTION_MOVE- mXMove : " + mXMove + " mXLastMove: " + mXLastMove);
                int scrollX = (int) (mXLastMove - mXMove);//正值向左，负值向右
                Log.d(TAG,"onTouchEvent()-ACTION_MOVE- scrollX : " + scrollX + " getScrollX(): " + getScrollX());
                Log.d(TAG,"getWidth() : " + getWidth() + " height: " + getHeight());
                if (getScrollX() + scrollX < mLeftBorder) {//向右滑出边界
                    scrollTo(mLeftBorder,0);
                } else if (getScrollX() + getWidth() + scrollX > mRightBorder) {//向左滑出边界
                    scrollTo(mRightBorder - getWidth(),0);
                }
                //相对当前位置滑动
                scrollBy(scrollX,0);
                mXLastMove = mXMove;
                break;

            case MotionEvent.ACTION_UP:
                //当手指抬起时，根据滑动值判断应该滚动到哪个子View的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                mScroller.startScroll(getScrollX(),0,dx,0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
       if (mScroller.computeScrollOffset()) {
           scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
           invalidate();
       }

    }
}
