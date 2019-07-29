package com.example.administrator.myapplication.OkHttp.MyOkhttp.Interceptor;

import com.example.administrator.myapplication.OkHttp.MyOkhttp.CB.MyOkhttpResponse;

import java.util.ArrayList;
import java.util.List;

public final class MyOkhttpRealInterceptorChain implements MyOkhttpInterceptor.Chain {

    List<MyOkhttpInterceptor> interceptors = new ArrayList<>();
    private final int index;

    public MyOkhttpRealInterceptorChain(List<MyOkhttpInterceptor> interceptors, int index) {
        this.interceptors = interceptors;
        this.index = index;
    }

    @Override
    public MyOkhttpResponse proceed() {
        if (index >= interceptors.size())throw new AssertionError();

        MyOkhttpInterceptor.Chain chain = new MyOkhttpRealInterceptorChain(interceptors,index+1);
        MyOkhttpInterceptor myOkhttpInterceptor = interceptors.get(index);
        return myOkhttpInterceptor.intercept(chain);
    }
}
