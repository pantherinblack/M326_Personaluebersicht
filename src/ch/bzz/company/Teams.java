package ch.bzz.company;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

public class Teams {
    public Vector<String> getDesignations() {
        return designations;
    }

    public void setDesignations(Vector<String> designations) {
        this.designations = designations;
    }

    private Vector<String> designations = new Vector<>();
    public Teams() {}
    @JsonIgnore
    public void addTeam(String team) {
        designations.add(team);
    }
    @JsonIgnore
    public String getTeam(int index) {
        return designations.get(index);
    }
    @JsonIgnore
    public void removeTeam(int index) {
        designations.remove(index);
    }
    @JsonIgnore
    public int getSize() {
        return designations.size();
    }
}
