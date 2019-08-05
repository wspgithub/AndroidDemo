package com.example.administrator.myapplication.Test;

import android.os.AsyncTask;
import android.os.Binder;
import android.os.Process;
import android.provider.ContactsContract;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wsp on 2019/4/15.
 */

public abstract class MyAsyncTask<Params,Progress,Result> {

    private final static Executor executor;
    public static final Executor SERIAL_EXECUTOR = new SerialExecutor();
    private final AtomicBoolean mTaskInvoked = new AtomicBoolean();
    /**
     * 创建线程工厂
     */
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "download#" + mCount.getAndIncrement());
        }
    };
    private static final BlockingQueue<Runnable> workingDeque = new LinkedBlockingDeque<>(100);
    private AsyncTask asyncTask;
    private WorkRunable workRunable;
    private final FutureTask<Result> futureTask;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,3,60, TimeUnit.SECONDS,workingDeque,sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        executor = threadPoolExecutor;
    }


    public MyAsyncTask() {
        workRunable = new WorkRunable<Params,Result>() {
            @Override
            public Result call() throws Exception {
                Result result = null;
                mTaskInvoked.set(true);
                try {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    //noinspection unchecked
                    result = doInBackground(mParams);
                    Binder.flushPendingCommands();
                }catch (Throwable tr){
                    Log.e("TestAsyncTask",tr.getMessage());
                }finally {
                    postResult(result);
                }

                return null;
            }
        };

        futureTask = new FutureTask<Result>(workRunable){
                @Override
                protected void done() {
                try {
                    postResultIfNotInvoked(get());
                } catch (InterruptedException e) {

                } catch (ExecutionException e) {
                    throw new RuntimeException("An error occurred while executing doInBackground()",
                            e.getCause());
                } catch (CancellationException e) {
                    postResultIfNotInvoked(null);
                }
            }
            };

    }

    private void postResultIfNotInvoked(Result result) {
        final boolean wasTaskInvoked = mTaskInvoked.get();
        if (!wasTaskInvoked) {
            postResult(result);
        }
    }

    private static abstract class WorkRunable<Params,Result> implements Callable<Result>{
        Params[] mParams;
    }

    private static class SerialExecutor implements Executor {
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
        Runnable mActive;

        public synchronized void execute(final Runnable r) {
            mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (mActive == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                executor.execute(mActive);
            }
        }
    }


    @MainThread
    protected void onPreExecute(){}

    @WorkerThread
    protected abstract Result doInBackground(Params... params);

    private Result postResult(Result result) {

        return result;
    }


    public final void  execute(Params... params){

        onPreExecute();
        workRunable.mParams = params;
        executor.execute(futureTask);
       // SERIAL_EXECUTOR.execute(futureTask);
    }

}
