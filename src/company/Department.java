package company;

import employees.Person;

import java.util.Vector;

public class Department {
    private String name;
    private Vector<Person> members = new Vector<>();

    public Department(String name) {
        this.name = name;
    }

    public void addMember(Person person) {
        members.add(person);
    }

    public Person getMember(int index) {
        return members.get(index);
    }

    public void removeMember(int index) {
        members.remove(index);
    }

    public int getNumberOfMembers() {
        return members.size();
    }
}
