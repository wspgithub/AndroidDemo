package com.example.administrator.myapplication.ArchitectureComponents.Paging;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//类似于RecyclerView.Adapter的写法,只不多了些一次数据比较DiffUtil.ItemCallback
public class ConcertAdapter extends PagedListAdapter<DataBean, ConcertAdapter.ConcertViewHolder> {

    private Context context;

    public ConcertAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ConcertViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new ConcertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcertViewHolder concertViewHolder, int position) {
        DataBean dataBean = getItem(position);
        if (dataBean == null) return;
        concertViewHolder.textView.setText(dataBean.content);
    }

    class ConcertViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ConcertViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    private static DiffUtil.ItemCallback<DataBean> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<DataBean>() {
                @Override
                public boolean areItemsTheSame(DataBean oldItem, DataBean newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(DataBean oldItem, @NonNull DataBean newItem) {
                    return oldItem.equals(newItem);
                }
            };

}