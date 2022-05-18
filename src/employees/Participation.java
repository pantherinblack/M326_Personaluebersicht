package employees;

import company.*;

import java.util.Vector;

public class Participation {
    private Vector<JobFunction> functions;
    private Vector<Team> teams;
    private Person owner;

    public Participation(Person person) {
        owner = person;
    }

    public void addFunction(JobFunction jobFunction) {
        functions.add(jobFunction);
    }
}
