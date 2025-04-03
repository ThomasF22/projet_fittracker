package com.fittracker.model;
import java.util.Date;
import java.util.List;

public class Routine {
    private int id;
    private String name;
    private Date dateCreated;

    public Routine() {}

    public Routine(int id, String name, Date dateCreated) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Date getDateCreated() { return dateCreated; }



    @Override
    public String toString(){
        return this.name;
    }
}
