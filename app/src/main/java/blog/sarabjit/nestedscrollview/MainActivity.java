package blog.sarabjit.nestedscrollview;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements CustomizedNestedScrollView.NestedScrollListener {
    private static final String TAG = "MainActivity";
    private Rect mRect;
    private LinearLayout mLinearLayout;
    private CustomizedNestedScrollView mCustomizedNestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setListener();
    }

    /**
     * Set Listener
     */
    private void setListener() {
        mCustomizedNestedScrollView.setNestedScrollListener(this);
    }

    /**
     * Initialize Views
     */
    private void initializeViews() {
        mLinearLayout = findViewById(R.id.container);
        mCustomizedNestedScrollView = findViewById(R.id.nestedScrollview);
        mRect = new Rect();
        mCustomizedNestedScrollView.getHitRect(mRect);
    }

    @Override
    public void onNestedScrollStopped() {
        Log.d(TAG, "Nested Scroll Stopped");
        findCompletelyVisibleChildren();
    }

    /**
     * find completely visible Positions
     */
    private void findCompletelyVisibleChildren() {
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            View view = mLinearLayout.getChildAt(i);
            if (view != null) {
                if (!view.getLocalVisibleRect(mRect) || mRect.height() < view.getHeight()) {
                    Log.d(TAG, "View " + view.getTag() + " is Partially Visible");
                } else {
                    Log.d(TAG, "View " + view.getTag() + " is Completely Visible");
                }
            }
        }
    }
}
