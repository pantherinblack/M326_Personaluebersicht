package ch.bzz.company;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

public class JobFunctions {
    public Vector<String> getDesignations() {
        return designations;
    }

    public void setDesignations(Vector<String> designations) {
        this.designations = designations;
    }

    private Vector<String> designations = new Vector<>();

    public JobFunctions() {}
    @JsonIgnore
    public void addJobFunction(String function) {
        designations.add(function);
    }
    @JsonIgnore
    public String getJobFunction(int index) {
        return designations.get(index);
    }
    @JsonIgnore
    public void removeJobFunction(int index) {
        designations.remove(index);
    }
    @JsonIgnore
    public int getSize() {
        return designations.size();
    }
}
