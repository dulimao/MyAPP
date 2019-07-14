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
 *
 *
 * 1.外部拦截法伪代码：如果父容器需要此事件，则拦截交给当前View处理，否则不拦截，交给子View处理
 *
 * private int mLastX = 0;
 * private int mLastY = 0;
 *
 *
 * public boolean onInterceptTouchEvent(MotionEvent event) {
 *     boolean intercept = false;
 *     int x = (int) event.getX();
 *     int y = (int) event.getY();
 *     switch(event.getAction()) {
 *         case MotionEvent.ACTION_DOWN:
 *              intercept = false;
 *              break:
 *         case MotionEvent.ACTION_MOVE:
 *              if (父容器需要拦截此事件) {
 *                  intercept = true;
 *              } else {
 *                  intercept = false;
 *              }
 *              break;
 *          case MotionEvent.ACTION_UP:
 *              intercept = false;
 *              break;
 *     }
 *     mLastX = x;
 *     mLastY = y;
 *     return intercept;
 * }
 *
 *
 * 2.内部拦截发伪代码：父容器不拦截任何事件，所有事件都交给子元素，
 * 如果子元素需要此事件直接消耗，否则j交给父容器
 *
 * public int mLastX;
 * public int mLastY;
 *
 * public boolean dispatchTouchEvent(MotionEvent event) {
 *     int x = (int) event.getX();
 *     int y = (int) event.getY();
 *     switch (event.getAction() {
 *         case MotionEvent.ACTION_DOWN:
 *              parent.requestDisallowInterceptTouchEvent(true);
 *              break;
 *         case MotionEvent.ACTION_MOVE:
 *              int deltaX = x - mLastX;
 *              int deltaY = y - mLastY;
 *              if (父容器需要此事件) {
 *                  parent.requestDisallowInterceptTouchEvent(false);
 *              }
 *              break;
 *          case MotionEvent.ACTION_UP:
 *
 *               break;
 *     }
 *
 *     mLastX = x;
 *     mLastY = y;
 *     return super.dispatchTouchEvent(event);
 *
 * }
 *
 * 父容器：
 * public boolean onInterceptTouchEvent(MotionEvent event) {
 *     int action = event.getAction();
 *     if (action = MotionEvent.ACTION_DOWN) {
 *         return false;
 *     } else {
 *         return true;
 *     }
 *
 * }
 *
 *
 *
 *
 */
public class MotionEventDispatch {
}
