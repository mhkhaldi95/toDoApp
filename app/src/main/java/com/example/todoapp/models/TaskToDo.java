package com.example.todoapp.models;

public class TaskToDo {

    private String Id;
    private String Title;
    private String Date;
    private String Description;
    private boolean isChecked;

    public TaskToDo() {
    }

    public TaskToDo(String title, String date, String description, boolean isChecked) {
        Title = title;
        Date = date;
        Description = description;
        this.isChecked = isChecked;
    }

    public TaskToDo(String id, String title, String date, String description, boolean isChecked) {
        Id = id;
        Title = title;
        Date = date;
        Description = description;
        this.isChecked = isChecked;
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}