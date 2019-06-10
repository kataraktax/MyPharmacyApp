package sample.model;

import java.time.LocalDate;
import java.util.Date;
import java.sql.*;

public class Medicine {

    private String name;
    private String description;
    private java.sql.Date expireDate;

    public Medicine() {
    }

    public Medicine(String name, String description, java.sql.Date expireDate) {
        this.name = name;
        this.description = description;
        this.expireDate = expireDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(java.sql.Date expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", expireDate=" + expireDate +
                '}';
    }
}
