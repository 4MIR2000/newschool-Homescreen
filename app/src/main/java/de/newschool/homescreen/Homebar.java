package de.newschool.homescreen;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by ASUS on 11.11.2016.
 */
public class Homebar extends ViewGroup {
    public Homebar(Context context) {
        super(context);

    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Drawable drawable = getResources().getDrawable(R.drawable.fach_biologie);

        ImageView imageView = new ImageView(MainActivity.getContext());
        imageView.setImageDrawable(drawable);

        addView(imageView);
    }
}
