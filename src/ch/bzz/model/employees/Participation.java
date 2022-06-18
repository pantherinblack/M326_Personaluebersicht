package ch.bzz.model.employees;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

/**
 * Model for indicating the functions and teams a person can have
 *
 * @author Kevin
 * @version 1.1
 * @since 18.05.2022
 */
public class Participation {
    private Vector<String> functions = new Vector<>();
    private Vector<String> teams = new Vector<>();

    /**
     * empty constructor
     * Needed for ObjectMapper
     */
    public Participation() {
    }

    /**
     * Adds a function to the person
     *
     * @param jobFunction to add
     */
    @JsonIgnore
    public void addFunction(String jobFunction) {
        functions.add(jobFunction);
    }

    /**
     * gets the function of the person, by the index
     *
     * @param index of the function
     * @return function
     */
    @JsonIgnore
    public String getFunction(int index) {
        return functions.get(index);
    }

    /**
     * removes a function of a person using an index
     *
     * @param index of the function to be removed
     */
    @JsonIgnore
    public void removeFunction(int index) {
        functions.remove(index);
    }

    /**
     * gets the number of functions
     *
     * @return number of function
     */
    @JsonIgnore
    public int getNumberOfFunctions() {
        return functions.size();
    }

    /**
     * adds a team to the person
     *
     * @param team to be added
     */
    @JsonIgnore
    public void addTeam(String team) {
        teams.add(team);
    }

    /**
     * gets the team of a person by a index
     *
     * @param index of the team
     * @return team
     */
    @JsonIgnore
    public String getTeam(int index) {
        return teams.get(index);
    }

    /**
     * removes a team of the person using an index
     *
     * @param index of teh team
     */
    @JsonIgnore
    public void removeTeam(int index) {
        teams.remove(index);
    }

    /**
     * gets the number of teams of the person
     *
     * @return number of teams
     */
    @JsonIgnore
    public int getNumberOfTeams() {
        return teams.size();
    }

    /**
     * gets the functions of the person
     *
     * @return functions
     */
    public Vector<String> getFunctions() {
        return functions;
    }

    /**
     * sets the functions of the person
     * Needed for ObjectMapper
     *
     * @param functions to be set
     */
    private void setFunctions(Vector<String> functions) {
        this.functions = functions;
    }

    /**
     * gets the teams of the person
     *
     * @return teams
     */
    public Vector<String> getTeams() {
        return teams;
    }

    /**
     * sets the teams of a person
     * Needed for ObjectMapper
     *
     * @param teams to be set
     */
    public void setTeams(Vector<String> teams) {
        this.teams = teams;
    }
}
