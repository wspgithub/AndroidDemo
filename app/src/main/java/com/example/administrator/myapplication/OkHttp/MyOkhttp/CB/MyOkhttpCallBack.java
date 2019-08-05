package com.example.administrator.myapplication.OkHttp.MyOkhttp.CB;

import java.io.IOException;

public interface MyOkhttpCallBack {

     void onFailure(MyOkhttpCall myOkhttpCall, IOException io);

     void onResponse(MyOkhttpCall myOkhttpCall,MyOkhttpResponse myOkhttpResponse);

}
