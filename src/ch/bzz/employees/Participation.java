package ch.bzz.employees;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

public class Participation {


    private Vector<String> functions = new Vector<>();
    private Vector<String> teams = new Vector<>();

    public Participation() {}
    @JsonIgnore
    public void addFunction(String jobFunction) {
        functions.add(jobFunction);
    }
    @JsonIgnore
    public String getFunction(int index) {
        return functions.get(index);
    }
    @JsonIgnore
    public void removeFunction(int index) {
        functions.remove(index);
    }
    @JsonIgnore
    public int getNumberOfFunctions() {
        return functions.size();
    }
    @JsonIgnore
    public void addTeam(String team) {
        teams.add(team);
    }
    @JsonIgnore
    public String getTeam(int index) {
        return teams.get(index);
    }
    @JsonIgnore
    public void removeTeam(int index) {
        teams.remove(index);
    }
    @JsonIgnore
    public int getNumberOfTeams() {
        return teams.size();
    }

    public Vector<String> getFunctions() {
        return functions;
    }

    public void setFunctions(Vector<String> functions) {
        this.functions = functions;
    }

    public Vector<String> getTeams() {
        return teams;
    }

    public void setTeams(Vector<String> teams) {
        this.teams = teams;
    }
}
