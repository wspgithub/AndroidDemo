package com.example.administrator.myapplication.OkHttp.MyOkhttp;


import com.example.administrator.myapplication.OkHttp.MyOkhttp.CB.MyOkhttpRealCall;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.internal.Util;

public final class MyOkhttpDispatcher {

    private ExecutorService executorService;

    private final Deque<MyOkhttpRealCall.MyOkhttpAsyncCall> readyAsyncCalls = new ArrayDeque<>();

    private final Deque<MyOkhttpRealCall.MyOkhttpAsyncCall> runningAsyncCalls = new ArrayDeque<>();

    private final Deque<MyOkhttpRealCall> runningSyncCalls = new ArrayDeque<>();


    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", false));
        }
        return executorService;
    }

    synchronized public void enqueue(MyOkhttpRealCall.MyOkhttpAsyncCall myOkhttpAsyncCall){
        runningAsyncCalls.add(myOkhttpAsyncCall);
        executorService().execute(myOkhttpAsyncCall);
    }
}
