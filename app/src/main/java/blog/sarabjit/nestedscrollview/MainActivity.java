package blog.sarabjit.nestedscrollview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements CustomizedNestedScrollView.NestedScrollListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomizedNestedScrollView customizedNestedScrollView = findViewById(R.id.nestedScrollview);
        customizedNestedScrollView.setNestedScrollListener(this);
    }

    @Override
    public void onNestedScrollStopped() {
        Log.d(TAG, "Nested Scroll Stopped");
        //Write your logic here.
    }
}
