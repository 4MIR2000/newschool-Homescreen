package multiscreenfragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

public class ViewPagerAdapter extends android.support.v4.view.PagerAdapter {
    private final List<RelativeLayout> layouts;

    public ViewPagerAdapter(List<RelativeLayout> layouts) {
        this.layouts = layouts;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(layouts.get(position));
        return layouts.get(position);
    }

    @Override
    public int getCount() {
        return layouts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public RelativeLayout getLayout(int position) {
        return layouts.get(position);
    }
}
