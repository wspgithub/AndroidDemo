package com.example.administrator.myapplication.RemoteViewTest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.GridViewTest.GridViewActivity;
import com.example.administrator.myapplication.R;

/**
 * Created by wsp on 2019/4/15.
 */
@ShowActivity
public class RemoteViewTestActivity extends AppCompatActivity {

    private Button button;
    private Button buttonTwo;
    //private RemoteViewsService
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_button_layout);
        button = findViewById(R.id.button);
        buttonTwo = findViewById(R.id.buttonTwo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemoteViewTestActivity.this,RemoteViewService.class);
                startService(intent);
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemoteViewTestActivity.this,RemoteViewService.class);
                stopService(intent);
            }
        });


    }

    private void showDefaultNotification() {
        //使用系统默认的样式弹出一个通知的方式如下：（android3.0之后）
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // 设置通知的基本信息：icon、标题、内容
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("My notification");
        builder.setContentText("Hello World!");
        builder.setAutoCancel(true);

        // 设置通知的点击行为：这里启动一个 Activity
        Intent intent = new Intent(this, GridViewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // 发送通知 id 需要在应用内唯一
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    private void showCustomNotification() {

        RemoteViews remoteView;
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
        Intent intent = new Intent(this, GridViewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
       // notification.flags = Notification.FLAG_ONGOING_EVENT;
      //  notification.flags = Notification.FLAG_AUTO_CANCEL;
      //  notification.defaults = Notification.DEFAULT_VIBRATE;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1001, notification);

        /**
         * layout：

         FrameLayout、Linearlayout、Relativelayout、GridLayout

         View:

         AnalogClock、Button、Chronometer、ImageButton、ImageView、ProgressBar、TextView、ViewFlipper、ListView、GridView、StackView、AdapterViewFlipper、ViewStub
         */
    }

}
