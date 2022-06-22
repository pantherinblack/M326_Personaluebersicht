package ch.bzz.model.company;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

/**
 * Model storing all information
 *
 * @author Kevin
 * @version 1.3
 * @since 18.05.2022
 */
public class Company {
    private String name;
    private Vector<Department> departments = new Vector<>();
    private JobFunctions functions = new JobFunctions();
    private Teams teams = new Teams();

    /**
     * empty constructor
     * Needed for ObjectMapper
     */
    private Company() {
    }

    /**
     * constructor setting the name
     *
     * @param name to be set
     */
    public Company(String name) {
        this.name = name;
    }

    /**
     * gets the name of the company
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the comany name
     *
     * @param name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * adds a department
     *
     * @param department to be added
     */
    @JsonIgnore
    public void addDepartment(Department department) {
        departments.add(department);
    }

    /**
     * gets the departments
     *
     * @return departments
     */
    public Vector<Department> getDepartments() {
        return departments;
    }

    /**
     * sets the departments
     * Needed for ObjectMapper
     *
     * @param departments to be set
     */
    public void setDepartments(Vector<Department> departments) {
        this.departments = departments;
    }

    /**
     * gets the functions
     * Needed for ObjectMapper
     *
     * @return functions
     */
    public JobFunctions getFunctions() {
        return functions;
    }

    /**
     * sets the functions
     * Needed for ObjectMapper
     *
     * @param functions to be set
     */
    public void setFunctions(JobFunctions functions) {
        this.functions = functions;
    }

    /**
     * gets the teams
     * Needed for ObjectMapper
     *
     * @return teams
     */
    public Teams getTeams() {
        return teams;
    }

    /**
     * sets the teams
     * Needed for ObjectMapper
     *
     * @param teams to be set
     */
    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    /**
     * gets the department using the index
     *
     * @param index of the department
     * @return department
     */
    @JsonIgnore
    public Department getDepartment(int index) {
        return departments.get(index);
    }

    /**
     * gets the name of a specific department
     *
     * @param index of the department
     * @return name
     */
    @JsonIgnore
    public String getDepartmentName(int index) {
        return departments.get(index).getName();
    }

    /**
     * removes a department usinig the index
     *
     * @param index of the department
     */
    @JsonIgnore
    public void removeDepartment(int index) {
        departments.remove(index);
    }

    /**
     * removes a department using the reference
     *
     * @param department to be removed
     */
    @JsonIgnore
    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    /**
     * gets the number of departments of the company
     *
     * @return number of departments
     */
    @JsonIgnore
    public int getNumberOfDepartments() {
        return departments.size();
    }

    /**
     * adds a function to the company
     *
     * @param function to be added
     */
    @JsonIgnore
    public void addFunction(String function) {
        functions.addJobFunction(function);
    }

    /**
     * gets the function using the index
     *
     * @param index of the function
     * @return function
     */
    @JsonIgnore
    public String getFunction(int index) {
        return functions.getJobFunction(index);
    }

    /**
     * removes a function using the index
     *
     * @param index of the function
     */
    @JsonIgnore
    public void removeFunction(int index) {
        functions.removeJobFunction(index);
    }

    /**
     * removes a function using the name
     *
     * @param function to be removed
     */
    @JsonIgnore
    public void removeFunction(String function) {
        functions.removeJobFunction(function);
    }

    /**
     * gets the number of functions in the company
     *
     * @return number of teams
     */
    @JsonIgnore
    public int getNumberOfFunction() {
        return functions.getSize();
    }

    /**
     * adds a team
     *
     * @param team to be added
     */
    @JsonIgnore
    public void addTeam(String team) {
        teams.addTeam(team);
    }

    /**
     * gets a team using the index
     *
     * @param index of the team
     * @return team
     */
    @JsonIgnore
    public String getTeam(int index) {
        return teams.getTeam(index);
    }

    /**
     * removes a team using the index
     *
     * @param index
     */
    @JsonIgnore
    public void removeTeam(int index) {
        teams.removeTeam(index);
    }

    /**
     * removes a team using the name
     *
     * @param team to be removed
     */
    @JsonIgnore
    public void removeTeam(String team) {
        teams.removeTeam(team);
    }

    /**
     * gets the number of teams in the company
     *
     * @return number of teams
     */
    @JsonIgnore
    public int getNumberOfTeams() {
        return teams.getSize();
    }
}
