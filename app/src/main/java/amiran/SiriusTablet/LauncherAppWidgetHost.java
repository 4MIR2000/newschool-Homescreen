package amiran.SiriusTablet;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;

/**
 * Created by amirt on 05.03.2016.
 */
public class LauncherAppWidgetHost extends AppWidgetHost {

    public LauncherAppWidgetHost(Context context, int hostId) {
        super(context, hostId);
    }

    @Override
    protected AppWidgetHostView onCreateView(Context context, int appWidgetId,
                                             AppWidgetProviderInfo appWidget) {
        return new LauncherAppWidgetHostView(context);
    }

    @Override
    public void stopListening() {
        super.stopListening();
        clearViews();
    }
}