package company;

import java.util.Vector;

public class Teams {
    private Vector<String> designations = new Vector<>();

    public Teams() {}

    public void addTeam(String team) {
        designations.add(team);
    }

    public String getTeam(int index) {
        return designations.get(index);
    }

    public void removeTeam(int index) {
        designations.remove(index);
    }

    public int getSize() {
        return designations.size();
    }
}
