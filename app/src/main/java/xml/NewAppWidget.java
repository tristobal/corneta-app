package xml;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.RemoteViews;

import com.example.csanchez.myapplication.MainActivity;
import com.example.csanchez.myapplication.R;

public class NewAppWidget extends AppWidgetProvider {

    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";

    @Override
    public void onReceive(Context context, Intent intent) {
        // v1.5 fix that doesn't call onDelete Action
        final String action = intent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
            //The widget is being deleted off the desktop
            final int appWidgetId = intent.getExtras().getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                this.onDeleted(context, new int[] { appWidgetId });
            }
        } else {
            // check, if our Action was called
            if (intent.getAction().equals(ACTION_WIDGET_RECEIVER)) {

                //Play the audio file
                //The audio file is in /res/raw/ and is an OGG file
                MediaPlayer mp = MediaPlayer.create(context, R.raw.corneta);
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(context, R.raw.corneta);
                }
                mp.start();
            } else {
                // do nothing
            }
            super.onReceive(context, intent);
        }
    }



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Create an Intent to launch ExampleActivity
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.button_widget, null);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

//        //First up, the icon. I've created a file called h_yellow_x.png and placed it in /res/drawable
//        //int drawableResourse = R.drawable.example_appwidget_preview;
//        int drawableResourse = R.mipmap.ic_launcher;
//        //Set Up the widget
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
//        //Set the image which will appear on the screen
//        remoteViews.setImageViewResource(R.id.ImageView01, drawableResourse );
//        Intent active = new Intent(context, ButtonWidget.class);
//        active.setAction(ACTION_WIDGET_RECEIVER);
//        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
//        remoteViews.setOnClickPendingIntent(R.id.ImageView01, actionPendingIntent);
//        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

