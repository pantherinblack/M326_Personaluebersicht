package ch.bzz.model.company;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

public class Teams {
    private Vector<String> designations = new Vector<>();

    /**
     * constructor
     * Needed for ObjectMapper
     */
    public Teams() {
    }

    public Vector<String> getDesignations() {
        return designations;
    }

    public void setDesignations(Vector<String> designations) {
        this.designations = designations;
    }

    /**
     * adds a team to the List
     *
     * @param team to be added
     */
    @JsonIgnore
    public void addTeam(String team) {
        designations.add(team);
    }

    /**
     * gets a team using the index
     *
     * @param index of the requested team
     * @return team
     */
    @JsonIgnore
    public String getTeam(int index) {
        return designations.get(index);
    }

    /**
     * removes a team unsing the index
     *
     * @param index in the List
     */
    @JsonIgnore
    public void removeTeam(int index) {
        designations.remove(index);
    }

    /**
     * removes a tam using the designation
     *
     * @param team designation
     */
    @JsonIgnore
    public void removeTeam(String team) {
        designations.remove(team);
    }

    /**
     * gives back the number of teams
     *
     * @return number of teams
     */
    @JsonIgnore
    public int getSize() {
        return designations.size();
    }
}
