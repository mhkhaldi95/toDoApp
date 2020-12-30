package com.example.todoapp.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.todoapp.R;
import com.example.todoapp.TaskActivity;
import com.example.todoapp.models.ListToDo;

import java.util.ArrayList;

public class ListAdapterr extends RecyclerView.Adapter<ListAdapterr.ListViewHolder> {

    Activity activity;
    ArrayList<ListToDo> data;

    public ListAdapterr(Activity activity, ArrayList<ListToDo> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.list_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(lp);
        return new ListViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ListToDo list = data.get(position);
        holder.list_title.setText(list.getTitle());
        holder.task_count.setText(list.getTasks().size() + " Tasks");

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(activity, TaskActivity.class);
            intent.putExtra("listId", list.getId());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        public TextView list_title;
        public TextView task_count;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            list_title = itemView.findViewById(R.id.list_title);
            task_count = itemView.findViewById(R.id.task_count);
        }
    }
}