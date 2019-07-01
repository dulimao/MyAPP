package com.example.myapp.view.scroller;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myapp.R;

public class ScrollerActivity extends AppCompatActivity {
    private static final String TAG = "ScrollerActivity";

    private Button mScrollToBtn;
    private Button mScrollByBtn;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        mLayout = findViewById(R.id.layout);
        mScrollToBtn = findViewById(R.id.btn_scroll_to);
        mScrollByBtn = findViewById(R.id.btn_scroll_by);

        mScrollToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注意：改变的是View内部的内容（在这里也就是两个Button）的位置，而不能改变View的位置（mLayout）
                mLayout.scrollTo(-60,-100);
                Log.d(TAG,"mScrollX: " + mLayout.getScrollX() + " mScrollY: " + mLayout.getScrollY());
            }
        });

        mScrollByBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollByBtn.scrollBy(-60,-100);
                Log.d(TAG,"mScrollX: " + mLayout.getScrollX() + " mScrollY: " + mLayout.getScrollY());
            }
        });
    }

    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int FRAME_COUNT = 60;
    private static final int DELAY_TIME = 16;
    private int mCount = 0;

    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SCROLL_TO:
                    mCount++;
                    if (mCount <= FRAME_COUNT) {
                        float fraction = mCount / (float) FRAME_COUNT;
                        Log.d(TAG,"fraction: " + fraction);
                        int scrollX = (int) (fraction * 100);
                        mLayout.scrollTo(-scrollX,0);
                        mHandle.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO,DELAY_TIME);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    //需求：1000ms内将View的内容向右移动100像素
    public void testDelay(View view) {
        mHandle.sendEmptyMessage(MESSAGE_SCROLL_TO);
    }
}
