package amiran.SiriusTablet;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.DragEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

class AppWidgetListeners implements View.OnLongClickListener, View.OnDragListener {
    private View dragingView;
    private TextView delete_bar_tv;

    public AppWidgetListeners(TextView delete_tv) {
        delete_bar_tv = delete_tv;
    }

    private AppWidgetListeners(View v) {
        dragingView = v;
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData.Item item = new ClipData.Item("drag_item");
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData clipData = new ClipData("drag_clipdata", mimeTypes, item);
        View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
        v.startDrag(clipData, shadow, null, 0);
        v.setVisibility(View.INVISIBLE);
        delete_bar_tv.setVisibility(View.VISIBLE);

        new AppWidgetListeners(v);

        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dragingView.getWidth(), dragingView.getHeight());
                params.leftMargin = (int) event.getX() - (dragingView.getWidth() / 2);
                params.topMargin = (int) event.getY() - (dragingView.getHeight() / 2);

                //dragging_view is the View which is longpressed. Have a look
                //at LongClickListener
                dragingView.setLayoutParams(params);

                //making the icon visible
                dragingView.setVisibility(View.VISIBLE);

                //making the delete bar invisible when the icon is droped
                delete_bar_tv.setVisibility(View.INVISIBLE);
            case DragEvent.ACTION_DRAG_ENDED:
                boolean drop = event.getResult();
                if (drop == false) {
                    delete_bar_tv.setVisibility(View.INVISIBLE);
                }
        }
        return true;
    }
}
