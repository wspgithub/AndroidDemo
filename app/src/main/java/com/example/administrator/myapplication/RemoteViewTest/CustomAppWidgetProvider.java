package com.example.administrator.myapplication.RemoteViewTest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.administrator.myapplication.GridViewTest.GridViewActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.SeekBarTest.SeekBarTestActivity;

/**
 * Created by Daniel on 2017/7/1.
 */

public class CustomAppWidgetProvider extends AppWidgetProvider {

    public static final String CLICK_WEDGET_ONE = "com.shenhuniurou.appwidgetprovider.click.one";

    public static final String CLICK_WEDGET_TWO = "com.shenhuniurou.appwidgetprovider.click.two";

    public static final String CLICK_WEDGET_THREE = "com.shenhuniurou.appwidgetprovider.click.three";

    public static final String CLICK_WEDGET_FOUR = "com.shenhuniurou.appwidgetprovider.click.four";


    public CustomAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.small_remote_view);

        // 判断action是否是自己定义的action
        if (action.equals(CLICK_WEDGET_ONE)) {

            // 点击的是第一个按钮
            Intent firstIntent = Intent.makeRestartActivityTask(new ComponentName(context, GridViewActivity.class));
            Intent secondIntent = new Intent(context, SeekBarTestActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivities(context, 0, new Intent[] { firstIntent, secondIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.btn1, pendingIntent);

        } else if (action.equals(CLICK_WEDGET_TWO)) {

            // 点击的是第二个按钮
//            Intent clickIntent = new Intent(context, ThirdActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
//            remoteViews.setOnClickPendingIntent(R.id.btn2, pendingIntent);

        } else if (action.equals(CLICK_WEDGET_THREE)) {

            // 点击的是第三个按钮
//            Intent clickIntent = new Intent(context, ForthActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
//            remoteViews.setOnClickPendingIntent(R.id.btn3, pendingIntent);

        } else if (action.equals(CLICK_WEDGET_FOUR)) {

            // 点击的是第四个按钮
//            Intent clickIntent = new Intent(context, FifthActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
//            remoteViews.setOnClickPendingIntent(R.id.btn4, pendingIntent);

        }

        appWidgetManager.updateAppWidget(new ComponentName(context, CustomAppWidgetProvider.class), remoteViews);

    }

    /**
     * 桌面小部件每次更新时调用的方法
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }
    }


    /**
     * 更新桌面小部件
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.small_remote_view);

        Intent intent1 = new Intent(CLICK_WEDGET_ONE);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn1, pendingIntent1);

        Intent intent2 = new Intent(CLICK_WEDGET_TWO);
//        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.btn2, pendingIntent2);

        Intent intent3 = new Intent(CLICK_WEDGET_THREE);
//        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context, 0, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.btn3, pendingIntent3);

        Intent intent4 = new Intent(CLICK_WEDGET_FOUR);
//        PendingIntent pendingIntent4 = PendingIntent.getBroadcast(context, 0, intent4, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.btn4, pendingIntent4);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

}