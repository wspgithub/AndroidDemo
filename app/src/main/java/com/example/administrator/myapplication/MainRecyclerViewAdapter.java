package com.example.administrator.myapplication;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterView.OnItemClickListener {


    private Context context;
    private ArrayList<MainListItem> dataList = new ArrayList<>();

    public ArrayList<MainListItem> getDataList() {
        return dataList;
    }

    private ItemTouchHelper itemTouchHelper;
    public MainRecyclerViewAdapter(Context context,ArrayList<MainListItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder)holder).name.setText(dataList.get(position).getName());
        ((MyViewHolder)holder).name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataList.get(position).getaClass()!=null) {
                    Intent intent = new Intent();
                    intent.setClass(context, dataList.get(position).getaClass());
                    context.startActivity(intent);
                }
            }
        });

        ((MyViewHolder)holder).imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        itemTouchHelper.startDrag(holder);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }


    private static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.mainItem);
            imageView = (ImageView) itemView.findViewById(R.id.img);
        }
    }



    public void deteleItem(int position)
    {
        dataList.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position,dataList.size()-position);
    }

    public List<MainListItem> getItemViews() {
        return dataList;
    }

    public void clearListData()
    {
        dataList.clear();
        this.notifyDataSetChanged();
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    //希尔排序-根据序号排列章节的位置
//    public void shellSort(ArrayList<Story> arrayList)
//    {
//        int j = 0;
//        Story story = new Story();
//        for (int increment = arrayList.size() / 2; increment > 0; increment /= 2) {
//            for (int i = increment; i < arrayList.size(); i++) {
//                story.setNum(arrayList.get(i).getNum());
//                story.setText(arrayList.get(i).getText());
//                story.setTitle(arrayList.get(i).getTitle());
//                for (j = i - increment; j >= 0; j -= increment) {
//
//                    if (Integer.parseInt(story.getNum()) < Integer.parseInt(arrayList.get(j).getNum())) {
//                        arrayList.get(j+increment).setNum(arrayList.get(j).getNum());
//                        arrayList.get(j+increment).setTitle(arrayList.get(j).getTitle());
//                        arrayList.get(j+increment).setText(arrayList.get(j).getText());
//                        //arrayList.get(j+increment)=arrayList.get(j);
//                    } else {
//                        break;
//                   }
//               }
//               arrayList.get(j+increment).setNum(story.getNum());
//              arrayList.get(j+increment).setTitle(story.getTitle());
//                arrayList.get(j+increment).setText(story.getText());
//            }
//
//       }
//    }

}
