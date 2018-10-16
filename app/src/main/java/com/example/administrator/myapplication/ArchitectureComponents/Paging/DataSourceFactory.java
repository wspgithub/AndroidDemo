package com.example.administrator.myapplication.ArchitectureComponents.Paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

public class DataSourceFactory
        extends DataSource.Factory<Integer, DataBean> {

    private MutableLiveData<LocalTestDataSource> mSourceLiveData =
            new MutableLiveData<>();

    @Override
    public DataSource<Integer, DataBean> create() {
        LocalTestDataSource source = new LocalTestDataSource();
        mSourceLiveData.postValue(source);
        return source;

    }
}