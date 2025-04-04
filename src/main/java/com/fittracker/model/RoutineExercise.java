package com.fittracker.model;

import java.util.List;

public class RoutineExercise {
    private int id;
    private Exercise exercise;
    private double weight;
    private int duration;
    private int reps;

    public RoutineExercise() {}
    
    public RoutineExercise(int id, Exercise exercise, double weight, int duration, int reps) {
        this.id = id;
        this.exercise = exercise;
        this.weight = weight;
        this.duration = duration;
        this.reps = reps;
    }

    public int getId() { return id; }
    public Exercise getExercise() { return exercise; }
    public String getExerciseName() { return this.exercise.getName();}
    public int getExerciseId() { return this.exercise.getId();}
    public double getWeight() { return weight; }
    public int getDuration() { return duration; }
    public int getReps() { return reps; }

    public void setWeight(double weight) { this.weight = weight;}
    public void setReps(int reps) { this.reps = reps;}

    public void setDuration(int duration) { this.duration = duration;}



    @Override
    public String toString() {
        return this.exercise.getName();
    }
}
