package multiscreenfragments;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import amiran.siriustablet.test.R;

public class PagerChangeListener implements ViewPager.OnPageChangeListener {
    private static final String LOG_TAG = PagerChangeListener.class.getName();
    private static ImageView[] mdots; // @TODO Dangerous static instance

    public PagerChangeListener(ImageView[] dots) {
        mdots = dots;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(LOG_TAG, "onPageScrolled");
    }

    @Override
    public void onPageSelected(int position) {
        for (final ImageView mdot : mdots) {
            mdot.setImageResource(R.drawable.indicator_nonselecteditem_dot);
        }

        mdots[position].setImageResource(R.drawable.indicator_selecteditem_dot);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(LOG_TAG, "onPageScrollStateChanged");
    }
}
