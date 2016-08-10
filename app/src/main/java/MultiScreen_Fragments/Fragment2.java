package MultiScreen_Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import amiran.siriustablet_test.R;

/**
 * Created by ASUS on 27.05.2016.
 */
public class Fragment2 extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null){
            return null;
        }

        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.homescreen_fragment2_layout,container,false);

        return layout;
    }
}
