package de.newschool.homescreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
    private final Context mcontext;
    private final Activity mactivity;

    public MyGestureListener(Context context, Activity activity) {
        mcontext = context;
        mactivity = activity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1 != null && e2 != null){
        if (e2.getX() > e1.getX()) {
            //Intent intent = new Intent(mcontext,MainActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            // mcontext.startActivity(intent);


            mactivity.finish();
            
        }

        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
