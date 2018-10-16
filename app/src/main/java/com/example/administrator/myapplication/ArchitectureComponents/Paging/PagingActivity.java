package com.example.administrator.myapplication.ArchitectureComponents.Paging;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.ViewModelStore;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2018/10/15.
 */
@ShowActivity
public class PagingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ConcertAdapter mAdapter;
    private ConcertViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paging_layout);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ConcertAdapter(this);
        recyclerView.setAdapter(mAdapter);
        ViewModelProviders.of(this).get(null);
        ViewModelProvider provider = new ViewModelProvider(new ViewModelStore(),new ViewModelProvider.AndroidViewModelFactory(getApplication()));
        mViewModel = provider.get(ConcertViewModel.class);
        //LiveData关联到mAdapter,并与Activity相关联
        mViewModel.getConcertList().observe(this, new Observer<PagedList<DataBean>>() {
            @Override
            public void onChanged(@Nullable PagedList<DataBean> dataBeans) {
                mAdapter.submitList(mViewModel.getConcertList().getValue());
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新数据
                mViewModel.invalidateDataSource();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
