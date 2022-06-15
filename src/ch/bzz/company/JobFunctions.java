package ch.bzz.company;

import java.util.Vector;

public class JobFunctions {
    private Vector<String> designations = new Vector<>();

    public JobFunctions() {}

    public void addJobFunction(String function) {
        designations.add(function);
    }

    public String getJobFunction(int index) {
        return designations.get(index);
    }

    public void removeJobFunction(int index) {
        designations.remove(index);
    }

    public int getSize() {
        return designations.size();
    }
}
