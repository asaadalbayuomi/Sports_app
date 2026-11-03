package com.example.mytype;

/**
 * نموذج بيانات التمرين (POJO) — يمثّل صفاً واحداً في جدول exercises.
 * كلاس بيانات فقط: ليس Adapter وليس View.
 */
public class Exercise {

    private final int id;
    private final String name;
    private final int duration;
    private boolean completed;
    private final String muscleGroup;

    public Exercise(int id, String name, int duration, boolean completed, String muscleGroup) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.completed = completed;
        this.muscleGroup = muscleGroup;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getDuration() { return duration; }

    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getMuscleGroup() { return muscleGroup; }
}
