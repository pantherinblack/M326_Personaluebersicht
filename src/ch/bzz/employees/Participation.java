package ch.bzz.employees;

import java.util.Vector;

public class Participation {
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

    private Vector<String> functions;
    private Vector<String> teams;

    public Participation() {}

    public void addFunction(String jobFunction) {
        functions.add(jobFunction);
    }

    public String getFunction(int index) {
        return functions.get(index);
    }

    public void removeFunction(int index) {
        functions.remove(index);
    }

    public int getNumberOfFunctions() {
        return functions.size();
    }

    public void addTeam(String team) {
        teams.add(team);
    }

    public String getTeam(int index) {
        return teams.get(index);
    }

    public void removeTeam(int index) {
        teams.remove(index);
    }

    public int getNumberOfTeams() {
        return teams.size();
    }
}
