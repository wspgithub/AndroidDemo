package com.example.administrator.myapplication.ArchitectureComponents.Paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConcertViewModel extends ViewModel {

    private Executor myExecutor = Executors.newSingleThreadExecutor();

    private PagedList.Config myPagingConfig = new PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(10)
            .setPrefetchDistance(30)
            .setEnablePlaceholders(false)
            .build();

    private DataSource.Factory<Integer, DataBean> myConcertDataSource =
            new DataSourceFactory();

    public LiveData<PagedList<DataBean>> getConcertList() {
        return concertList;
    }

    private LiveData<PagedList<DataBean>> concertList =
            new LivePagedListBuilder<>(myConcertDataSource, myPagingConfig)
                    .setFetchExecutor(myExecutor)
                    .build();

    //用于刷新数据
    public void invalidateDataSource() {
        PagedList<DataBean> pagedList = concertList.getValue();
        if (pagedList != null)
            pagedList.getDataSource().invalidate();
    }

}