package sample.model;

import java.sql.Date;

public class Treatment {

    private int id;
    private String name;
    private java.sql.Date startDate;
    private int duration;
    private int headache;
    private int fever;
    private int cold;
    private int cough;

    public Treatment() {
    }

    public Treatment(String name) {
        this.name = name;
    }

    public Treatment(int id, String name, Date startDate, int duration, int headache, int fever, int cold, int cough) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.duration = duration;
        this.headache = headache;
        this.fever = fever;
        this.cold = cold;
        this.cough = cough;
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

    public int getHeadache() {
        return headache;
    }

    public void setHeadache(int headache) {
        this.headache = headache;
    }

    public int getFever() {
        return fever;
    }

    public void setFever(int fever) {
        this.fever = fever;
    }

    public int getCold() {
        return cold;
    }

    public void setCold(int cold) {
        this.cold = cold;
    }

    public int getCough() {
        return cough;
    }

    public void setCough(int cough) {
        this.cough = cough;
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
