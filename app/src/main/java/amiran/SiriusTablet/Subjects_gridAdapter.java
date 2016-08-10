package amiran.SiriusTablet;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

import amiran.siriustablet_test.R;

public class Subjects_gridAdapter extends BaseDynamicGridAdapter {
    public final String LOG_TAG = Subjects_gridAdapter.class.getName();
    Context mcontext;
    List<SubjectDetail> msubjects;

    public Subjects_gridAdapter(Context context, List<SubjectDetail> subjects, int columnCount) {
        super(context, subjects, columnCount);
        mcontext = context;
        msubjects = subjects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //==Null because convertview is always the view of the previous item
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.subjects_grid_item, null);


            // int he next lines of code I am going to calculate the width of the subject icon

            Display display = ((WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int display_width = display.getWidth();

            //used this for calculating the width of gridview
            int gridView_margin = (int) (mcontext.getResources().getDimension(R.dimen.subjectGridView_margin)
                    / mcontext.getResources().getDisplayMetrics().density);

            //gridview_layout width is 1/4 of hole screen
            //but gridview has margins so - gridview-margin*2
            int gridView_width = (int) (display_width * 0.4 - gridView_margin * 2);

            int subject_margin = (int) (mcontext.getResources().getDimension(R.dimen.subjects_margin)
                    / mcontext.getResources().getDisplayMetrics().density);

            int subject_width = ((gridView_width - subject_margin * 2) / 3);

            //both subject_width, because it should be a square
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(subject_width, subject_width);
            convertView.setLayoutParams(params);
        }

        SubjectDetail detail = (SubjectDetail) getItem(position);
        ImageView icon = null;
        if (detail != null) {
            if (detail.pic != 0) {
                Log.d(LOG_TAG, detail.name);
                Drawable drawable = mcontext.getResources().getDrawable(detail.pic);
                icon = (ImageView) convertView.findViewById(R.id.subject_imageView);
                icon.setBackground(drawable);
            }
        }

        return convertView;
    }
}
