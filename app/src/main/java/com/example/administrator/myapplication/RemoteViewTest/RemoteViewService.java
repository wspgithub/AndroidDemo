package com.example.administrator.myapplication.RemoteViewTest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.administrator.myapplication.GridViewTest.GridViewActivity;
import com.example.administrator.myapplication.R;

/**
 * Created by wsp on 2019/4/18.
 */

public class RemoteViewService extends Service {
    private RemoteViews remoteView;
    private Notification notification;
    private NotificationManager notificationManager;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Service","开始");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        // 构建 remoteView
        remoteView = new RemoteViews(getPackageName(), R.layout.layout_notification);
        //  Button button = remoteView.
        remoteView.setTextViewText(R.id.buttonOne, "测试");
        // remoteView.setImageViewResource(R.id.buttonOne, R.mipmap.ic_launcher_round);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // 设置自定义 RemoteViews
        builder.setContent(remoteView).setSmallIcon(R.mipmap.ic_launcher);

        // 设置通知的优先级(悬浮通知)
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // 设置通知的提示音
        builder.setSound(alarmSound);


        // 设置通知的点击行为：这里启动一个 Activity
        Intent intentTwo = new Intent(this, GridViewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentTwo, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        builder.setAutoCancel(true);
        notification = builder.build();
         //notification.flags = Notification.FLAG_ONGOING_EVENT;
        //notification.flags = Notification.FLAG_AUTO_CANCEL;
        //  notification.defaults = Notification.DEFAULT_VIBRATE;

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1001, notification);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(1000);
                        remoteView.setTextViewText(R.id.buttonOne, "测试"+i);
                        notificationManager.notify(1001, notification);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Service","停止");
        notificationManager.cancel(1001);//通知栏消失
    }
}
