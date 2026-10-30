package com.example.mytype;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//  كلاس العنصر
public class Exercise extends RecyclerView.Adapter {
    private int id;
    private String name ;
    private int duration ;
    private boolean completed;
    private String muscleGroup ;
    public Exercise(int id, String name, int duration, boolean completed, String muscleGroup) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.completed = completed;
        this.muscleGroup = muscleGroup;
    }

    public Exercise(List<Exercise> list, MainActivity mainActivity) {
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getDuration() { return duration; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public String getMuscleGroup() { return muscleGroup; }

    public void updateData(List<Exercise> list) {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
