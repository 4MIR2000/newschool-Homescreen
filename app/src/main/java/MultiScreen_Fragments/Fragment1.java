package MultiScreen_Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import amiran.siriustablet_test.R;

public class Fragment1 extends android.support.v4.app.Fragment {
    public static LinearLayout layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        layout = (LinearLayout) inflater.inflate(R.layout.homescreen_fragment1_layout, container, false);

        return layout;
    }
}
