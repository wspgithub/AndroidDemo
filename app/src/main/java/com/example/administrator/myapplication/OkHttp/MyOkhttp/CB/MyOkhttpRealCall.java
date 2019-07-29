package com.example.administrator.myapplication.OkHttp.MyOkhttp.CB;



import android.util.Log;

import com.example.administrator.myapplication.OkHttp.MyOkhttp.Interceptor.MyOkhttpCallServerInterceptor;
import com.example.administrator.myapplication.OkHttp.MyOkhttp.Interceptor.MyOkhttpInterceptor;
import com.example.administrator.myapplication.OkHttp.MyOkhttp.Interceptor.MyOkhttpRealInterceptorChain;
import com.example.administrator.myapplication.OkHttp.MyOkhttp.MyOkHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class MyOkhttpRealCall implements MyOkhttpCall {

    private MyOkHttpClient myOkHttpClient = new MyOkHttpClient();


    public void enqueue(MyOkhttpCallBack myOkhttpCallBack){
        myOkHttpClient.getMyOkhttpDispatcher().enqueue(new MyOkhttpAsyncCall(myOkhttpCallBack));
    }

    public class MyOkhttpAsyncCall implements Runnable{
      public MyOkhttpCallBack callBack;

        public MyOkhttpAsyncCall(MyOkhttpCallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void run() {
            MyOkhttpResponse response = getResponseWithInterceptorChain();
            if(response.getMessage() == ""){
                callBack.onFailure(MyOkhttpRealCall.this,new IOException("Canceled"));
            }else{
                callBack.onResponse(MyOkhttpRealCall.this,response);
            }
        }
    }

    public MyOkhttpResponse  getResponseWithInterceptorChain(){
        List<MyOkhttpInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new MyOkhttpCallServerInterceptor());

        MyOkhttpRealInterceptorChain chain = new MyOkhttpRealInterceptorChain(interceptors,0);

        return chain.proceed();
    }
}
