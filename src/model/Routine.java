package src.model;
import java.util.Date;

public class Routine {
    private int id;
    private String name;
    private Date dateCreated;

    public Routine(int id, String name, Date dateCreated) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Date getDateCreated() { return dateCreated; }
}
