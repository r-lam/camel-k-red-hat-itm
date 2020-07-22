package db2db.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GeneralData {
    @JsonIgnore
    private Object meta;

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public List<DrugData> getResults() {
        return results;
    }

    public void setResults(List<DrugData> results) {
        this.results = results;
    }

}