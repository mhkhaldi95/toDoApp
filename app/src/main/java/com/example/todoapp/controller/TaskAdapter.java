package com.example.todoapp.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.example.todoapp.R;
import com.example.todoapp.TaskActivity;
import com.example.todoapp.models.ListToDo;
import com.example.todoapp.models.TaskToDo;
import com.google.firebase.database.DatabaseReference;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    Activity activity;
    ListToDo data;
    DatabaseReference listRef;

    public TaskAdapter(Activity activity, ListToDo data, DatabaseReference listRef) {
        this.activity = activity;
        this.data = data;
        this.listRef = listRef;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.task_itam, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(lp);
        return new TaskViewHolder(root);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskToDo task = data.getTasks().get(position);
        holder.task_title.setText(task.getTitle());
        holder.task_check.setChecked(task.isChecked());

        if(task.isChecked()){
            holder.task_title.setTextColor(activity.getColor(R.color.black));
            holder.task_title.setBackground(activity.getDrawable(R.drawable.style_line));
        }

        holder.task_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listRef.child("tasks").child(task.getId()).child("checked").setValue(isChecked);
            if(isChecked){
                holder.task_title.setTextColor(activity.getColor(R.color.black));
                holder.task_title.setBackground(activity.getDrawable(R.drawable.style_line));
            } else {
                holder.task_title.setTextColor(activity.getColor(R.color.style_uncheck_box));
                holder.task_title.setBackground(null);
            }
        });

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(activity, TaskActivity.class);
            intent.putExtra("listId", data.getId());
            intent.putExtra("taskId", task.getId());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.getTasks().size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        private final TextView task_title;
        private  final CheckBox task_check;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            task_title = itemView.findViewById(R.id.task_title);
            task_check = itemView.findViewById(R.id.task_check);
        }
    }

}