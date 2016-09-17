package de.newschool.homescreen;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.askerov.dynamicgrid.DynamicGridView;

import de.newschool.homescreen.R;

class SubjectListeners implements AdapterView.OnItemLongClickListener, DynamicGridView.OnDropListener, DynamicGridView.OnItemClickListener {
    private final Context mcontext;
    RelativeLayout.LayoutParams params;
    private final DynamicGridView mgridView;

    private static final String TAG = SubjectListeners.class.getName();

    public SubjectListeners(Context context, DynamicGridView gridview) {
        mcontext = context;
        mgridView = gridview;
        mgridView.setOnDropListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //mgridView.startEditMode(position);

        return true;
    }

    @Override
    public void onActionDrop() {
        Log.d(TAG, " item droped");
       // mgridView.stopEditMode();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Intent intent = mcontext.getPackageManager().getLaunchIntentForPackage("com.steadfastinnovation.android.projectpapyrus");

            if(intent != null) {
                SubjectDetail detail = (SubjectDetail) mgridView.getItemAtPosition(position);
                intent.putExtra("subject", detail.name);
                mcontext.startActivity(intent);

            }else{
                Toast.makeText(mcontext, mcontext.getResources().getString(R.string.writtingAppNotFound), Toast.LENGTH_SHORT).show();
            }



        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(mcontext, mcontext.getResources().getString(R.string.writtingAppNotFound), Toast.LENGTH_SHORT).show();
        }
    }
}
