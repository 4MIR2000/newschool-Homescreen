package multiscreenfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import de.newschool.homescreen.R;

public class Fragment1 extends android.support.v4.app.Fragment {
    private static LinearLayout layout; // @TODO Dangerous static instance

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
