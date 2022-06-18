package ch.bzz.model.company;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

public class Company {
    private String name;
    private Vector<Department> departments = new Vector<>();
    private JobFunctions functions = new JobFunctions();
    private Teams teams = new Teams();

    private Company() {

    }
    @JsonIgnore
    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public void addDepartment(Department department) {
        departments.add(department);
    }

    public Vector<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Vector<Department> departments) {
        this.departments = departments;
    }

    public JobFunctions getFunctions() {
        return functions;
    }

    public void setFunctions(JobFunctions functions) {
        this.functions = functions;
    }

    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    @JsonIgnore
    public Department getDepartment(int index) {
        return departments.get(index);
    }
    @JsonIgnore
    public String getDepartmentName(int index) {
        return departments.get(index).getName();
    }
    @JsonIgnore
    public void removeDepartment(int index) {
        departments.remove(index);
    }
    @JsonIgnore
    public int getNumberOfDepartments() {
        return departments.size();
    }
    @JsonIgnore
    public void addFunction(String function) {
        functions.addJobFunction(function);
    }
    @JsonIgnore
    public String getFunction(int index) {
        return functions.getJobFunction(index);
    }
    @JsonIgnore
    public void removeFunction(int index) {
        functions.removeJobFunction(index);
    }
    @JsonIgnore
    public int getNumberOfFunction() {
        return functions.getSize();
    }
    @JsonIgnore
    public void addTeam(String team) {
        teams.addTeam(team);
    }
    @JsonIgnore
    public String getTeam(int index) {
        return teams.getTeam(index);
    }
    @JsonIgnore
    public void removeTeam(int index) {
        teams.removeTeam(index);
    }
    @JsonIgnore
    public int getNumberOfTeams() {
        return teams.getSize();
    }

    public void setName(String name) {
        this.name = name;
    }
}
