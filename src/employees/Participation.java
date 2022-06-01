package employees;

import company.*;

import java.util.Vector;

public class Participation {
    private Vector<JobFunctions> functions;
    private Vector<Teams> teams;

    public Participation() {}

    public void addFunction(JobFunctions jobFunction) {
        functions.add(jobFunction);
    }

    public JobFunctions getFunction(int index) {
        return functions.get(index);
    }

    public void removeFunction(int index) {
        functions.remove(index);
    }

    public int getNumberOfFunctions() {
        return functions.size();
    }

    public void addTeam(Teams team) {
        teams.add(team);
    }

    public Teams getTeam(int index) {
        return teams.get(index);
    }

    public void removeTeam(int index) {
        teams.remove(index);
    }

    public int getNumberOfTeams() {
        return teams.size();
    }
}
