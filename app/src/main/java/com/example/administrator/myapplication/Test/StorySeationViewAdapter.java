package com.example.administrator.myapplication.Test;


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

import com.example.administrator.myapplication.MainListItem;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StorySeationViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<String> list;
    private StorySeationOnclick storySeationOnclick;

    public interface StorySeationOnclick{
        void onClick(String st);
    }

    public void setStorySeationOnclick(StorySeationOnclick storySeationOnclick) {
        this.storySeationOnclick = storySeationOnclick;
    }



    public StorySeationViewAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_seation_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder)holder).name.setText(list.get(position));
        ((MyViewHolder)holder).name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(storySeationOnclick!=null)
                   storySeationOnclick.onClick(list.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.title);
        }
    }






}
