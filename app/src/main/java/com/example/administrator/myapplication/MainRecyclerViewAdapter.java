package com.example.administrator.myapplication;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class MainRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<MainListItem> dataList = new ArrayList<>();

    public ArrayList<MainListItem> getDataList() {
        return dataList;
    }

    public MainRecyclerViewAdapter(ArrayList<MainListItem> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).name.setText(dataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



    private static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.mainItem);
        }
    }



    public void deteleItem(int position)
    {
        dataList.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position,dataList.size()-position);
    }

    public void clearListData()
    {
        dataList.clear();
        this.notifyDataSetChanged();
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
