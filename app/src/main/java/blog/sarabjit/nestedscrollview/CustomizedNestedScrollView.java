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
    private Runnable mScrollingRunnable;

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
        }
        performClick();
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public void setNestedScrollListener(NestedScrollListener nestedScrollListener) {
        this.mNestedScrollListener = nestedScrollListener;
    }

    @Override
    protected void onScrollChanged(int newX, int newY, int oldX, int oldY) {
        super.onScrollChanged(newX, newY, oldX, oldY);
        if (Math.abs(oldY - newY) > 0) {
            if (mScrollingRunnable != null) {
                removeCallbacks(mScrollingRunnable);
            }
            mScrollingRunnable = new Runnable() {
                public void run() {
                    if (mIsScrolling && mNestedScrollListener != null) {
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
     * Listener to send the event when the scrolling stops
     */
    public interface NestedScrollListener {
        /**
         * Callback when scroll is stopped.
         */
        void onNestedScrollStopped();
    }
}
