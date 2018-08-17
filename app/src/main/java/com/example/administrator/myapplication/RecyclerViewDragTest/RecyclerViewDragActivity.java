package com.example.administrator.myapplication.RecyclerViewDragTest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.MainListItem;
import com.example.administrator.myapplication.MainRecyclerViewAdapter;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/14.
 */
@ShowActivity
public class RecyclerViewDragActivity extends AppCompatActivity implements RecyItemTouchHelperCallback.OnDevItemDropListener {

    private RecyclerView recyclerView;
    private ArrayList<MainListItem> arrayList = new ArrayList<>();
    private RecyItemTouchHelperCallback itemTouchHelperCallBack;
    // 设备长按对话框
    private HomeDevMoreDialog mDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recy_drag);
        recyclerView =findViewById(R.id.mRecyclerView);
        for (int i = 0; i < 100; i++) {
            arrayList.add(new MainListItem("测试"+i,null));
        }
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(this,arrayList);
        itemTouchHelperCallBack = new RecyItemTouchHelperCallback(mainRecyclerViewAdapter, false);
        itemTouchHelperCallBack.setOnDevItemDropListener(this);



        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        //itemTouchHelper.
        mainRecyclerViewAdapter.setItemTouchHelper(itemTouchHelper);
        //recyclerView.item
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
            }

            @Override
            public void onLongClick(final RecyclerView.ViewHolder viewHolder) {
                if (itemTouchHelperCallBack != null && itemTouchHelperCallBack.isDraging()) {
                    return;
                }
                if (mDialog == null) {
                    mDialog = new HomeDevMoreDialog(RecyclerViewDragActivity.this);
                }
                mDialog.setOnHomeDevLongClickListener(new HomeDevMoreDialog.OnHomeDevLongClickListener() {
                    @Override
                    public void onDevDrag() {
                        //itemTouchHelper.startDrag(viewHolder);

                    }

                    @Override
                    public void onDevDetail() {
                        try {
                            if (viewHolder.itemView != null) {
//                                LinearLayout itemParent = (LinearLayout) viewHolder.itemView;
//                                if (itemParent.getChildCount() > 0 && itemParent.getChildAt(0) instanceof MainListItem) {
//                                    MainListItem itemView = (MainListItem) itemParent.getChildAt(0);
//                                    itemView.onDeviceViewClick();
//                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                if (!mDialog.isShowing()) {
                    mDialog.show();
                }
            }
        });

        //recyclerView.setLayoutManager(new GridLayoutManager(RecyclerViewDragActivity.this, 4));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mainRecyclerViewAdapter);
    }

    @Override
    public void onDragStop() {
        Log.e("","");
    }
}
