package sample.model;

import java.sql.Date;

public class Treatment {

    private int id;
    private String name;
    private java.sql.Date startDate;
    private int duration;

    public Treatment() {
    }

    public Treatment(String name) {
        this.name = name;
    }

    public Treatment(String name, Date startDate, int duration) {
        this.name = name;
        this.startDate = startDate;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", duration=" + duration +
                '}';
    }
}
