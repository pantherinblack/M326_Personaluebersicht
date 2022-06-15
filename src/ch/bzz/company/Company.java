package ch.bzz.company;

import java.util.Vector;

public class Company {
    private String name;
    private Vector<Department> departments = new Vector<>();
    private JobFunctions functions = new JobFunctions();
    private Teams teams = new Teams();

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public Department detDepartment(int index) {
        return departments.get(index);
    }

    public String getDepartmentName(int index) {
        return departments.get(index).getName();
    }

    public void removeDepartment(int index) {
        departments.remove(index);
    }

    public int getNumberOfDepartments() {
        return departments.size();
    }

    public void addFunction(String function) {
        functions.addJobFunction(function);
    }

    public String getFunction(int index) {
        return functions.getJobFunction(index);
    }

    public void removeFunction(int index) {
        functions.removeJobFunction(index);
    }

    public int getNumberOfFunction() {
        return functions.getSize();
    }

    public void addTeam(String team) {
        teams.addTeam(team);
    }

    public String getTeam(int index) {
        return teams.getTeam(index);
    }

    public void removeTeam(int index) {
        teams.removeTeam(index);
    }

    public int getNumberOfTeams() {
        return teams.getSize();
    }
}
