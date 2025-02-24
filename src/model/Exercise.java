package src.model;

public class Exercise {
    private int id;
    private String name;
    private double weight;
    private int duration;
    private int reps;

    public Exercise(int id, String name, double weight, int duration, int reps) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.duration = duration;
        this.reps = reps;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getWeight() { return weight; }
    public int getDuration() { return duration; }
    public int getReps() { return reps; }
}

