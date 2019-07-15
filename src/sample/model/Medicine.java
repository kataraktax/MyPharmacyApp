package sample.model;

public class Medicine {

    private int id;
    private String name;
    private String description;
    private java.sql.Date expireDate;
    private int headache;
    private int fever;
    private int cold;
    private int cough;

    public Medicine() {
    }

    public Medicine(int id, String name, String description, java.sql.Date expireDate, int headache, int fever, int cold, int cough) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.expireDate = expireDate;
        this.headache = headache;
        this.fever = fever;
        this.cold = cold;
        this.cough = cough;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Medicine{" + "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", expireDate=" + expireDate +
                '}';
    }
}
