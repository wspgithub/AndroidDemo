package com.example.administrator.myapplication.AIDLTest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.IBookManager;
import com.example.administrator.myapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */
@ShowActivity
public class BookManagerClient extends AppCompatActivity {

    private IBookManager iBookManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aidl);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager  = IBookManager.Stub.asInterface(service);

            try {
               // service.linkToDeath(deathRecipient,0);
                List<Book> list = iBookManager.getBookList();
                for (Book book:list){
                    Log.e("打印",book.getBookName());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
          if(iBookManager == null)
              return;

            iBookManager.asBinder().unlinkToDeath(deathRecipient,0);
            iBookManager = null;
            //重新绑定远程服务
            Intent intent = new Intent(BookManagerClient.this,BookManagerService.class);
            bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        }
    };

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
