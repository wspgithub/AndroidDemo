package com.example.administrator.myapplication.OkHttp.MyOkhttp.Interceptor;

import com.example.administrator.myapplication.OkHttp.MyOkhttp.CB.MyOkhttpResponse;

public interface MyOkhttpInterceptor  {

    MyOkhttpResponse intercept(Chain chain);

    interface Chain{
        MyOkhttpResponse proceed();
    }
}
