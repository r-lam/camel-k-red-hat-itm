package db2db.pojo;

public class Circuit {
    private String id;
    private String sensorName;
    private String monthYear;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id= id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getmonthYear() {
        return monthYear;
    }

    public void setmonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}