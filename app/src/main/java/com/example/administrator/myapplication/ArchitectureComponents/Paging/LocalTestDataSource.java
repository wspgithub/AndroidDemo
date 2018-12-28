package com.example.administrator.myapplication.ArchitectureComponents.Paging;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocalTestDataSource extends PositionalDataSource<DataBean> {

    @Override
    public void loadInitial(@NonNull LoadInitialParams params,
                            final @NonNull LoadInitialCallback<DataBean> callback) {
        final int startPosition = 0;
        List<DataBean> list = buildDataList(startPosition, params.requestedLoadSize);
        //将数据回调
        callback.onResult(list, 0);
    }

    @Override
    public void loadRange(@NonNull final LoadRangeParams params,
                          @NonNull final LoadRangeCallback<DataBean> callback) {
        List<DataBean> list = buildDataList(params.startPosition, params.loadSize);
        callback.onResult(list);
    }

    private List<DataBean> buildDataList(int startPosition, int loadSize) {
        List<DataBean> list = new ArrayList<>();
        DataBean bean;
        for (int i = startPosition; i < startPosition + loadSize; i++) {
            bean = new DataBean();
            bean.id = i;
            bean.content = String.format(Locale.getDefault(), "第%d条数据", i + 1);
            list.add(bean);
        }
        return list;
    }
}
