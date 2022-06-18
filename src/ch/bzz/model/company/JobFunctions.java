package ch.bzz.model.company;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

public class JobFunctions {
    private Vector<String> designations = new Vector<>();

    /**
     * constructor
     * Needed for ObjectMapper
     */
    public JobFunctions() {
    }

    public Vector<String> getDesignations() {
        return designations;
    }

    public void setDesignations(Vector<String> designations) {
        this.designations = designations;
    }

    /**
     * adds a function to the list
     *
     * @param function to be added
     */
    @JsonIgnore
    public void addJobFunction(String function) {
        designations.add(function);
    }

    /**
     * gets a function using an index
     *
     * @param index of the function
     * @return funtion
     */
    @JsonIgnore
    public String getJobFunction(int index) {
        return designations.get(index);
    }

    /**
     * removes a function using an index
     *
     * @param index of the function
     */
    @JsonIgnore
    public void removeJobFunction(int index) {
        designations.remove(index);
    }

    /**
     * removes a function using the designation
     *
     * @param function to be removed
     */
    @JsonIgnore
    public void removeJobFunction(String function) {
        designations.remove(function);
    }

    /**
     * gives back the number of functions
     *
     * @return number of functions
     */
    @JsonIgnore
    public int getSize() {
        return designations.size();
    }
}
