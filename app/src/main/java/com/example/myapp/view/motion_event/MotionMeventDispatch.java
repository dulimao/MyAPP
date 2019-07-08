package com.example.myapp.view.motion_event;

/**
 * 事件分发的伪代码：
 * public boolean dispatchTouchEvent(MotionEvent event) {
 *     boolean consume = false;
 *     if(onInterceptTouchEvent(event)){
 *         consume = onTouchEvent(event);
 *     } else {
 *         consume = child.dispatchTouchEvent(event);
 *     }
 *     return consume;
 *
 * }
 */
public class MotionMeventDispatch {
}
