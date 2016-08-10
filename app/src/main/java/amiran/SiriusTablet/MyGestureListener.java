package amiran.SiriusTablet;

import android.app.Activity;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
    Context mcontext;
    Activity mactivity;

    public MyGestureListener(Context context, Activity activity) {
        mcontext = context;
        mactivity = activity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2.getX() > e1.getX()) {
            // Intent intent = new Intent(mcontext,mainActivity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            // mcontext.startActivity(intent);

            mactivity.finish();
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
