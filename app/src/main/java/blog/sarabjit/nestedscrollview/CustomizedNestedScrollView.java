package blog.sarabjit.nestedscrollview;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Customized NestedScrollView to detect when the scrolling is stopped.
 */
public class CustomizedNestedScrollView extends NestedScrollView {

    private static final int DELAY = 200;
    private NestedScrollListener mNestedScrollListener;
    private boolean mIsScrolling;
    private boolean mIsTouching;
    private Runnable mScrollingRunnable;
    private int mOldY;
    private int mNewY;
    private boolean mPerformActionLater;

    public CustomizedNestedScrollView(Context context) {
        super(context);
    }

    public CustomizedNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomizedNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_MOVE) {
            mIsScrolling = true;
            mIsTouching = true;
        }
        if (action == MotionEvent.ACTION_UP) {
            mIsTouching = false;
            performActionAfterTouch();
        }
        performClick();
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    /**
     * Set Listener to send callback to the parent container
     *
     * @param nestedScrollListener
     */
    public void setNestedScrollListener(NestedScrollListener nestedScrollListener) {
        this.mNestedScrollListener = nestedScrollListener;
    }

    @Override
    protected void onScrollChanged(int newX, int newY, int oldX, int oldY) {
        super.onScrollChanged(newX, newY, oldX, oldY);
        mOldY = oldY;
        mNewY = newY;
        checkCoordinatesAndPerformAction();
    }

    /**
     * Determines & fire event if user has lifted the finger and scroll has stopped
     */
    private void checkCoordinatesAndPerformAction() {
        if (Math.abs(mOldY - mNewY) > 0) {
            if (mScrollingRunnable != null) {
                removeCallbacks(mScrollingRunnable);
            }
            mScrollingRunnable = new Runnable() {
                public void run() {
                    if (mIsTouching) {
                        mPerformActionLater = true;
                    }
                    if (mIsScrolling && !mIsTouching && mNestedScrollListener != null) {
                        mNestedScrollListener.onNestedScrollStopped();
                    }
                    mIsScrolling = false;
                    mScrollingRunnable = null;
                }
            };
            postDelayed(mScrollingRunnable, DELAY);
        }
    }

    /**
     * Fire Event when Scroll has stopped but user did not lifted finger from screen.
     */
    private void performActionAfterTouch() {
        if (mScrollingRunnable == null && Math.abs(mOldY - mNewY) > 0) {
            mScrollingRunnable = new Runnable() {
                public void run() {
                    if (mPerformActionLater && mNestedScrollListener != null) {
                        mNestedScrollListener.onNestedScrollStopped();
                    }
                    mScrollingRunnable = null;
                }
            };
            post(mScrollingRunnable);
        }
    }

    /**
     * Listener to send the event when the scrolling stops
     */
    public interface NestedScrollListener {
        /**
         * Callback when scroll is stopped.
         */
        void onNestedScrollStopped();
    }
}
