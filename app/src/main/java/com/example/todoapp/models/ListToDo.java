package com.example.todoapp.models;

import java.util.ArrayList;

public class ListToDo {
    String Id;
    String Title;
    ArrayList<TaskToDo> Tasks = new ArrayList<>();

    public ListToDo() {
    }

    public ListToDo(String id, String title) {
        Id = id;
        Title = title;
    }

    public ListToDo(String id, String title, ArrayList<TaskToDo> tasks) {
        Id = id;
        Title = title;
        Tasks = tasks;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<TaskToDo> getTasks() {
        return Tasks;
    }

    public void setTasks(ArrayList<TaskToDo> tasks) {
        Tasks = tasks;
    }
}
