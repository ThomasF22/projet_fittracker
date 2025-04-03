package com.fittracker.model;

import java.util.List;

public class Exercise {
    private int id;
    private String name;


    public Exercise() {}

    public Exercise(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() { 
        return id; 
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}

