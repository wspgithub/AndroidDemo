package com.example.administrator.myapplication.AIDLTest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;


import com.example.administrator.myapplication.IBookManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2018/8/13.
 */

public class BookManagerService extends Service {


    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(0,"一"));
        mBookList.add(new Book(1,"二"));
    }

    private IBinder iBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
}
