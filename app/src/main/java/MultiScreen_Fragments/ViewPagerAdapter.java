package MultiScreen_Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by ASUS on 27.05.2016.
 */
public class ViewPagerAdapter extends android.support.v4.view.PagerAdapter {

    public List<RelativeLayout> layouts;

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
        container.removeView((View)object);

    }

    public RelativeLayout getLayout(int position){

        return layouts.get(position);
    }
}
